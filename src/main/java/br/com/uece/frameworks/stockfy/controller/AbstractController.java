package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.service.GenericService;
import br.com.uece.frameworks.stockfy.util.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

public abstract class AbstractController<Entity extends BaseEntity<Long>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);
    private final GenericService<Entity> service;
    private static final String VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM = "pages/entity/form";

    @Autowired
    public AbstractController(GenericService<Entity> service) {
        this.service = service;
    }

    @GetMapping
    public String listEntities(ModelMap model) {
        List<Entity> results = this.service.findAll();
        model.put("entitys", results);
        return "pages/entity/entityList";
    }

    @GetMapping(value = "/new")
    public String initCreationForm(ModelMap modelMap) {
        Entity entity = getEntity();
        modelMap.put("entity", entity);
        return VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/new")
    public String processCreationForm(@ModelAttribute @Valid Entity entity, BindingResult result) {
        LOGGER.debug("Received request to create the {}", entity);
        if (result.hasErrors()){
            LOGGER.debug("Validation errors occurred in the process to create the entity {}", result.getAllErrors());
            return VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM;
        } else {
            this.service.save(entity);
            return "redirect:/admin/entitys/" + entity.getId();
        }
    }


    @GetMapping("/{entityId}/edit")
    public String initUpdateForm(@PathVariable("entityId") Long entityId, Model model) {
        LOGGER.debug("Received request to edit a entity by its id: {}", entityId);
        Entity entity = this.service.findById(entityId);
        LOGGER.debug("Received request to edit the {}", entity);
        model.addAttribute(entity);
        return VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{entityId}/edit")
    public String processUpdateForm(@ModelAttribute @Valid Entity entity, BindingResult result, @PathVariable("entityId") Long entityId) {
        LOGGER.debug("Received request to update the {}", entity);
        if (result.hasErrors()) {
            LOGGER.debug("Validation errors occurred in the process of update the entity {}", result.getAllErrors());
            return VIEWS_CATEGORIAS_CREATE_OR_UPDATE_FORM;
        } else {
            entity.setId(entityId);
            this.service.save(entity);
            return "redirect:/admin/entitys/{entityId}";
        }
    }

    @GetMapping("/{entityId}/delete")
    public String delete(@PathVariable("entityId") Long entityId){
        LOGGER.debug("Received request to delete a entity by its id: {}", entityId);
        this.service.deleteById(entityId);
        return "redirect:/admin/entitys";
    }

    /**
     * Custom handler for displaying an entity.
     *
     * @param entityId the ID of the entity to display
     * @return a ModelMap with the model attributes for the view
     */
    @GetMapping("/{entityId}")
    public ModelAndView show(@PathVariable("entityId") Long entityId) {
        ModelAndView mav = new ModelAndView("pages/entity/entityDetails");
        mav.addObject(this.service.findById(entityId));
        return mav;
    }

    protected abstract Entity getEntity();
}
