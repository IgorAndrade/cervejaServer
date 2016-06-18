package br.com.cerveja.model;

public enum Perfis {
	ADM("role_adm"), ADM_TI("role_adm_ti"), USER("role_user");
	private String role;

	Perfis(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
