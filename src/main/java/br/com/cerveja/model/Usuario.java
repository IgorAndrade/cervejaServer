package br.com.cerveja.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Usuario {
	@Id
	@GeneratedValue
	private Long id;
	@NotBlank(message="erro.usuario.email.null")
	@Email(message="erro.usuario.email.invalido")
	@Length(max=200)
	@Column(unique=true,nullable=false,length=200)
	private String email;
	@Length(min=5,max=8,message="erro.usuario.password.invalido")
	private String password;
	
	@NotBlank(message="erro.usuario.nome.null")
	@Length(max=80)
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass=Perfis.class)
	private List<Perfis> perfis;
	
	public List<Perfis> getPerfis() {
		return perfis;
	}
	public void setPerfis(List<Perfis> perfis) {
		this.perfis = perfis;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	private String sobrenome;
}
