package curso.springboot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import curso.springboot.model.PessoaModel;
import curso.springboot.model.SexoPessoa;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.ProfissaoRepository;
import curso.springboot.repository.TelefoneRepository;
import curso.springboot.service.EnvioEmailService;
import curso.springboot.service.ReportUtil;

@Controller
public class PessoaController {

	private static final String CADASTRO_VIEW = "cadastro/cadastroPessoa";
	private static final String INDEX_VIEW = "index";
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private ReportUtil reportUtil;
	
	@Autowired
	private ProfissaoRepository profissaoRepository;
	
	@Autowired
	private EnvioEmailService envioEmailService;
	
	
	
	@RequestMapping("/springboot")
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView(INDEX_VIEW);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		mv.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 10, Sort.by("nome"))));
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/cadastroPessoa")
	public ModelAndView cadastroPessoa() {
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("pessoaObj", new PessoaModel());
		
		mv.addObject("profissoes", profissaoRepository.findAll());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/novo", consumes = {"multipart/form-data"})
	public ModelAndView criar(@Valid PessoaModel pessoaModel, BindingResult bindingResult, 
								final MultipartFile file) throws IOException {

		pessoaModel.setTelefones(telefoneRepository.getTelefoneModel(pessoaModel.getId()));
		
		if(bindingResult.hasErrors()) {
			
			ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
			mv.addObject("pessoaObj", pessoaModel);
			
			List<String> msg = new ArrayList<String>();
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				
				msg.add(objectError.getDefaultMessage());
			}
			
			mv.addObject("msgErro", msg);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			mv.addObject("usuarioSessao", auth.getName());
			mv.addObject("profissoes", profissaoRepository.findAll());
			
			return mv;
		}
		
		if(file.getSize() > 0) {
			
			pessoaModel.setCurriculo(file.getBytes());
			pessoaModel.setTipoFileCurriculo(file.getContentType());
			pessoaModel.setNomeFileCurriculo(file.getOriginalFilename());
			
		}else {
			
			if(pessoaModel.getId() != null && pessoaModel.getId() > 0) {
				
				PessoaModel pessoaTemp = pessoaRepository.findById(pessoaModel.getId()).get();
				
				pessoaModel.setCurriculo(pessoaTemp.getCurriculo());
				pessoaModel.setTipoFileCurriculo(pessoaTemp.getTipoFileCurriculo());
				pessoaModel.setNomeFileCurriculo(pessoaTemp.getNomeFileCurriculo());
			}
		}
		
		pessoaRepository.save(pessoaModel);
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("pessoaObj", new PessoaModel());
		mv.addObject("profissoes", profissaoRepository.findAll());
		mv.addObject("mensagem", "Pessoa salva com sucesso!");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;

	}
	
	@RequestMapping(method = RequestMethod.GET, value="/listaPessoas")
	public ModelAndView pessoas() {
		
		ModelAndView mv = new ModelAndView(INDEX_VIEW);
		
		mv.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 10, Sort.by("nome"))));
		
		mv.addObject("pessoaObj", new PessoaModel());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;
	}
	
	@GetMapping("/editarPessoa/{idPessoa}")
	public ModelAndView editar(@PathVariable("idPessoa") Long idPessoa) {
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		Optional<PessoaModel> pessoa = pessoaRepository.findById(idPessoa);
		
		mv.addObject("pessoaObj", pessoa.get());
		mv.addObject("profissoes", profissaoRepository.findAll());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;
	}
	
	@GetMapping("/excluirPessoa/{idPessoa}")
	public ModelAndView excluir(@PathVariable("idPessoa") Long idPessoa) {
		
		pessoaRepository.deleteById(idPessoa);
		
		ModelAndView mv = new ModelAndView(INDEX_VIEW);
		mv.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 10, Sort.by("nome"))));
		mv.addObject("mensagem", "Pessoa excluida com sucesso!");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;
	}
	
	@PostMapping("/pesquisar")
	public ModelAndView pesquisar(@RequestParam("nomePesquisa") String nomePesquisa, 
									@RequestParam("pesquisaSexo") SexoPessoa pesquisaSexo, 
										@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
		
		Page<PessoaModel> pessoaModel = null;
		
		if(pesquisaSexo != null && pesquisaSexo.getDescricao().isEmpty()) {
			pessoaModel = pessoaRepository.findPessoaByNameSexoPage(nomePesquisa, pesquisaSexo, pageable);
			
		}else if(pesquisaSexo != null && nomePesquisa.isEmpty()) {
			pessoaModel = pessoaRepository.findPessoaBySexoPage(pesquisaSexo, pageable);
			
		}else {
			pessoaModel = pessoaRepository.findPessoaByNamePage(nomePesquisa, pageable);
		}
		
		
		ModelAndView mv = new ModelAndView(INDEX_VIEW);
		mv.addObject("pessoas", pessoaModel);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		mv.addObject("nomePesquisa", nomePesquisa);
		
		return mv;
	}
	
	//Imprime relatório
	@GetMapping("/pesquisar")
	public void imprimePdf(@RequestParam("nomePesquisa") String nomePesquisa, 
							@RequestParam("pesquisaSexo") SexoPessoa pesquisaSexo,
							 HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<PessoaModel> pessoaModel = new ArrayList<PessoaModel>();
		
		if(pesquisaSexo != null && nomePesquisa != null) {
			
			pessoaModel = pessoaRepository.findPessoaByNameSexo(nomePesquisa, pesquisaSexo);
			
		}else if(nomePesquisa != null) {
			
			pessoaModel = pessoaRepository.findPessoaByName(nomePesquisa);
			
		}else {
			Iterable<PessoaModel> iterator = pessoaRepository.findAll(PageRequest.of(0, 10, Sort.by("nome")));
			
			for (PessoaModel pessoaModel2 : iterator) {
				
				pessoaModel.add(pessoaModel2);
			}
		}
		
		//Chama o serviço que faz a geração do relatorio -> ReportUtil
		byte[] pdf = reportUtil.geraRelatorio(pessoaModel, "PessoaModel", request.getServletContext());
		
		//Tamanho da resposta
		response.setContentLength(pdf.length);
		
		//Definir na resposta o tipo de arquivo
		response.setContentType("application/octet-stream");
		
		//Cabeçalho da resposta
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
		
		response.setHeader(headerKey, headerValue);
		
		//Finaliza a resposta para o navegador
		response.getOutputStream().write(pdf);
		
	}
	
	//Enviar relatório por e-mail
	@GetMapping("/pesquisarEmail")
	public ModelAndView enviarPdfEmail(@RequestParam("nomePesquisa") String nomePesquisa, 
							@RequestParam("pesquisaSexo") SexoPessoa pesquisaSexo,
								HttpServletRequest request, HttpServletResponse response, String enviarPara) throws Exception{
			
		List<PessoaModel> pessoaModel = new ArrayList<PessoaModel>();
		
		if(pesquisaSexo != null && nomePesquisa.isEmpty()) {
			
			pessoaModel = pessoaRepository.findPessoaByNameSexo(nomePesquisa, pesquisaSexo);
			
		}else if(nomePesquisa != null) {
			
			pessoaModel = pessoaRepository.findPessoaByName(nomePesquisa);
			
		}else {
			Iterable<PessoaModel> iterator = pessoaRepository.findAll(PageRequest.of(0, 10, Sort.by("nome")));
			
			for (PessoaModel pessoaModel2 : iterator) {
				
				pessoaModel.add(pessoaModel2);
			}
		}
			
			//Chama o serviço que faz a geração do relatorio -> ReportUtil
			byte[] pdf = reportUtil.geraRelatorio(pessoaModel, "PessoaModel", request.getServletContext());

			envioEmailService.enviaRelatorioEmail(enviarPara, pdf);
			
			ModelAndView mv = new ModelAndView(INDEX_VIEW);
			mv.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 10, Sort.by("nome"))));
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			mv.addObject("usuarioSessao", auth.getName());
			mv.addObject("mensagem", "E-mail enviado com sucesso!");
			
			return mv;
		}
	
	@GetMapping("/baixarCurriculo/{idPessoa}")
	public void baixarCurriculoPessoa(@PathVariable("idPessoa") Long idPessoa, HttpServletResponse response) throws IOException {
		
		PessoaModel pessoaModel = pessoaRepository.findById(idPessoa).get();
		
		if(pessoaModel.getCurriculo() != null && pessoaModel.getTipoFileCurriculo() != null) {
			
			response.setContentLength(pessoaModel.getCurriculo().length);
			
			response.setContentType(pessoaModel.getTipoFileCurriculo());
			
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", pessoaModel.getNomeFileCurriculo());
			
			response.setHeader(headerKey, headerValue);
			
			response.getOutputStream().write(pessoaModel.getCurriculo());
		}
		
	}
	
	
	@ModelAttribute("todosOsSexos")
	public List<SexoPessoa> todosOsSexos(){
		
		return Arrays.asList(SexoPessoa.values());
	}
	
	@GetMapping("/pessoaPagina")
	public ModelAndView carregaPessoaPorPagina(@PageableDefault(size = 10) Pageable pageable, 
			ModelAndView mv, @RequestParam("nomePesquisa") String nomePesquisa, @RequestParam("pesquisaSexo") SexoPessoa pesquisaSexo) {
		
		Page<PessoaModel> pagePessoaModel = pessoaRepository.findPessoaByNameSexoPage(nomePesquisa, pesquisaSexo, pageable);
		mv.addObject("pessoas", pagePessoaModel);
		mv.addObject("nomePesquisa", nomePesquisa);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		mv.setViewName(INDEX_VIEW);
		
		return mv;
	}
	
}