package br.com.cerveja.model;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

public class Cerveja {
	@Id @GeneratedValue
	private Long id;
	private String brewerydbId;
	@NotBlank(message="nome obrigatório")
	private String name;
	private String description;
	private String abv;
	private String ibu;
	private Boolean isOrganic;
	@Enumerated(EnumType.STRING)
	private StatusCerveja status;
	private Date createDate;
	private String glass;
	private String servingTemperatureDisplay;
	
}
