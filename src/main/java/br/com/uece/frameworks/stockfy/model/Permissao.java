package br.com.uece.frameworks.stockfy.model;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Permissao extends BaseEntity<Long> {

    private static final long serialVersionUID = 5409645250970033152L;

    @Column
    private String code;

    @Column
    private String descricao;

    public Permissao() {
    }

    public Permissao(String code, String descricao) {
        this.code = code;
        this.descricao = descricao;
    }
}
