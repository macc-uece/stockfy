package br.com.uece.frameworks.stockfy.repository;

import br.com.uece.frameworks.stockfy.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

/**
 * Create by Bruno Barbosa - 13/10/2018
 */
@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

    List<Gasto> findByDataBetween(Calendar inicio, Calendar fim);
}
