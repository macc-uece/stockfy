package br.com.uece.frameworks.stockfy.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserAutenticado extends User {

    private static final long serialVersionUID = -8460128448727946964L;

    private String nome;
    private String cargo;
    private Long imagem;

    public UserAutenticado(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
