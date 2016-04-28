package br.com.cerveja.model.cerveja;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

import br.com.cerveja.model.Images;

@Entity
public class Cerveja implements Serializable {
	private static final long serialVersionUID = -2420346134960559062L;
	@Id
	@GeneratedValue
	private Long id;
	private String brewerydbId;
	@NotBlank(message = "nome obrigatório")
	@Column(unique = true)
	private String name;
	@Column(length = 4000, columnDefinition = "varchar(4000)")
	private String description;
	private String abv;
	private String ibu;
	private Boolean isOrganic;
	@Enumerated(EnumType.STRING)
	private StatusCerveja status;
	private Date createDate;
	private String glass;
	private String servingTemperatureDisplay;
	@ManyToOne(fetch = FetchType.EAGER)
	private Cervejaria cervejaria;
	@ManyToOne(fetch = FetchType.EAGER)
	private Images rotulo;
	@ManyToOne(fetch = FetchType.EAGER)
	private Images garrafa;
	@ManyToMany
	@JoinTable(name = "Cerveja_Img", joinColumns = @JoinColumn(name = "Cerveja_id"), inverseJoinColumns = @JoinColumn(name = "Images_id", unique = false))
	private List<Images> outros;
	@ManyToOne(fetch = FetchType.EAGER)
	private Style style;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrewerydbId() {
		return brewerydbId;
	}

	public void setBrewerydbId(String brewerydbId) {
		this.brewerydbId = brewerydbId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAbv() {
		return abv;
	}

	public void setAbv(String abv) {
		this.abv = abv;
	}

	public String getIbu() {
		return ibu;
	}

	public void setIbu(String ibu) {
		this.ibu = ibu;
	}

	public Boolean getIsOrganic() {
		return isOrganic;
	}

	public void setIsOrganic(Boolean isOrganic) {
		this.isOrganic = isOrganic;
	}

	public StatusCerveja getStatus() {
		return status;
	}

	public void setStatus(StatusCerveja status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getGlass() {
		return glass;
	}

	public void setGlass(String glass) {
		this.glass = glass;
	}

	public String getServingTemperatureDisplay() {
		return servingTemperatureDisplay;
	}

	public void setServingTemperatureDisplay(String servingTemperatureDisplay) {
		this.servingTemperatureDisplay = servingTemperatureDisplay;
	}

	public Cervejaria getCervejaria() {
		return cervejaria;
	}

	public void setCervejaria(Cervejaria cervejaria) {
		this.cervejaria = cervejaria;
	}

	public Images getRotulo() {
		return rotulo;
	}

	public void setRotulo(Images rotulo) {
		this.rotulo = rotulo;
	}

	public Images getGarrafa() {
		return garrafa;
	}

	public void setGarrafa(Images garrafa) {
		this.garrafa = garrafa;
	}

	public List<Images> getOutros() {
		return outros;
	}

	public void setOutros(List<Images> outros) {
		this.outros = outros;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

}
