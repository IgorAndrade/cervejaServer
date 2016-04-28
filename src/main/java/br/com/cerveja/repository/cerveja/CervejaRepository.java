package br.com.cerveja.repository.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cerveja.model.cerveja.Cerveja;
@Repository
public interface CervejaRepository extends JpaRepository<Cerveja, Long> {
	
	
	@Query("from Cerveja c join c.cervejaria cervejaria where c.name like %:q% or cervejaria.name like %:q%")
	Page<Cerveja> pesquisar(@Param("q")String q,Pageable pageable);
	@Query("from Cerveja c join c.cervejaria cervejaria where c.name like %:q% or cervejaria.name like %:q%")
	List<Cerveja> pesquisar(@Param("q")String q);
	Cerveja findByBrewerydbId(String brewerydbId);

}
