package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Categoria;
import br.com.uece.frameworks.stockfy.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categorias")
public class CategoriaController extends AbstractController<Categoria>{
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaController.class);
    private static final String VIEWS_BASE_PATH = "pages/categoria";
    private static final String CATEGORIA_REQUEST_URL = "/admin/categorias";

    public CategoriaController(GenericService<Categoria> service) {
        super(service, VIEWS_BASE_PATH, CATEGORIA_REQUEST_URL);
    }


    @Override
    protected Categoria getEntity() {
        return new Categoria();
    }
}
