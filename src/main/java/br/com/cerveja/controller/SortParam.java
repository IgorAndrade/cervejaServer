package br.com.cerveja.controller;

import br.com.cerveja.model.cerveja.StatusCerveja;

public class SortParam {

	private String sortCerveja;
	private String sortCervejaria;
	private StatusCerveja sortStatus;
	private String sortStyle;
	private String sortCategoria;
	public String getSortCerveja() {
		return sortCerveja;
	}
	public void setSortCerveja(String sortCerveja) {
		this.sortCerveja = sortCerveja;
	}
	public String getSortCervejaria() {
		return sortCervejaria;
	}
	public void setSortCervejaria(String sortCervejaria) {
		this.sortCervejaria = sortCervejaria;
	}
	public StatusCerveja getSortStatus() {
		return sortStatus;
	}
	public void setSortStatus(StatusCerveja sortStatus) {
		this.sortStatus = sortStatus;
	}
	public String getSortStyle() {
		return sortStyle;
	}
	public void setSortStyle(String sortStyle) {
		this.sortStyle = sortStyle;
	}
	public String getSortCategoria() {
		return sortCategoria;
	}
	public void setSortCategoria(String sortCategoria) {
		this.sortCategoria = sortCategoria;
	}
	
	
}
