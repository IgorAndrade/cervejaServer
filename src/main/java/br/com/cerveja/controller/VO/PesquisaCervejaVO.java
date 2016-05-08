package br.com.cerveja.controller.VO;

import com.sosv.breweryDB.connector.entity.beer.Beer;

import br.com.cerveja.model.cerveja.Cerveja;

public class PesquisaCervejaVO {
	private Long id;
	private String brewerydbId;
	private String name;
	private String cervejaria;
	private String style;
	private String img;
	
	
	
	public PesquisaCervejaVO(Cerveja cerveja) {
		this.id=cerveja.getId();
		this.brewerydbId=cerveja.getBrewerydbId();
		this.name=cerveja.getName();
		this.cervejaria=cerveja.getCervejaria().getName();
		this.style=cerveja.getStyle().getName();
		if(cerveja.getRotulo()!=null)
			this.img=cerveja.getRotulo().getUrl();
	}
	public PesquisaCervejaVO(Beer beer) {
		this.brewerydbId=beer.getId();
		this.name=beer.getName();
		if(beer.getBreweries()!=null)
		this.cervejaria=beer.getBreweries().iterator().next().getName();
		if(beer.getStyle()!=null)
		this.style=beer.getStyle().getName();
		if(beer.getLabels()!=null)
			this.img=beer.getLabels().getMedium();
	}
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
	public String getCervejaria() {
		return cervejaria;
	}
	public void setCervejaria(String cervejaria) {
		this.cervejaria = cervejaria;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PesquisaCervejaVO other = (PesquisaCervejaVO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
