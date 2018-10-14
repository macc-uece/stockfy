package br.com.uece.frameworks.stockfy.enums;

import lombok.Getter;

/**
 * Create by Bruno Barbosa - 14/10/2018
 */
@Getter
public enum PermissaoUser {

    ADMINISTRADOR("ROLE_ADMINISTRADOR"),
    FUNCIONARIO("ROLE_FUNCIONARIO");

    private String code;

    PermissaoUser(String code) {
        this.code = code;
    }
}
