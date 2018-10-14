package br.com.uece.frameworks.stockfy.service;

import br.com.uece.frameworks.stockfy.enums.PermissaoUser;
import br.com.uece.frameworks.stockfy.model.Funcionario;
import br.com.uece.frameworks.stockfy.model.Permissao;
import br.com.uece.frameworks.stockfy.model.Usuario;
import br.com.uece.frameworks.stockfy.repository.FuncionarioRepository;
import br.com.uece.frameworks.stockfy.repository.PermissaoRepository;
import br.com.uece.frameworks.stockfy.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Create by Bruno Barbosa - 14/10/2018
 */
@RestController
public class SetupService {

    private final PermissaoRepository permissaoRepository;

    private final PasswordEncoder passwordEncoder;

    private final FuncionarioRepository funcionarioRepository;

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public SetupService(PermissaoRepository permissaoRepository, PasswordEncoder passwordEncoder, FuncionarioRepository funcionarioRepository, UsuarioRepository usuarioRepository) {
        this.permissaoRepository = permissaoRepository;
        this.passwordEncoder = passwordEncoder;
        this.funcionarioRepository = funcionarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @RequestMapping("/setup")
    public void setup(HttpServletResponse response) throws IOException {
        Permissao permissaoAdm = new Permissao();
        permissaoAdm.setDescricao(PermissaoUser.ADMINISTRADOR.toString());
        permissaoAdm.setCode(PermissaoUser.ADMINISTRADOR.getCode());
        permissaoAdm = permissaoRepository.save(permissaoAdm);

        Usuario usuarioAdm = new Usuario();
        usuarioAdm.setLogin("admin");
        usuarioAdm.setSenha(passwordEncoder.encode("admin123"));
        Set<Permissao> permissaoSet = new HashSet<>();
        permissaoSet.add(permissaoAdm);
        usuarioAdm.setPermissoes(permissaoSet);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome("ADM");
        funcionario.setCargo("Administrador");
        funcionario = funcionarioRepository.save(funcionario);

        usuarioAdm.setFuncionario(funcionario);
        usuarioRepository.save(usuarioAdm);

        Permissao permissaoFuncionario = new Permissao();
        permissaoFuncionario.setDescricao(PermissaoUser.FUNCIONARIO.toString());
        permissaoFuncionario.setCode(PermissaoUser.FUNCIONARIO.getCode());
        permissaoFuncionario = permissaoRepository.save(permissaoFuncionario);

        Usuario usuarioFunc = new Usuario();
        usuarioFunc.setLogin("funcionario");
        usuarioFunc.setSenha(passwordEncoder.encode("f123"));
        Set<Permissao> permissaoFunc = new HashSet<>();
        permissaoFunc.add(permissaoFuncionario);
        usuarioFunc.setPermissoes(permissaoFunc);

        Funcionario funcionarioFunc = new Funcionario();
        funcionarioFunc.setNome("FUNCIONARIO");
        funcionarioFunc.setCargo("Vendedor");
        funcionarioFunc = funcionarioRepository.save(funcionarioFunc);

        usuarioFunc.setFuncionario(funcionarioFunc);
        usuarioRepository.save(usuarioFunc);

        response.sendRedirect("/login");
    }
}
