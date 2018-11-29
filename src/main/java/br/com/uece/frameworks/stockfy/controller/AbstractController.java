package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.service.GenericService;
import br.com.uece.frameworks.stockfy.util.BaseEntity;
import br.com.uece.frameworks.stockfy.util.pagination.PaginationValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

public abstract class AbstractController<Entity extends BaseEntity<Long>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);
    private final GenericService<Entity> service;

    private final String VIEWS_BASE_PATH;
    private final String REQUEST_MAPPING_PATH;

    @Autowired
    private PaginationValue paginationValue;

    @Autowired
    public AbstractController(GenericService<Entity> service, String VIEWS_BASE_PATH, String REQUEST_MAPPING_PATH) {
        this.service = service;
        this.VIEWS_BASE_PATH = VIEWS_BASE_PATH;
        this.REQUEST_MAPPING_PATH = REQUEST_MAPPING_PATH;
    }

    @GetMapping
    public String listEntities(ModelMap model,
                               @RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
                               @RequestParam(value = "page", required = false) Optional<Integer> page) {
        Page<Entity> results;
        if (pageSize.isPresent() && page.isPresent()) {
            results = this.service.findPage(paginationValue.getPositionPagination(pageSize, page, Sort.by("id").descending()));
        } else {
            results = this.service.findAll(paginationValue.getPositionPagination(Optional.of(10), Optional.of(1)));
        }
        model.put("entities", results);
        model.put("selectedPageSize", paginationValue.getEvalPageSize());
        model.put("pageSizes", paginationValue.getPAGE_SIZES());
        model.put("pager", paginationValue.getPagerView(results.getTotalPages(), results.getNumber()));
        return VIEWS_BASE_PATH + "/list";
    }

    @GetMapping(value = "/new")
    public String initCreationForm(ModelMap modelMap) {
        Entity entity = getEntity();
        modelMap.put("entity", entity);
        return VIEWS_BASE_PATH + "/form";
    }

    @PostMapping(value = "/new")
    public String processCreationForm(@ModelAttribute("entity") @Valid Entity entity, BindingResult result, ModelMap model, @RequestParam(required = false) MultipartFile file) {
        LOGGER.debug("Received request to create the {}", entity);
        if (result.hasErrors()) {
            LOGGER.debug("Validation errors occurred in the process to create the entity {}", result.getAllErrors());
            model.put("entity", entity);
            return VIEWS_BASE_PATH + "/form";
        } else {
            this.service.save(entity);
            return "redirect:" + REQUEST_MAPPING_PATH + "/" + entity.getId();
        }
    }


    @GetMapping("/{entityId}/edit")
    public String initUpdateForm(@PathVariable("entityId") Long entityId, ModelMap model) {
        LOGGER.debug("Received request to edit a entity by its id: {}", entityId);
        Entity entity = this.service.findById(entityId);
        LOGGER.debug("Received request to edit the {}", entity);
        model.put("entity", entity);
        return VIEWS_BASE_PATH + "/form";
    }

    @PostMapping("/{entityId}/edit")
    public String processUpdateForm(@ModelAttribute @Valid Entity entity, BindingResult result, @PathVariable("entityId") Long entityId, ModelMap model, @RequestParam(required = false) MultipartFile file) {
        LOGGER.debug("Received request to update the {}", entity);
        if (result.hasErrors()) {
            LOGGER.debug("Validation errors occurred in the process of update the entity {}", result.getAllErrors());
            model.put("entity", entity);
            return VIEWS_BASE_PATH + "/form";
        } else {
            entity.setId(entityId);
            this.service.save(entity);
            return "redirect:" + REQUEST_MAPPING_PATH + "/{entityId}";
        }
    }

    @GetMapping("/{entityId}/delete")
    public String delete(@PathVariable("entityId") Long entityId) {
        LOGGER.debug("Received request to delete a entity by its id: {}", entityId);
        this.service.deleteById(entityId);
        return "redirect:" + REQUEST_MAPPING_PATH;
    }

    /**
     * Custom handler for displaying an entity.
     *
     * @param entityId the ID of the entity to display
     * @return a ModelMap with the model attributes for the view
     */
    @GetMapping("/{entityId}")
    public ModelAndView show(@PathVariable("entityId") Long entityId, ModelMap modelMap) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(VIEWS_BASE_PATH + "/details");
        mav.addAllObjects(modelMap);
        mav.addObject("entity", this.service.findById(entityId));
        return mav;
    }

    protected abstract Entity getEntity();

    protected GenericService<Entity> getService() {
        return service;
    }
}
