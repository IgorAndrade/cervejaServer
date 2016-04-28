package br.com.cerveja.repository.cerveja;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cerveja.model.cerveja.Category;
@Repository 
public interface CategoriaRepository extends JpaRepository<Category, Long>{
	public Category findByName(String name);
}
