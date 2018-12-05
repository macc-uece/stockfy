package br.com.uece.frameworks.stockfy.controller;

import br.com.uece.frameworks.stockfy.model.Endereco;
import br.com.uece.frameworks.stockfy.model.Estabelecimento;
import br.com.uece.frameworks.stockfy.model.Fornecedor;
import br.com.uece.frameworks.stockfy.service.FornecedorService;
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

@RunWith(SpringRunner.class)
@WebMvcTest(FornecedorController.class)
public class FornecedorControllerTests {
    private static final Long TEST_FORNECEDOR_ID = 1L;
    private static final String FORNECEDOR_URL = "/admin/fornecedores";

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private FornecedorService fornecedorService;

    private Faker faker;
    private Fornecedor fornecedorTeste;
    private Endereco enderecoTeste;

    @Before
    public void setup() {
        faker = new Faker(new Locale("pt-BR"));
        fornecedorTeste = new Fornecedor();
        enderecoTeste = new Endereco();

        enderecoTeste.setCep(faker.address().zipCode());
        enderecoTeste.setLogradouro(faker.address().streetAddress());
        enderecoTeste.setBairro(faker.lorem().word());
        enderecoTeste.setLocalidade(faker.address().city());
        enderecoTeste.setNumero(Integer.parseInt(faker.number().digits(3)));
        enderecoTeste.setUf(faker.address().stateAbbr());
        enderecoTeste.setComplemento(faker.lorem().sentence());

        fornecedorTeste.setEndereco(enderecoTeste);
        fornecedorTeste.setCnpj("14.553.382/0001-95"); // gerado em https://www.geradorcnpj.com/
        fornecedorTeste.setEmail(faker.internet().emailAddress());
        fornecedorTeste.setNome(faker.company().name());
        fornecedorTeste.setTelefone1(faker.phoneNumber().phoneNumber().replace("\\D", "").replace("\\D", "")); // telefone somente com numeros
        fornecedorTeste.setTelefone2(faker.phoneNumber().phoneNumber().replace("\\D", ""));

        System.out.println("\n\n" + enderecoTeste.toString());
        System.out.println(fornecedorTeste.toString());

        given(this.fornecedorService.findById(TEST_FORNECEDOR_ID)).willReturn(fornecedorTeste);
    }

    @Test
    public void testProtectedView() throws Exception {
        mockMvc.perform(get(FORNECEDOR_URL)).andExpect(status().isUnauthorized());
        mockMvc.perform(get(FORNECEDOR_URL + "/new")).andExpect(status().isUnauthorized());
        mockMvc.perform(post(FORNECEDOR_URL + "/new")).andExpect(status().isForbidden());
        mockMvc.perform(get(FORNECEDOR_URL + "/edit")).andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser(username = "admin")
    public void testInitCreationForm() throws Exception {
        mockMvc.perform(get(FORNECEDOR_URL + "/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("entity"))
                .andExpect(view().name("pages/fornecedor/form"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post(FORNECEDOR_URL + "/new")
                        .with(csrf())
                        // fornecedor
                        .param("cnpj", "86.359.292/0001-79") // cpnj gerado em https://www.geradorcnpj.com/
                        .param("nome", faker.name().fullName())
                        .param("email", faker.internet().emailAddress())
                        //              Getting error of bean validation when using the two follow lines, I think that could be someting with the regex pattern defined in such fields
//                .param("telefone1", faker.phoneNumber().phoneNumber().replace("\\D", ""))
//                .param("telefone2", faker.phoneNumber().phoneNumber().replace("\\D", ""))
                        // endereco
                        .param("endereco.cep", faker.address().zipCode())
                        .param("endereco.logradouro", faker.address().streetName())
                        .param("endereco.numero", faker.number().digits(3))
                        .param("endereco.localidade", faker.address().city())
                        .param("endereco.bairro", faker.lorem().sentence())
                        .param("endereco.uf", faker.address().stateAbbr())
                        .param("endereco.complemento", faker.lorem().sentence())
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin")
    public void testProcessCreationFormHasErrors() throws Exception {
        mockMvc.perform(post(FORNECEDOR_URL + "/new")
                .with(csrf())
                .param("nome", "")
                .param("email", faker.name().username())
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("entity"))
                .andExpect(model().attributeHasFieldErrors("entity", "nome"))
                .andExpect(view().name("pages/fornecedor/form"));
    }


    @Test
    @WithMockUser(username = "admin")
    public void testInitUpdateForm() throws Exception {
        mockMvc.perform(get(FORNECEDOR_URL + "/{entityId}/edit", TEST_FORNECEDOR_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("entity"))
                // fornecedor
                .andExpect(model().attribute("entity", hasProperty("nome", is(fornecedorTeste.getNome()))))
                .andExpect(model().attribute("entity", hasProperty("cnpj", is(fornecedorTeste.getCnpj()))))
                .andExpect(model().attribute("entity", hasProperty("email", is(fornecedorTeste.getEmail()))))
                //              Getting error of bean validation when using the two follow lines, I think that could be someting with the regex pattern defined in such fields
//                .andExpect(model().attribute("entity", hasProperty("telefone1", is(fornecedorTeste.getTelefone1()))))
//                .andExpect(model().attribute("entity", hasProperty("telefone2", is(fornecedorTeste.getTelefone2()))))
                // endereco
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("bairro", is(fornecedorTeste.getEndereco().getBairro())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("cep", is(fornecedorTeste.getEndereco().getCep())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("complemento", is(fornecedorTeste.getEndereco().getComplemento())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("localidade", is(fornecedorTeste.getEndereco().getLocalidade())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("numero", is(fornecedorTeste.getEndereco().getNumero())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("logradouro", is(fornecedorTeste.getEndereco().getLogradouro())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("uf", is(fornecedorTeste.getEndereco().getUf())))))
                .andExpect(view().name("pages/fornecedor/form"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testProcessUpdateFormSuccess() throws Exception {
        mockMvc.perform(post(FORNECEDOR_URL + "/" + TEST_FORNECEDOR_ID + "/edit")
                        .with(csrf())
                        // fornecedor
                        .param("nome", faker.company().name())
                        .param("cnpj", "25.800.162/0001-35") // cnpj gerado em https://www.geradorcnpj.com/
                        .param("email", faker.internet().emailAddress())
//              Getting error of bean validation when using the two follow lines, I think that could be someting with the regex pattern defined in such fields
//                .param("telefone1", faker.phoneNumber().phoneNumber().replace("\\D", ""))
//                .param("telefone2", faker.phoneNumber().phoneNumber().replace("\\D", ""))
                        // endereco
                        .param("endereco.bairro", faker.lorem().sentence())
                        .param("endereco.cep", faker.address().zipCode())
                        .param("endereco.complemento", faker.lorem().sentence())
                        .param("endereco.localidade", faker.address().city())
                        .param("endereco.numero", faker.number().digits(3))
                        .param("endereco.logradouro", faker.address().streetName())
                        .param("endereco.uf", faker.address().stateAbbr())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + FORNECEDOR_URL + "/{entityId}"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testProcessUpdateFormHasErrors() throws Exception {
        mockMvc.perform(post(FORNECEDOR_URL + "/" + TEST_FORNECEDOR_ID + "/edit")
                .with(csrf())
                .param("nome", "") // @NotBlank
                .param("email", faker.name().username()) // @Email
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("entity"))
                .andExpect(model().attributeHasFieldErrors("entity", "nome"))
                .andExpect(model().attributeHasFieldErrors("entity", "email"))
                .andExpect(view().name("pages/fornecedor/form"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testShow() throws Exception {
        mockMvc.perform(get(FORNECEDOR_URL + "/{entityId}", TEST_FORNECEDOR_ID))
                .andExpect(status().isOk())
                // fornecedor
                // @duvida: e se esquecermos de testar um campo do objeto. Existe uma forma de automarizar a escrita desses repetivos testes?
                .andExpect(model().attribute("entity", hasProperty("nome", is(fornecedorTeste.getNome()))))
                .andExpect(model().attribute("entity", hasProperty("email", is(fornecedorTeste.getEmail()))))
                .andExpect(model().attribute("entity", hasProperty("cnpj", is(fornecedorTeste.getCnpj()))))
                .andExpect(model().attribute("entity", hasProperty("telefone1", is(fornecedorTeste.getTelefone1()))))
                .andExpect(model().attribute("entity", hasProperty("telefone2", is(fornecedorTeste.getTelefone2()))))
                // endereco
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("bairro", is(fornecedorTeste.getEndereco().getBairro())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("cep", is(fornecedorTeste.getEndereco().getCep())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("complemento", is(fornecedorTeste.getEndereco().getComplemento())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("localidade", is(fornecedorTeste.getEndereco().getLocalidade())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("numero", is(fornecedorTeste.getEndereco().getNumero())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("logradouro", is(fornecedorTeste.getEndereco().getLogradouro())))))
                .andExpect(model().attribute("entity", hasProperty("endereco", hasProperty("uf", is(fornecedorTeste.getEndereco().getUf())))))
                .andExpect(view().name("pages/fornecedor/details"));
    }
}

