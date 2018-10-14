package br.com.uece.frameworks.stockfy.repository;

import br.com.uece.frameworks.stockfy.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Create by Bruno Barbosa - 14/10/2018
 */
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}
