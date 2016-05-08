package br.com.cerveja.model.cerveja;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
@RestResource(exported = false)
public class Category {
@Id @GeneratedValue
	private Long id;
	private Date createDate;
	@NotBlank @Column(unique=true)
   	private String name;
   	@Enumerated(EnumType.STRING)
   	private FAMILIA familia;
   	
   	public enum FAMILIA{
   		LAGER,ALE,OUTRAS
   	}
   	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public FAMILIA getFamilia() {
		return familia;
	}
	public void setFamilia(FAMILIA familia) {
		this.familia = familia;
	}
}
