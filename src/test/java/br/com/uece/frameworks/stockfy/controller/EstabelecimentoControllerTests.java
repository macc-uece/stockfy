package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Endereco;
import br.com.uece.frameworks.stockfy.model.Estabelecimento;
import br.com.uece.frameworks.stockfy.service.EstabelecimentoService;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(EstabelecimentoController.class)
public class EstabelecimentoControllerTests {
    private static final Long TEST_ESTABELECIMENTO_ID = 1L;
    private static final String ESTABELECIMENTO_URL = "/admin/estabelecimentos";

    @Autowired
    private MockMvc mockMvc;

    // MockHttpSession session = makeAuthSession("admin", "ROLE_ADMIN"); //Precisa injetar essa sessao nas requisicoes do mock

    @MockBean
    private EstabelecimentoService estabelecimentoService;

    private Faker faker;
    private Estabelecimento estabelecimentoTeste;
    private Endereco enderecoTeste;

    @Before
    public void setup() {
        faker = new Faker(new Locale("pt-BR"));
        estabelecimentoTeste = new Estabelecimento();
        enderecoTeste = new Endereco();

        enderecoTeste.setCep(faker.address().zipCode());
        enderecoTeste.setLogradouro(faker.address().streetAddress());
        enderecoTeste.setBairro(faker.lorem().word());
        enderecoTeste.setLocalidade(faker.address().city());
        enderecoTeste.setNumero(Integer.parseInt(faker.number().digits(3)));
        enderecoTeste.setUf(faker.address().stateAbbr());
        enderecoTeste.setComplemento(faker.lorem().sentence());

        estabelecimentoTeste.setEndereco(enderecoTeste);
        estabelecimentoTeste.setCnpj("14.553.382/0001-95"); // gerado em https://www.geradorcnpj.com/
        estabelecimentoTeste.setNome(faker.company().name());

        System.out.println("\n\n" + enderecoTeste.toString());
        System.out.println(estabelecimentoTeste.toString());

        given(this.estabelecimentoService.findById(TEST_ESTABELECIMENTO_ID)).willReturn(estabelecimentoTeste);
    }

    //@Test
    public void testProtectedView() throws Exception {
        mockMvc.perform(get(ESTABELECIMENTO_URL)).andExpect(status().isUnauthorized());
        mockMvc.perform(get(ESTABELECIMENTO_URL + "/new")).andExpect(status().isUnauthorized());
        mockMvc.perform(post(ESTABELECIMENTO_URL + "/new")).andExpect(status().isUnauthorized());
        mockMvc.perform(get(ESTABELECIMENTO_URL + "/edit")).andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser(username = "admin")
    public void testInitCreationForm() throws Exception {
        mockMvc.perform(get(ESTABELECIMENTO_URL + "/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("entity"))
                .andExpect(view().name("pages/estabelecimento/form"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post(ESTABELECIMENTO_URL + "/new")
                .with(csrf())
                // estabelecimento
                .param("nome", faker.company().name())
                .param("cnpj", "86.359.292/0001-79") // cpnj gerado em https://www.geradorcnpj.com/
                // endereco
                .param("endereco.cep", faker.address().zipCode()) // endereco gerado em https://www.geradordecep.com.br/
                .param("endereco.logradouro", faker.address().streetName())
                .param("endereco.numero", faker.number().digits(3))
                .param("endereco.localidade", faker.address().city())
                .param("endereco.bairro", faker.lorem().sentence())
                .param("endereco.uf", faker.address().stateAbbr())
                .param("endereco.complemento", faker.lorem().sentence()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin")
    public void testProcessCreationFormHasErrors() throws Exception {
        mockMvc.perform(post(ESTABELECIMENTO_URL + "/new")
                .with(csrf())
                .param("nome", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("entity"))
                .andExpect(model().attributeHasFieldErrors("entity", "nome"))
                .andExpect(view().name("pages/estabelecimento/form"));
    }


    @Test
    @WithMockUser(username = "admin")
    public void testInitUpdateForm() throws Exception {
        mockMvc.perform(get(ESTABELECIMENTO_URL + "/{entityId}/edit", TEST_ESTABELECIMENTO_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("entity"))
                .andExpect(model().attribute("entity", hasProperty("nome", is(estabelecimentoTeste.getNome()))))
                .andExpect(model().attribute("entity", hasProperty("cnpj", is(estabelecimentoTeste.getCnpj()))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("bairro", is (estabelecimentoTeste.getEndereco().getBairro())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("cep", is(estabelecimentoTeste.getEndereco().getCep())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("complemento", is(estabelecimentoTeste.getEndereco().getComplemento())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("localidade", is(estabelecimentoTeste.getEndereco().getLocalidade())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("numero", is(estabelecimentoTeste.getEndereco().getNumero())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("logradouro", is(estabelecimentoTeste.getEndereco().getLogradouro())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("uf", is(estabelecimentoTeste.getEndereco().getUf())))))
                .andExpect(view().name("pages/estabelecimento/form"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testProcessUpdateFormSuccess() throws Exception {
        mockMvc.perform(post(ESTABELECIMENTO_URL + "/"+TEST_ESTABELECIMENTO_ID+"/edit")
                .with(csrf())
                .param("nome", faker.company().name())
                .param("cnpj", "25.800.162/0001-35") // cnpj gerado em https://www.geradorcnpj.com/
                .param("endereco.bairro", faker.lorem().sentence())
                .param("endereco.cep", faker.address().zipCode())
                .param("endereco.complemento", faker.lorem().sentence())
                .param("endereco.localidade", faker.address().city())
                .param("endereco.numero", faker.number().digits(3))
                .param("endereco.logradouro", faker.address().streetName())
                .param("endereco.uf", faker.address().stateAbbr())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + ESTABELECIMENTO_URL + "/{entityId}"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testProcessUpdateFormHasErrors() throws Exception {
        mockMvc.perform(post(ESTABELECIMENTO_URL + "/"+TEST_ESTABELECIMENTO_ID+"/edit")
                .with(csrf())
                .param("nome", "")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("entity"))
                .andExpect(model().attributeHasFieldErrors("entity", "nome"))
                .andExpect(view().name("pages/estabelecimento/form"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testShow() throws Exception {
        mockMvc.perform(get(ESTABELECIMENTO_URL + "/{entityId}", TEST_ESTABELECIMENTO_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("entity", hasProperty("nome", is(estabelecimentoTeste.getNome()))))
                .andExpect(model().attribute("entity", hasProperty("cnpj", is(estabelecimentoTeste.getCnpj()))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("bairro", is (estabelecimentoTeste.getEndereco().getBairro())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("cep", is(estabelecimentoTeste.getEndereco().getCep())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("complemento", is(estabelecimentoTeste.getEndereco().getComplemento())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("localidade", is(estabelecimentoTeste.getEndereco().getLocalidade())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("numero", is(estabelecimentoTeste.getEndereco().getNumero())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("logradouro", is(estabelecimentoTeste.getEndereco().getLogradouro())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("uf", is(estabelecimentoTeste.getEndereco().getUf())))))
                .andExpect(view().name("pages/estabelecimento/details"));
    }
}
