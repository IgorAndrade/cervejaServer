package br.com.cerveja.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RNException extends Exception{
	private static final long serialVersionUID = 1L;
	
	private List<String> erros = new ArrayList<String>();
	
	public RNException(String msg) {
		super(msg);
	}
	public RNException(String msg,List<String> erros) {
		super(msg);
		this.erros=erros;
	}
	
	public void add(String erro){
		this.erros.add(erro);
	}
	
	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}

	
	
}
