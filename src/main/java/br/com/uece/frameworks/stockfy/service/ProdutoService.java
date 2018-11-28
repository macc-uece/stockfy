package br.com.uece.frameworks.stockfy.service;

import br.com.uece.frameworks.stockfy.model.Produto;
import br.com.uece.frameworks.stockfy.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

/**
 * Create by Bruno Barbosa - 15/11/2018
 */

@Service
public class ProdutoService extends GenericService<Produto> {

    public Produto findByFetchSubProduto(Long id) {
        ProdutoRepository produtoRepository = (ProdutoRepository) repository;
        return produtoRepository.findProdutoFetchSubProduto(id);
    }

    public Produto findByReferencia(Long ref) {
        return ((ProdutoRepository) repository).findByReferencia(ref);
    }
}
