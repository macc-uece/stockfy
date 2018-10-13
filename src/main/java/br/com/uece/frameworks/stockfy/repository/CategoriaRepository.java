package br.com.uece.frameworks.stockfy.repository;

import br.com.uece.frameworks.stockfy.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CategoriaRepository
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}