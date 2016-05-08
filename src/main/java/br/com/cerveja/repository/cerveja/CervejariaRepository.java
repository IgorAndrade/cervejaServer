package br.com.cerveja.repository.cerveja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cerveja.model.cerveja.Cervejaria;
import br.com.cerveja.model.cerveja.StatusCerveja;

public interface CervejariaRepository extends JpaRepository<Cervejaria, Long> {
	
	Cervejaria findByBrewerydbId(String brewerydbId);
	
	Page<Cervejaria> findByNameIgnoreCaseContainingOrStatus(String name, StatusCerveja status,Pageable pageable);
}
