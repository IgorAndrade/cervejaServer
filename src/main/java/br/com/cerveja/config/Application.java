package br.com.cerveja.config;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


@EnableAutoConfiguration
@ComponentScan("br.com.cerveja")
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableJpaRepositories(basePackages="br.com.cerveja.repository")
@EnableTransactionManagement
@EntityScan(basePackages="br.com.cerveja.model")
@PropertySource(value={"classpath:configuration.properties"})
public class Application  extends SpringBootServletInitializer{
	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(Application.class).run(args);
	    }
	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(Application.class);
	    }
	
	@Bean
    public ApplicationSecurity applicationSecurity() {
	return new ApplicationSecurity();
    }

    @Bean
    public LocaleResolver localeResolver() {
	SessionLocaleResolver slr = new SessionLocaleResolver();
	slr.setDefaultLocale(Locale.ENGLISH);
	return slr;
    }
    
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
    	Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
    	b.indentOutput(true).dateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    	return b;
    }
}
