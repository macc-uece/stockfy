package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Estabelecimento;
import br.com.uece.frameworks.stockfy.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create by Bruno Barbosa - 15/11/2018
 */
@Controller
@RequestMapping("/admin/estabelecimentos")
public class EstabelecimentoController extends AbstractController<Estabelecimento> {

    private static final String VIEWS_BASE_PATH = "pages/estabelecimento";
    private static final String ESTABELECIMENTO_REQUEST_URL = "/admin/estabelecimentos";

    @Autowired
    public EstabelecimentoController(GenericService<Estabelecimento> service) {
        super(service, VIEWS_BASE_PATH, ESTABELECIMENTO_REQUEST_URL);
    }

    @Override
    protected Estabelecimento getEntity() {
        return new Estabelecimento();
    }
}
