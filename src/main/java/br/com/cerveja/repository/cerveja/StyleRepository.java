package br.com.cerveja.repository.cerveja;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cerveja.model.cerveja.Style;

public interface StyleRepository extends JpaRepository<Style, Long> {
	public Style findByName(String name);
}
