package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Categoria;
import br.com.uece.frameworks.stockfy.service.CategoriaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTests {

    private static final Long TEST_CATEGORIA_ID = 1L;
    private static final String CATEGORIA_URL = "/admin/categorias";

    @Autowired
    private MockMvc mockMvc;

    // MockHttpSession session = makeAuthSession("admin", "ROLE_ADMIN"); //Precisa injetar essa sessao nas requisicoes do mock

    @MockBean
    private CategoriaService categoriaService;

    private Categoria categoriaTeste;

    @Before
    public void setup() {
        categoriaTeste = new Categoria();
        categoriaTeste.setNome("Perfumaria");
        given(this.categoriaService.findById(TEST_CATEGORIA_ID)).willReturn(categoriaTeste);
    }

    //@Test
    public void testProtectedView() throws Exception {
        mockMvc.perform(get(CATEGORIA_URL)).andExpect(status().isUnauthorized());
        mockMvc.perform(get(CATEGORIA_URL + "/new")).andExpect(status().isUnauthorized());
        mockMvc.perform(post(CATEGORIA_URL + "/new")).andExpect(status().isUnauthorized());
        mockMvc.perform(get(CATEGORIA_URL + "/edit")).andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser(username = "admin")
    public void testInitCreationForm() throws Exception {
        mockMvc.perform(get(CATEGORIA_URL + "/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("entity"))
                .andExpect(view().name("pages/categoria/form"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post(CATEGORIA_URL + "/new")
                .with(csrf())
                .param("nome", "Cuidados com a pele"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin")
    public void testProcessCreationFormHasErrors() throws Exception {
        mockMvc.perform(post(CATEGORIA_URL + "/new")
                .with(csrf())
                .param("nome", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("entity"))
                .andExpect(model().attributeHasFieldErrors("entity", "nome"))
                .andExpect(view().name("pages/categoria/form"));
    }


    @Test
    @WithMockUser(username = "admin")
    public void testInitUpdateForm() throws Exception {
        mockMvc.perform(get(CATEGORIA_URL + "/{entityId}/edit", TEST_CATEGORIA_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("entity"))
                .andExpect(model().attribute("entity", hasProperty("nome", is("Perfumaria"))))
                .andExpect(view().name("pages/categoria/form"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testProcessUpdateFormSuccess() throws Exception {
        mockMvc.perform(post(CATEGORIA_URL + "/"+TEST_CATEGORIA_ID+"/edit")
                .with(csrf())
                .param("nome", "Escritório")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + CATEGORIA_URL + "/{entityId}"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testProcessUpdateFormHasErrors() throws Exception {
        mockMvc.perform(post(CATEGORIA_URL + "/"+TEST_CATEGORIA_ID+"/edit")
                .with(csrf())
                .param("nome", "")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("entity"))
                .andExpect(model().attributeHasFieldErrors("entity", "nome"))
                .andExpect(view().name("pages/categoria/form"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testShow() throws Exception {
        mockMvc.perform(get(CATEGORIA_URL + "/{entityId}", TEST_CATEGORIA_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("entity", hasProperty("nome", is("Perfumaria"))))
                .andExpect(view().name("pages/categoria/details"));
    }

}