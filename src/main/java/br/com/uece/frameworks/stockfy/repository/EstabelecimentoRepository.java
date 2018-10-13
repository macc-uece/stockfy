package br.com.uece.frameworks.stockfy.repository;

import br.com.uece.frameworks.stockfy.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Create by Bruno Barbosa - 13/10/2018
 */
@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {
}
