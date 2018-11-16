package br.com.uece.frameworks.stockfy.repository;

import br.com.uece.frameworks.stockfy.model.Categoria;
import br.com.uece.frameworks.stockfy.model.Fornecedor;
import br.com.uece.frameworks.stockfy.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by Bruno Barbosa - 13/10/2018
 */
@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Produto findByReferencia(Long referencia);

    Produto findByDescricao(String descricao);

    List<Produto> findByCategoria(Categoria categoria);

    List<Produto> findByFornecedor(Fornecedor fornecedor);

    @Query("Select p From Produto p left join fetch p.subProdutos sp Where p.id = :id")
    Produto findProdutoFetchSubProduto(@Param("id") Long id);
}
