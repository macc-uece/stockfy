package br.com.uece.frameworks.stockfy.repository;

import br.com.uece.frameworks.stockfy.model.Produto;
import br.com.uece.frameworks.stockfy.model.SubProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by Bruno Barbosa - 15/11/2018
 */
@Repository
public interface SubProdutoRepository extends JpaRepository<SubProduto,Long> {

    List<SubProduto> findByProduto(Produto produto);
}
