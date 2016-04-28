package br.com.cerveja.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Images {
	@Id @GeneratedValue
	private Long id;
	private String public_id;
	private String format;
	private byte[] bytes;
	private String url;
	private String secure_url;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPublic_id() {
		return public_id;
	}
	public void setPublic_id(String public_id) {
		this.public_id = public_id;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSecure_url() {
		return secure_url;
	}
	public void setSecure_url(String secure_url) {
		this.secure_url = secure_url;
	}
}
