package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Estabelecimento;
import br.com.uece.frameworks.stockfy.model.Fornecedor;
import br.com.uece.frameworks.stockfy.service.EstabelecimentoService;
import br.com.uece.frameworks.stockfy.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * Create by Bruno Barbosa - 15/11/2018
 */
@Controller
@RequestMapping("/admin/estabelecimentos")
public class EstabelecimentoController extends AbstractController<Estabelecimento>{

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
