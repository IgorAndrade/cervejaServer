package br.com.cerveja.model.cerveja;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.rest.core.annotation.RestResource;

import br.com.cerveja.model.Images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@RestResource(exported = false)
public class Cervejaria implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique=true)
	private String brewerydbId;
	private Date createDate;
	@Column(length=4000,columnDefinition="varchar(4000)")
   	private String description;
   	private String established;
   	@ManyToOne(fetch=FetchType.EAGER)
   	private Images images;
   	private String isOrganic;
   	@Column(unique=true)
   	private String name;
   	@Enumerated(EnumType.STRING)
   	private StatusCerveja status;
   	private String website;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description.length() > 3900)
			this.description = description.substring(0, 3900) + "...";
		else
			this.description = description;
	}

	public String getEstablished() {
		return established;
	}

	public void setEstablished(String established) {
		this.established = established;
	}

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public String getIsOrganic() {
		return isOrganic;
	}

	public void setIsOrganic(String isOrganic) {
		this.isOrganic = isOrganic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StatusCerveja getStatus() {
		return status;
	}

	public void setStatus(StatusCerveja status) {
		this.status = status;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}


	public String getBrewerydbId() {
		return brewerydbId;
	}

	public void setBrewerydbId(String brewerydbId) {
		this.brewerydbId = brewerydbId;
	}
}
