package br.com.cerveja.repository.cerveja;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cerveja.model.cerveja.Cerveja;
import br.com.cerveja.model.cerveja.Cervejaria;
@Repository
public interface CervejariaRepository extends JpaRepository<Cervejaria, Long> {
	
	Cervejaria findByBrewerydbId(String brewerydbId);
}
