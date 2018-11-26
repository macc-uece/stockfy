package br.com.uece.frameworks.stockfy.service;

import br.com.uece.frameworks.stockfy.model.Produto;
import br.com.uece.frameworks.stockfy.model.SubProduto;
import br.com.uece.frameworks.stockfy.model.Venda;
import br.com.uece.frameworks.stockfy.repository.ProdutoRepository;
import br.com.uece.frameworks.stockfy.repository.SubProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Create by Bruno Barbosa - 21/11/2018
 */
@Service
@RequestMapping("/api/venda")
public class VendaService extends GenericService<Venda> {

    private final ProdutoRepository produtoRepository;
    private final SubProdutoRepository subProdutoRepository;

    @Autowired
    public VendaService(ProdutoRepository produtoRepository, SubProdutoRepository subProdutoRepository) {
        this.produtoRepository = produtoRepository;
        this.subProdutoRepository = subProdutoRepository;
    }

    @RequestMapping(value = "/getProdutos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, Object> getProdutos(@RequestParam String referencia) {
        List<Produto> produtos = produtoRepository.findRefAndPrecoByRefContains(referencia + "%");
        if(produtos != null){
            int qtd = produtos.size() > 20 ? 20 :produtos.size();
            Map<String, Object> json = new HashMap<>();
            List<Map<String, Object>> suggestions = new LinkedList<>();
            produtos.subList(0,qtd).forEach(prd ->{
                try {
                    Map<String, Object> dados = new LinkedHashMap<>();
                    dados.put("value", String.valueOf(prd.getReferencia()));
                    dados.put("data", String.valueOf(prd.getPreco()));
                    suggestions.add(dados);
                } catch (Exception e) {}
            });
            json.put("query", referencia);
            json.put("suggestions", suggestions);
            return json;
        }
        return new HashMap<>();
    }

    @RequestMapping("/subProdutos")
    @ResponseBody
    public List<Map<String, String>> getSubProdutos(@RequestParam("refBusca") String referencia) {
            Produto produto = produtoRepository.findByReferencia(Long.parseLong(referencia));
            if (produto != null) {
                List<SubProduto> subProdutos = subProdutoRepository.findByProduto(produto);
                if (subProdutos != null) {
                    List<Map<String, String>> retorno = new LinkedList<>();
                    Map<String, String> select = new HashMap<>();
                    select.put("0","Selecione");
                    retorno.add(select);
                    subProdutos.sort(Comparator.comparing(SubProduto::getCor).thenComparing(SubProduto::getTamanho));
                    for (SubProduto subProduto : subProdutos) {
                        Map<String, String> data = new HashMap<>();
                        data.put(String.valueOf(subProduto.getId()), subProduto.getDescricaoSubProduto());
                        retorno.add(data);
                    }
                    return retorno;
                }
            }
        return new LinkedList<>();
    }
}
