package br.com.uece.frameworks.stockfy.repository;

import br.com.uece.frameworks.stockfy.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

/**
 * Create by Bruno Barbosa - 13/10/2018
 */
@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByDataBetween(Calendar inicio, Calendar fim);
}
