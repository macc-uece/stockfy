package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.enums.TipoPagamento;
import br.com.uece.frameworks.stockfy.model.*;
import br.com.uece.frameworks.stockfy.service.EstabelecimentoService;
import br.com.uece.frameworks.stockfy.service.GenericService;
import br.com.uece.frameworks.stockfy.service.ProdutoService;
import br.com.uece.frameworks.stockfy.service.SubProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Create by Bruno Barbosa - 21/11/2018
 */

@Controller
@RequestMapping(value = {"/admin/vendas", "/funcionario/vendas"})
public class VendaController extends AbstractController<Venda> {

    private final EstabelecimentoService estabelecimentoService;
    private final ProdutoService produtoService;
    private final SubProdutoService subProdutoService;

    private static final String VIEWS_BASE_PATH = "pages/venda";
    private static final String VENDA_REQUEST_URL = "/admin/vendas";

    @Autowired
    public VendaController(GenericService<Venda> service, EstabelecimentoService estabelecimentoService, ProdutoService produtoService, SubProdutoService subProdutoService) {
        super(service, VIEWS_BASE_PATH, VENDA_REQUEST_URL);
        this.estabelecimentoService = estabelecimentoService;
        this.produtoService = produtoService;
        this.subProdutoService = subProdutoService;
    }

    @Override
    protected Venda getEntity() {
        return new Venda();
    }

    @Override
    public String initCreationForm(ModelMap modelMap) {
        modelMap.put("estabelecimentos", estabelecimentoService.findAll());
        return super.initCreationForm(modelMap);
    }

    @PostMapping(value = "/new/venda")
    @ResponseBody
    public String salvarVendas(@RequestBody List<Map<String, String>> vendas) {
        List<Venda> vendasSalvar = new ArrayList<>();
        if (vendas != null) {
            vendas.forEach(venda -> {
                try {
                    SubProduto subProduto = subProdutoService.findById(Long.parseLong(venda.get("subproduto")));
                    Integer quantidade = Integer.parseInt(venda.get("quantidade"));
                    BigDecimal total = new BigDecimal(venda.get("total"));
                    Estabelecimento estabelecimento = estabelecimentoService.findById(Long.parseLong(venda.get("estabelecimento")));
                    Calendar hoje = Calendar.getInstance();
                    hoje.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(venda.get("data")));

                    Venda entity = new Venda();
                    entity.setEstabelecimento(estabelecimento);
                    entity.setTipoPagamento(TipoPagamento.DINHEIRO);
                    entity.setDataVenda(hoje);
                    entity.setTotalVenda(total);
                    entity.setDescricaoProduto(subProduto.getDescricaoCompleta());
                    entity.setQuantidadeItens(quantidade);
                    vendasSalvar.add(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            try {
                getService().saveAll(vendasSalvar);
                return "200";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "500";
    }

    @Override
    public ModelAndView show(@PathVariable("entityId") Long entityId, ModelMap modelMap) {
        Venda venda = getService().findById(entityId);
        try {
            Long ref = Long.parseLong(venda.getDescricaoProduto().split("-")[0].trim());
            Produto produto = produtoService.findByReferencia(ref);
            modelMap.put("imagem", produto.getImagem());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.show(entityId, modelMap);
    }
}
