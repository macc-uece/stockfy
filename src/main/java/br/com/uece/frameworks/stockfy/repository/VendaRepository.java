package br.com.uece.frameworks.stockfy.repository;

import br.com.uece.frameworks.stockfy.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Create by Bruno Barbosa - 21/11/2018
 */
@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
}
