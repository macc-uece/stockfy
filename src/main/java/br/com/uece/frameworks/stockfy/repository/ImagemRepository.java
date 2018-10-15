package br.com.uece.frameworks.stockfy.repository;

import br.com.uece.frameworks.stockfy.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Create by Bruno Barbosa - 15/10/2018
 */
@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {
}
