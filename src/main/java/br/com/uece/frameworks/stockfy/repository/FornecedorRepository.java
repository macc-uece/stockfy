package br.com.uece.frameworks.stockfy.repository;

import br.com.uece.frameworks.stockfy.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}
