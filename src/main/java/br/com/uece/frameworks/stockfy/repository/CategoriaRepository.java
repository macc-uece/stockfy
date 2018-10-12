package br.com.uece.frameworks.stockfy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uece.frameworks.stockfy.model.Categoria;

/**
 * CategoriaRepository
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    
}