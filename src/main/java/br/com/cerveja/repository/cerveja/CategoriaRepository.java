package br.com.cerveja.repository.cerveja;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cerveja.controller.Filter;
import br.com.cerveja.model.cerveja.Category;
import br.com.cerveja.model.cerveja.Cerveja;
@Repository 
public interface CategoriaRepository extends JpaRepository<Category, Long>{
	public Category findByName(String name);
	
	@Query("select c from Category c where (c.name like %:#{#param.categoria}% or :#{#param.categoria} =null)")
	Page<Category> pesquisarCat(@Param("param")Filter filter,Pageable pageable);
}
