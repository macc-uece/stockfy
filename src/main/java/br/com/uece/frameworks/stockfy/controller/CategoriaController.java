package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Categoria;
import br.com.uece.frameworks.stockfy.service.CategoriaService;
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

@Controller
@RequestMapping("/admin/categorias")
public class CategoriaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaController.class);
    private final CategoriaService categoriaService;
    private static final String VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM = "pages/categoria/form";

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listCategorias(ModelMap model) {
        List<Categoria> results = this.categoriaService.findAll();
        model.put("categorias", results);
        return "pages/categoria/categoriaList";
    }

    @GetMapping(value = "/new")
    public String initCreationForm(ModelMap modelMap) {
        Categoria categoria = new Categoria();
        modelMap.put("categoria", categoria);
        return VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/new")
    public String processCreationForm(@ModelAttribute @Valid Categoria categoria, BindingResult result) {
        LOGGER.debug("Received request to create the {}", categoria);
        if (result.hasErrors()){
            LOGGER.debug("Validation errors occurred in the process to create the category {}", result.getAllErrors());
            return VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM;
        } else {
            this.categoriaService.save(categoria);
            return "redirect:/admin/categorias/" + categoria.getId();
        }
    }


    @GetMapping("/{categoriaId}/edit")
    public String initUpdateForm(@PathVariable("categoriaId") Long categoriaId, Model model) {
        LOGGER.debug("Received request to edit a category by its id: {}", categoriaId);
        Categoria categoria = this.categoriaService.findById(categoriaId);
        LOGGER.debug("Received request to edit the {}", categoria);
        model.addAttribute(categoria);
        return VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{categoriaId}/edit")
    public String processUpdateForm(@ModelAttribute @Valid Categoria categoria, BindingResult result, @PathVariable("categoriaId") Long categoriaId) {
        LOGGER.debug("Received request to update the {}", categoria);
        if (result.hasErrors()) {
            LOGGER.debug("Validation errors occurred in the process of update the category {}", result.getAllErrors());
            return VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM;
        } else {
            categoria.setId(categoriaId);
            this.categoriaService.save(categoria);
            return "redirect:/admin/categorias/{categoriaId}";
        }
    }

    @GetMapping("/{categoriaId}/delete")
    public String delete(@PathVariable ("categoriaId") Long categoriaId){
        LOGGER.debug("Received request to delete a category by its id: {}", categoriaId);
        this.categoriaService.deleteById(categoriaId);
        return "redirect:/admin/categorias";
    }
    
    /**
     * Custom handler for displaying an category.
     *
     * @param categoriaId the ID of the category to display
     * @return a ModelMap with the model attributes for the view
     */
    @GetMapping("/{categoriaId}")
    public ModelAndView show(@PathVariable("categoriaId") Long categoriaId) {
        ModelAndView mav = new ModelAndView("pages/categoria/categoriaDetails");
        mav.addObject(this.categoriaService.findById(categoriaId));
        return mav;
    }
}
