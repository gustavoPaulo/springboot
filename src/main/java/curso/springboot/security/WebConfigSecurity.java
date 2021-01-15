package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable() //Desativa as configurações padrão de memoria
		.authorizeRequests() //Permite restringir acessos
		.antMatchers(HttpMethod.GET, "/").permitAll() //All -> acesso liberado para todos
		.anyRequest().authenticated()
		.and().formLogin().permitAll() //Permite qualquer usuario
		.loginPage("/login")
		.defaultSuccessUrl("/springboot")
		.failureUrl("/loginError")
		.and().logout().logoutSuccessUrl("/login") //Mapeia url de sair do sistema e valida usuario autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override //Cria autenticação do banco de dados com o usuario em memoria
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	@Override //Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/materialize/**");
	}
}