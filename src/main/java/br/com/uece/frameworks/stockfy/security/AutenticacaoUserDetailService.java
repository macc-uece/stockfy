package br.com.uece.frameworks.stockfy.security;

import br.com.uece.frameworks.stockfy.model.Permissao;
import br.com.uece.frameworks.stockfy.model.Usuario;
import br.com.uece.frameworks.stockfy.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
@Service
public class AutenticacaoUserDetailService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AutenticacaoUserDetailService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login);
        if(usuario == null){
            throw new UsernameNotFoundException("Login ou Senha Inválido!.");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(Permissao permissao : usuario.getPermissoes()){
            authorities.add(new SimpleGrantedAuthority(permissao.getCode()));
        }

        UserAutenticado userAutenticado = new UserAutenticado(usuario.getLogin(),usuario.getSenha(),authorities);
        userAutenticado.setNome(usuario.getFuncionario().getNome());
        userAutenticado.setCargo(usuario.getFuncionario().getCargo());
        if(usuario.getImagem() != null){
            userAutenticado.setImagem(usuario.getImagem().getId());
        }

        return userAutenticado;
    }
}
