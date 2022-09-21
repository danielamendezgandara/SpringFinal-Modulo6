package cl.awakelab.empresaprevencionriesgos;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsManager users(DataSource dataSource) {
		String userQuery = "select nick,password1,'true' as enabled from usuario where nick = ?";
		String authoritiesQuery = "select nick,perfil_id,'true' as enabled from usuario where nick = ?";
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.setUsersByUsernameQuery(userQuery);
		users.setAuthoritiesByUsernameQuery(authoritiesQuery);
		return users;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// Permite recuperar datos de usuario autenticado
		auth.eraseCredentials(false);
		/* configure user-service, password-encoder etc ... */
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder,
			UserDetailsService userDetailService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailService)
				.passwordEncoder(passwordEncoder).and().build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// Autorizaciones -> ADMIN : código 1 , CLIENTE : código 2 , PROFESIONAL :
		// código 3
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and().authorizeRequests()
				.antMatchers("/","/resources/**","/js/**","/css/**").permitAll()
				.antMatchers("/crearUsuario", "/editarUsuario","/eliminarUsuario","/listadoDeUsuarios").hasAnyAuthority("1")
				.antMatchers("/capacitacion/**","/contacto").hasAnyAuthority("2")
				.antMatchers("/enConstruccion").hasAnyAuthority("1", "2", "3")
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login")
				.loginProcessingUrl("/logincheck").usernameParameter("nick").passwordParameter("password1")
				.defaultSuccessUrl("/inicio").permitAll().and().logout().logoutUrl("/cerrarsesion")
				.logoutSuccessUrl("/").permitAll().and().exceptionHandling().accessDeniedPage("/403");

		return http.build();
	}

}