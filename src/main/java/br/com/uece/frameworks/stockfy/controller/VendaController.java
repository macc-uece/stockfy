package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Venda;
import br.com.uece.frameworks.stockfy.service.GenericService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create by Bruno Barbosa - 21/11/2018
 */

@Controller
@RequestMapping(value = {"/admin/vendas", "/funcionario/vendas"})
public class VendaController extends AbstractController<Venda> {

    private static final String VIEWS_BASE_PATH = "pages/venda";
    private static final String VENDA_REQUEST_URL = "/admin/vendas";

    public VendaController(GenericService<Venda> service) {
        super(service, VIEWS_BASE_PATH, VENDA_REQUEST_URL);
    }

    @Override
    protected Venda getEntity() {
        return new Venda();
    }
}
