package br.com.cerveja.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.thymeleaf.util.StringUtils;

@Controller
public class Index {
	 @RequestMapping("/")
	 public String index(){
		 return  "index";
	 }
	 
	 @RequestMapping("/ng-templates/{resource}")
	    public String angularHtmlTemplate(@PathVariable("resource") String resource) {

		return "ng-templates/" + resource;
	    }
	 

	    @RequestMapping("/login")
	    public String login() {
		return "login";
	    }

	    @RequestMapping("/logout")
	    public String logout() {
		return "login";
	    }
	    @GET
	    @RequestMapping("/adm/**")
	    public String home(HttpServletRequest request) {
	    	String restOfTheUrl = (String) request.getAttribute(
	    	        HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	    	return StringUtils.substringBefore(restOfTheUrl, ".html");
	    }
}
