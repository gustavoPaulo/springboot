package curso.springboot.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import curso.springboot.model.UsuarioModel;
import curso.springboot.repository.UsuarioRepository;

@Service
@Transactional
public class ImplementacaoUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioModel usuarioModel = usuarioRepository.findUserByLogin(username);
		
		if(usuarioModel == null) {
			
			throw new UsernameNotFoundException("Usuário não foi encontrado!");
		}
		
		return new User(usuarioModel.getLogin(), 
						usuarioModel.getSenha(), 
						usuarioModel.isEnabled(), 
						true, 
						true, 
						true, 
						usuarioModel.getAuthorities());
	}
}