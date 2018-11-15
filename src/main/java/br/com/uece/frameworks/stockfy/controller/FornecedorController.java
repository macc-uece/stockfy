package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Fornecedor;
import br.com.uece.frameworks.stockfy.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/fornecedores")
public class FornecedorController extends AbstractController<Fornecedor> {

    private static final String VIEWS_BASE = "pages/fornecedor";
    private static final String FORNECEDOR_REQUEST_URL = "/admin/fornecedores";

    @Autowired
    public FornecedorController(GenericService<Fornecedor> service) {
        super(service, VIEWS_BASE, FORNECEDOR_REQUEST_URL);
    }

    @Override
    protected Fornecedor getEntity() {
        return new Fornecedor();
    }
}
