package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Imagem;
import br.com.uece.frameworks.stockfy.model.Produto;
import br.com.uece.frameworks.stockfy.model.SubProduto;
import br.com.uece.frameworks.stockfy.service.*;
import br.com.uece.frameworks.stockfy.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * Create by Bruno Barbosa - 15/11/2018
 */
@Controller
@RequestMapping("/admin/produtos")
public class ProdutoController extends AbstractController<Produto> {

    private final CategoriaService categoriaService;
    private final FornecedorService fornecedorService;
    private final ImagemService imagemService;
    private final SubProdutoService subProdutoService;

    private final GenericService<Produto> service;

    private static final String VIEWS_BASE_PATH = "pages/produto";
    private static final String PRODUTO_REQUEST_URL = "/admin/produtos";

    @Autowired
    public ProdutoController(GenericService<Produto> service, CategoriaService categoriaService, FornecedorService fornecedorService, ImagemService imagemService, SubProdutoService subProdutoService) {
        super(service, VIEWS_BASE_PATH, PRODUTO_REQUEST_URL);
        this.service = service;
        this.categoriaService = categoriaService;
        this.fornecedorService = fornecedorService;
        this.imagemService = imagemService;
        this.subProdutoService = subProdutoService;
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
        entity.setImagem(getImg(file));
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
        entity.setImagem(getImg(file));
        return super.processUpdateForm(entity, result, entityId, model, file);
    }

    @Override
    public ModelAndView show(@PathVariable("entityId") Long entityId, ModelMap modelMap) {
        modelMap.put("categorias", categoriaService.findAll());
        modelMap.put("fornecedores", fornecedorService.findAll());
        return super.show(entityId, modelMap);
    }

    private Imagem getImg(MultipartFile file) {
        try {
            if (ImageIO.read(file.getInputStream()) != null) {
                Imagem imagem = new Imagem();
                imagem.setNome(file.getOriginalFilename());
                imagem.setImage(ImageUtil.getImage(file));
                imagem.setExtensao(file.getOriginalFilename().split("\\.")[1]);
                return imagemService.save(imagem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/subProduto/add")
    public String addSubProduto(ModelMap modelMap, @RequestParam Map<String, String> idProduto) {
        SubProduto subProduto = new SubProduto();
        modelMap.put("subProduto", subProduto);
        modelMap.put("idProduto", idProduto.get("idProduto"));
        return "fragments/modal :: modalSubProduto";
    }

    @RequestMapping("/subProduto/{id}")
    public String addSubProduto(ModelMap modelMap, @RequestParam Map<String, String> idProduto, @PathVariable("id") Long idSubProduto) {
        SubProduto subProduto = subProdutoService.findById(idSubProduto);
        modelMap.put("subProduto", subProduto);
        modelMap.put("idProduto", idProduto.get("idProduto"));
        return "fragments/modal :: modalSubProduto";
    }

    @RequestMapping("/subProduto/save")
    public String saveSubProduto(@RequestParam("idProduto") Long idProduto, @ModelAttribute("subProduto") SubProduto subProduto) {
        try {
            SubProduto persist;
            if (subProduto.getId() == null) {
                persist = new SubProduto();
            } else {
                persist = subProdutoService.findById(subProduto.getId());
            }
            persist.setCodBarras(subProduto.getCodBarras());
            persist.setCor(subProduto.getCor());
            persist.setTamanho(subProduto.getTamanho());
            persist.setProduto(((ProdutoService) service).findByFetchSubProduto(idProduto));
            subProdutoService.save(persist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + PRODUTO_REQUEST_URL + "/" + idProduto + "/edit";
    }

    @RequestMapping("/subProdutos/delete/{p}/{sub}")
    public String deleteSubProduto(@PathVariable("p") Long idProduto, @PathVariable("sub") Long idSubProduto) {
        try {
            subProdutoService.deleteById(idSubProduto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + PRODUTO_REQUEST_URL + "/" + idProduto + "/edit";
    }

}
