package br.com.uece.frameworks.stockfy.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CNPJ;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Fornecedor
 */

@Entity
@Setter
@Getter
public class Fornecedor extends BaseEntity<Long>{
    
    @NotBlank
    private String nome;
    @CNPJ
    private String cnpj;
    @Email
    private String email;
    /*
    * Expressao regular para validar telefones e celulares do Brasil, com ou sem DDD
    * https://gist.github.com/boliveirasilva/c927811ff4a7d43a0e0c
    */
    @Pattern(regexp = "/^(?:(?:\\+|00)?(55)\\s?)?(?:\\(?([1-9][0-9])\\)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$/")
    private String telefone1;
    @Pattern(regexp = "/^(?:(?:\\+|00)?(55)\\s?)?(?:\\(?([1-9][0-9])\\)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$/")
    private String telefone2;
    
}