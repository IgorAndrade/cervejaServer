package br.com.cerveja.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class RNExceptionFactory {
	@Autowired
	private MessageSource message;
	private ArrayList<String> erros= new ArrayList<String>();
	
	
	public RNExceptionFactory addRN(RN rn,String... param){
		
		String erro = message.getMessage(rn.getMsg(), param, null);
		this.erros.add(erro);
		
		return this;
	}
	public RNExceptionFactory addRN(RN rn){
		String erro = message.getMessage(rn.getMsg(), null, null);
		this.erros.add(erro);
		
		return this;
	}
	
	public RNException build(){
		ArrayList<String> clone =(ArrayList<String>) this.erros.clone();
		this.erros.clear();
		return new RNException("Erro", clone);
	}
	public void lancar() throws RNException{
		ArrayList<String> clone =(ArrayList<String>) this.erros.clone();
		this.erros.clear();
		throw new RNException("Erro", clone);
	}
}
