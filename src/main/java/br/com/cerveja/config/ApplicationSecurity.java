package br.com.cerveja.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("login")
	private UserDetailsService loginSevice;
	

	@Bean
	public TokenBasedRememberMeServices rememberMeServices() {
		return new TokenBasedRememberMeServices("remember-me-key",
				loginSevice);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		 auth.inMemoryAuthentication().withUser("adm-ti").password("senha123").roles("role_adm_ti","role_user");
		 auth.inMemoryAuthentication().withUser("adm").password("senha").roles("role_adm","role_user");
		 auth.eraseCredentials(true)
			.userDetailsService(loginSevice)
			.passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().csrf().disable().authorizeRequests()
				.antMatchers("/views/**").permitAll()
				.antMatchers("/index.html").permitAll()
//				.antMatchers("/rest/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/static/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/service/cervejaria/**").hasRole("role_user")
//				.antMatchers("/service/cerveja/**").hasRole("ADM-TI")
				.anyRequest()
				.fullyAuthenticated()
				.and()
//				.anyRequest().permitAll().and()
				.formLogin().loginPage("/#login").failureUrl("/#login?error").usernameParameter("username")
			    .passwordParameter("password")
				.loginProcessingUrl("/authenticate")
				.permitAll().and().logout().deleteCookies("JSESSIONID").invalidateHttpSession(true)
				.logoutUrl("/logout")
				.permitAll().logoutSuccessUrl("/#login?logout").and().rememberMe()
				.rememberMeServices(rememberMeServices())
				.key("remember-me-key").and().exceptionHandling().accessDeniedPage("/Access_Denied");;

	}
}