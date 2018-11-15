package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Estabelecimento;
import br.com.uece.frameworks.stockfy.service.EstabelecimentoService;
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
public class EstabelecimentoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstabelecimentoController.class);
    private static final String VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM = "pages/estabelecimento/form";

    private final EstabelecimentoService estabelecimentoService;

    @Autowired
    public EstabelecimentoController(EstabelecimentoService estabelecimentoService) {
        this.estabelecimentoService = estabelecimentoService;
    }

    @GetMapping
    public String listEstabelecimentos(ModelMap model) {
        List<Estabelecimento> results = this.estabelecimentoService.findAll();
        model.put("estabelecimentos", results);
        return "pages/estabelecimento/list";
    }

    @GetMapping(value = "/new")
    public String initCreationForm(ModelMap modelMap) {
        Estabelecimento estabelecimento = new Estabelecimento();
        modelMap.put("estabelecimento", estabelecimento);
        return VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/new")
    public String processCreationForm(@ModelAttribute @Valid Estabelecimento estabelecimento, BindingResult result) {
        LOGGER.debug("Received request to create the {}", estabelecimento);
        if (result.hasErrors()) {
            LOGGER.debug("Validation errors occurred in the process to create the category {}", result.getAllErrors());
            return VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM;
        } else {
            this.estabelecimentoService.save(estabelecimento);
            return "redirect:/admin/estabelecimentos/" + estabelecimento.getId();
        }
    }


    @GetMapping("/{estabelecimentoId}/edit")
    public String initUpdateForm(@PathVariable("estabelecimentoId") Long estabelecimentoId, Model model) {
        LOGGER.debug("Received request to edit a category by its id: {}", estabelecimentoId);
        Estabelecimento estabelecimento = this.estabelecimentoService.findById(estabelecimentoId);
        LOGGER.debug("Received request to edit the {}", estabelecimento);
        model.addAttribute(estabelecimento);
        return VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{estabelecimentoId}/edit")
    public String processUpdateForm(@ModelAttribute @Valid Estabelecimento estabelecimento, BindingResult result, @PathVariable("estabelecimentoId") Long estabelecimentoId) {
        LOGGER.debug("Received request to update the {}", estabelecimento);
        if (result.hasErrors()) {
            LOGGER.debug("Validation errors occurred in the process of update the category {}", result.getAllErrors());
            return VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM;
        } else {
            estabelecimento.setId(estabelecimentoId);
            this.estabelecimentoService.save(estabelecimento);
            return "redirect:/admin/estabelecimentos/{estabelecimentoId}";
        }
    }

    @GetMapping("/{estabelecimentoId}/delete")
    public String delete(@PathVariable("estabelecimentoId") Long estabelecimentoId) {
        LOGGER.debug("Received request to delete a category by its id: {}", estabelecimentoId);
        this.estabelecimentoService.deleteById(estabelecimentoId);
        return "redirect:/admin/estabelecimentos";
    }

    @GetMapping("/{estabelecimentoId}")
    public ModelAndView show(@PathVariable("estabelecimentoId") Long estabelecimentoId) {
        ModelAndView mav = new ModelAndView("pages/estabelecimento/details");
        mav.addObject(this.estabelecimentoService.findById(estabelecimentoId));
        return mav;
    }
}
