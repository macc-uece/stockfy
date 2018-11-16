package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Despesa;
import br.com.uece.frameworks.stockfy.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/despesas")
public class DespesaController extends AbstractController<Despesa> {

    private static final String VIEWS_BASE_PATH = "pages/despesa";
    private static final String ESTABELECIMENTO_REQUEST_URL = "/admin/despesas";

    @Autowired
    public DespesaController(GenericService<Despesa> service) {
        super(service, VIEWS_BASE_PATH, ESTABELECIMENTO_REQUEST_URL);
    }

    @Override
    protected Despesa getEntity() {
        return new Despesa();
    }
}
