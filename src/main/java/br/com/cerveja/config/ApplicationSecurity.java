package br.com.cerveja.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

//	@Autowired
//	@Qualifier("login")
//	private UserDetailsService loginSevice;
	

//	@Bean
//	public TokenBasedRememberMeServices rememberMeServices() {
//		return new TokenBasedRememberMeServices("remember-me-key",
//				loginSevice);
//	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new StandardPasswordEncoder();
//	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/views/**").permitAll()
				.antMatchers("/rest/**").permitAll()
				.antMatchers("/static/**").permitAll()
//				.anyRequest()
//				.fullyAuthenticated()
//				.and()
				.anyRequest().permitAll().and()
				.formLogin().loginPage("/#login").failureUrl("/#login?error")
				.loginProcessingUrl("/authenticate")
				.permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.permitAll().logoutSuccessUrl("/login?logout");

	}
}