package br.com.cerveja.repository.cerveja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cerveja.model.cerveja.Style;
@Repository
public interface StyleRepository extends JpaRepository<Style, Long> {
	public Style findByName(String name);
	
	@Query("select s from Style s join s.category c where s.name like %:name% and c.name like %:category% ")
	public Page<Style> findByNameOrCategory(@Param("name")String name,@Param("category")String category,Pageable pageable);
}
