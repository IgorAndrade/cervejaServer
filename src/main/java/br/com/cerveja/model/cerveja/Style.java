package br.com.cerveja.model.cerveja;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
@RestResource(exported = false)
public class Style {
	@Id @GeneratedValue
	private Long id;
	private String abvMax;
   	private String abvMin;
   	@ManyToOne(fetch=FetchType.EAGER)
   	private Category category;
   	private Date createDate;
   	@Column(length=4000,columnDefinition="varchar(4000)")
   	private String description;
   	private String fgMax;
   	private String fgMin;
   	private String ibuMax;
   	private String ibuMin;
   	@NotBlank @Column(unique=true)
   	private String name;
   	private String ogMin;
   	private String srmMax;
   	private String srmMin;
   	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAbvMax() {
		return abvMax;
	}
	public void setAbvMax(String abvMax) {
		this.abvMax = abvMax;
	}
	public String getAbvMin() {
		return abvMin;
	}
	public void setAbvMin(String abvMin) {
		this.abvMin = abvMin;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		if (description.length() > 3900)
			this.description = description.substring(0, 3900) + "...";
		else
			this.description = description;
	}
	public String getFgMax() {
		return fgMax;
	}
	public void setFgMax(String fgMax) {
		this.fgMax = fgMax;
	}
	public String getFgMin() {
		return fgMin;
	}
	public void setFgMin(String fgMin) {
		this.fgMin = fgMin;
	}
	public String getIbuMax() {
		return ibuMax;
	}
	public void setIbuMax(String ibuMax) {
		this.ibuMax = ibuMax;
	}
	public String getIbuMin() {
		return ibuMin;
	}
	public void setIbuMin(String ibuMin) {
		this.ibuMin = ibuMin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOgMin() {
		return ogMin;
	}
	public void setOgMin(String ogMin) {
		this.ogMin = ogMin;
	}
	public String getSrmMax() {
		return srmMax;
	}
	public void setSrmMax(String srmMax) {
		this.srmMax = srmMax;
	}
	public String getSrmMin() {
		return srmMin;
	}
	public void setSrmMin(String srmMin) {
		this.srmMin = srmMin;
	}

}
