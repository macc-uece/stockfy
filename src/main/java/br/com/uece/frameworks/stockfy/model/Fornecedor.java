package br.com.uece.frameworks.stockfy.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import java.util.Optional;

/**
 * Fornecedor
 */

@Entity
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Fornecedor extends Pessoa {

    private static final long serialVersionUID = -1868298993542817252L;

//    Opcional
    @Pattern(message = "Digite um CNPJ válido", regexp = "([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})?")
    private String cnpj;
}