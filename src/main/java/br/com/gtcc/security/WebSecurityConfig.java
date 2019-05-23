package br.com.gtcc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private GpUserDetailsService userDetailsService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests()
			.antMatchers(
                    "/js/**",
                    "/css/**",
                    "/img/**",
                    "/webjars/**").permitAll()
				.antMatchers("**/gtcc/usuarios**").hasAnyRole("ROLE_USUARIOS")
				.antMatchers("**/gtcc/produtos**").hasAnyRole("ROLE_PRODUTOS")
				.antMatchers("**/gtcc/perfilUsuarios**").hasAnyRole("ROLE_PERFIL")
				.anyRequest() //para qualquer requisição
				.authenticated()//usuário precisa estar autenticado
				
				//.antMatchers("**/perfisUsuario").hasAnyRole("ROLE_REL_CUSTOS")
				
			.and()
			.formLogin()
				.loginPage("/gtcc/login")
				.passwordParameter("senha")
				.usernameParameter("nomeUsuario")
				.defaultSuccessUrl("/gtcc/home")
				.permitAll() //permita todos acessar a página de login
			.and()
			.logout()
				.logoutSuccessUrl("/gtcc/login?logout=true")// caso o usuário dê logout
				.permitAll()
			.and()
			.rememberMe()
				.userDetailsService(userDetailsService);
	}



}
