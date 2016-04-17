package br.com.cerveja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
