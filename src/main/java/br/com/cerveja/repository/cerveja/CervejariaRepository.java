package br.com.cerveja.repository.cerveja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cerveja.controller.Filter;
import br.com.cerveja.model.cerveja.Cervejaria;

public interface CervejariaRepository extends JpaRepository<Cervejaria, Long> {
	
	Cervejaria findByBrewerydbId(String brewerydbId);
	
	Page<Cervejaria> findByNameIgnoreCaseContaining(String name, Pageable pageable);
	
	@Query("select c from Cervejaria c where (c.name like %:#{#param.cervejaria}% or :#{#param.cervejaria} = null) and (c.status = :#{#param.status} or :#{#param.status} =null)")
	Page<Cervejaria> pesquisar(@Param("param")Filter param, Pageable pageable);
	
}
