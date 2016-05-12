package br.com.cerveja.repository.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cerveja.controller.Filter;
import br.com.cerveja.model.cerveja.Cerveja;
import br.com.cerveja.model.cerveja.StatusCerveja;
@Repository
public interface CervejaRepository extends JpaRepository<Cerveja, Long> {
	
	
	@Query("select c from Cerveja c join c.cervejaria cervejaria where c.name like %:q% or cervejaria.name like %:q%")
	Page<Cerveja> pesquisar(@Param("q")String q,Pageable pageable);
	@Query("select c from Cerveja c join c.cervejaria cervejaria where c.name like %:q% or cervejaria.name like %:q%")
	List<Cerveja> pesquisar(@Param("q")String q);
	Cerveja findByBrewerydbId(String brewerydbId);
	@Query("select c from Cerveja c join c.cervejaria cervejaria where c.name like %:name% and cervejaria.name like %:cervejaria% and (c.status=:status or :status=null)")
	Page<Cerveja> listar(@Param("name")String name,@Param("cervejaria")String cervejaria,@Param("status")StatusCerveja filterStatus,Pageable pageable);
	@Query("select c from Cerveja c join c.cervejaria cervejaria where (c.name like %:#{#param.cerveja}% or :#{#param.cerveja} =null) and (cervejaria.name like %:#{#param.cervejaria}% or :#{#param.cervejaria} =null) and (c.status=:#{#param.status} or :#{#param.status}=null)")
	Page<Cerveja> pesquisar(@Param("param")Filter filter,Pageable pageable);
	

}
