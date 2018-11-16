package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Imagem;
import br.com.uece.frameworks.stockfy.model.Produto;
import br.com.uece.frameworks.stockfy.service.CategoriaService;
import br.com.uece.frameworks.stockfy.service.FornecedorService;
import br.com.uece.frameworks.stockfy.service.GenericService;
import br.com.uece.frameworks.stockfy.service.ImagemService;
import br.com.uece.frameworks.stockfy.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Create by Bruno Barbosa - 15/11/2018
 */
@Controller
@RequestMapping("/admin/produtos")
public class ProdutoController extends AbstractController<Produto> {

    private final CategoriaService categoriaService;
    private final FornecedorService fornecedorService;
    private final ImagemService imagemService;

    private static final String VIEWS_BASE_PATH = "pages/produto";
    private static final String ESTABELECIMENTO_REQUEST_URL = "/admin/produtos";

    @Autowired
    public ProdutoController(GenericService<Produto> service, CategoriaService categoriaService, FornecedorService fornecedorService, ImagemService imagemService) {
        super(service, VIEWS_BASE_PATH, ESTABELECIMENTO_REQUEST_URL);
        this.categoriaService = categoriaService;
        this.fornecedorService = fornecedorService;
        this.imagemService = imagemService;
    }

    @Override
    protected Produto getEntity() {
        return new Produto();
    }

    @Override
    public String initCreationForm(ModelMap modelMap) {
        modelMap.put("categorias", categoriaService.findAll());
        modelMap.put("fornecedores", fornecedorService.findAll());
        return super.initCreationForm(modelMap);
    }

    @Override
    public String processCreationForm(@Valid Produto entity, BindingResult result, ModelMap model, MultipartFile file) {
        try {
            if (ImageIO.read(file.getInputStream()) != null) {
                Imagem imagem = new Imagem();
                imagem.setNome(file.getOriginalFilename());
                imagem.setImage(ImageUtil.getImage(file));
                imagem.setExtensao(file.getOriginalFilename().split("\\.")[1]);
                imagemService.save(imagem);
                entity.setImagem(imagem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.processCreationForm(entity, result, model, file);
    }

    @Override
    public String initUpdateForm(@PathVariable("entityId") Long entityId, ModelMap model) {
        model.put("categorias", categoriaService.findAll());
        model.put("fornecedores", fornecedorService.findAll());
        return super.initUpdateForm(entityId, model);
    }

    @Override
    public String processUpdateForm(@Valid Produto entity, BindingResult result, @PathVariable("entityId") Long entityId, ModelMap model, MultipartFile file) {

        return super.processUpdateForm(entity, result, entityId, model, file);
    }
}
