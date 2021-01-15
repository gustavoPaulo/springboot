package curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.UsuarioModel;
import curso.springboot.model.UsuariosRole;
import curso.springboot.repository.UsuarioRepository;
import curso.springboot.repository.UsuariosRoleRepository;
import curso.springboot.service.EnvioEmailService;

@Controller
public class UsuarioController {

	private static final String USUARIO_VIEW = "cadastro/cadastroUsuario";
	private static final String VISUALIZARUSUARIO_VIEW = "visualizarUsuario";
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuariosRoleRepository usuariosRoleRepository;
	
	@Autowired
	private EnvioEmailService envioEmailService;
	
	
	@RequestMapping("/novoUsuario")
	public ModelAndView novoUsuario() {
		
		ModelAndView mv = new ModelAndView(USUARIO_VIEW);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/criarUsuario")
	public ModelAndView criar(@Valid UsuarioModel usuario, UsuariosRole usuariosRole) {
		
		envioEmailService.envioEmailUsuario(usuario);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode(usuario.getPassword());

		usuario.setSenha(result);

		usuarioRepository.save(usuario);
		
		
		usuariosRole.setUsuario_id(usuario.getId());
		usuariosRoleRepository.save(usuariosRole);
		
		
		ModelAndView mv = new ModelAndView(USUARIO_VIEW);
		mv.addObject("mensagem", "Usuário criado com sucesso!");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;
	}
	
	@RequestMapping("/visualizarUsuario")
	public ModelAndView visualizarUsuario() {
		
		ModelAndView mv = new ModelAndView(VISUALIZARUSUARIO_VIEW);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;
	}
	
	@PostMapping("/pesquisarUsuario")
	public ModelAndView pesquisar(@RequestParam("nomePesquisa") String nomePesquisa) {
		
		List<UsuarioModel> usuarioModel = new ArrayList<UsuarioModel>();
		
		usuarioModel = usuarioRepository.findPessoaByName(nomePesquisa);
		
		ModelAndView mv = new ModelAndView(VISUALIZARUSUARIO_VIEW);
		mv.addObject("usuarios", usuarioModel);
		mv.addObject("permissoes", (List<UsuariosRole>) usuariosRoleRepository.findAllPermissoes());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;
	}
	
	@GetMapping("/excluirUsuario/{idUsuario}")
	public ModelAndView excluir(@PathVariable("idUsuario") Long idUsuario) {
		
		usuarioRepository.deleteById(idUsuario);
		
		ModelAndView mv = new ModelAndView(VISUALIZARUSUARIO_VIEW);
		mv.addObject("usuarios", usuarioRepository.findPessoaByTodos());
		mv.addObject("permissoes", (List<UsuariosRole>) usuariosRoleRepository.findAllPermissoes());
		mv.addObject("mensagem", "Usuário excluido com sucesso!");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		mv.addObject("usuarioSessao", auth.getName());
		
		return mv;
	}
	
	@RequestMapping({"/loginError"})
	  public ModelAndView validaLogin() {
	    ModelAndView mv = new ModelAndView("/login");
	    mv.addObject("error", "Usuário ou senha incorretos!");
	    return mv;
	  }
}