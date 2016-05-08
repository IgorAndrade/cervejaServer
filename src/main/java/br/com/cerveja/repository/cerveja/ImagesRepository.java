package br.com.cerveja.repository.cerveja;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cerveja.model.Images;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {

}
