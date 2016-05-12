package br.com.cerveja.controller;

import br.com.cerveja.model.cerveja.StatusCerveja;

public class Filter {

	private String cerveja;
	private String cervejaria;
	private StatusCerveja status;
	private String style;
	private String categoria;
	
	public String getCerveja() {
		return cerveja;
	}
	public void setCerveja(String cerveja) {
		this.cerveja = cerveja;
	}
	public String getCervejaria() {
		return cervejaria;
	}
	public void setCervejaria(String cervejaria) {
		this.cervejaria = cervejaria;
	}
	public StatusCerveja getStatus() {
		return status;
	}
	public void setStatus(StatusCerveja status) {
		this.status = status;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}
