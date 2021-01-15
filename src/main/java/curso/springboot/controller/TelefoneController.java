package curso.springboot.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


import curso.springboot.model.PessoaModel;
import curso.springboot.model.TelefoneModel;
import curso.springboot.model.TipoTelefone;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.TelefoneRepository;


@Controller
public class TelefoneController {

	private static final String TELEFONE_VIEW = "cadastro/telefonePessoa";
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;
	
	
	
	
	@GetMapping("/telefonePessoa/{idPessoa}")
	public ModelAndView telefone(@PathVariable("idPessoa") Long idPessoa) {
		
		Optional<PessoaModel> pessoa = pessoaRepository.findById(idPessoa);
		ModelAndView mv = new ModelAndView(TELEFONE_VIEW);
		mv.addObject("telefones", telefoneRepository.getTelefoneModel(idPessoa));
		
		mv.addObject("pessoaObj", pessoa.get());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;
	}
	
	@PostMapping("/addTelefonePessoa/{idPessoa}")
	public ModelAndView addTelefonePessoa(TelefoneModel telefoneModel, @PathVariable("idPessoa") Long idPessoa) {
		
		PessoaModel pessoaModel = pessoaRepository.findById(idPessoa).get();
		
		if(telefoneModel != null && (telefoneModel.getNumero().isEmpty())
				|| telefoneModel.getNumero() == null || telefoneModel.getNumero().length() < 12) {
			
			ModelAndView mv = new ModelAndView(TELEFONE_VIEW);
			mv.addObject("pessoaObj", pessoaModel);
			mv.addObject("telefones", telefoneRepository.getTelefoneModel(idPessoa));
			
			List<String> msg = new ArrayList<String>();
			
			if(telefoneModel.getNumero().isEmpty()) {
				msg.add("Número deve ser preenchido!");
			}
			if(telefoneModel.getNumero().length() < 12) {
				msg.add("Número deve conter no mínimo 11 dígitos com o DDD!");
			}
			
			mv.addObject("msgErro", msg);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			mv.addObject("usuarioSessao", auth.getName());
			
			return mv;
		}
		
		telefoneModel.setPessoaModel(pessoaModel);
		telefoneRepository.save(telefoneModel);
		
		ModelAndView mv = new ModelAndView(TELEFONE_VIEW);
		mv.addObject("pessoaObj", pessoaModel);
		mv.addObject("mensagem", "Telefone salvo com sucesso!");
		mv.addObject("telefones", telefoneRepository.getTelefoneModel(idPessoa));
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;
	}
	
	@GetMapping("/excluirTelefone/{idTelefone}")
	public ModelAndView excluir(@PathVariable("idTelefone") Long idTelefone) {
		
		PessoaModel pessoaModel = telefoneRepository.findById(idTelefone).get().getPessoaModel();
		
		telefoneRepository.deleteById(idTelefone);
		
		ModelAndView mv = new ModelAndView(TELEFONE_VIEW);
		mv.addObject("mensagem", "Telefone excluido com sucesso!");
		
		mv.addObject("pessoaObj", pessoaModel);
		mv.addObject("telefones", telefoneRepository.getTelefoneModel(pessoaModel.getId()));
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;
	}
	
	@ModelAttribute("todosOsTiposTelefone")
	public List<TipoTelefone> todosOsTiposTelefone(){
		
		return Arrays.asList(TipoTelefone.values());
	}
	
}