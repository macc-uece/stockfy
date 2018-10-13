package br.com.uece.frameworks.stockfy.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.Entity;

/**
 * Fornecedor
 */

@Entity
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class Fornecedor extends Pessoa {

    private static final long serialVersionUID = -1868298993542817252L;

    @CNPJ
    private String cnpj;
}