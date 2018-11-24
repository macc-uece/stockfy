package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Usuario;
import br.com.uece.frameworks.stockfy.service.GenericService;
import br.com.uece.frameworks.stockfy.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController extends AbstractController<Usuario> {

    private static final String VIEWS_BASE_PATH = "pages/usuario";
    private static final String USUARIO_REQUEST_URL = "/admin/usuarios";

    private final PermissaoService permissaoService;

    @Autowired
    public UsuarioController(GenericService<Usuario> service, PermissaoService permissaoService) {
        super(service, VIEWS_BASE_PATH, USUARIO_REQUEST_URL);
        this.permissaoService = permissaoService;
    }

    @Override
    protected Usuario getEntity() {
        return new Usuario();
    }

    @Override
    public String initCreationForm(ModelMap modelMap) {
        modelMap.put("permissoes", permissaoService.findAll());
        return super.initCreationForm(modelMap);
    }

    @Override
    public String processCreationForm(@Valid Usuario entity, BindingResult result, ModelMap model, MultipartFile file) {
        model.put("permissoes", permissaoService.findAll());
        return super.processCreationForm(entity, result, model, file);
    }

    @Override
    public String initUpdateForm(Long entityId, ModelMap model) {
        model.put("permissoes", permissaoService.findAll());
        return super.initUpdateForm(entityId, model);
    }

    @Override
    public String processUpdateForm(@Valid Usuario entity, BindingResult result, Long entityId, ModelMap model, MultipartFile file) {
        model.put("permissoes", permissaoService.findAll());
        return super.processUpdateForm(entity, result, entityId, model, file);
    }

    @Override
    public ModelAndView show(Long entityId, ModelMap modelMap) {
        return super.show(entityId, modelMap);
    }
}
