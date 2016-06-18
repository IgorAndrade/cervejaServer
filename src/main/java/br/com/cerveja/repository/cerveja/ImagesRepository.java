package br.com.cerveja.repository.cerveja;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cerveja.model.Images;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {
	
	@Query("from Images where public_id = :publicid")
	public Images findByPublic_Id(@Param("publicid")String public_id);
}
