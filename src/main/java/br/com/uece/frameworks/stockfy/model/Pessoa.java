package br.com.uece.frameworks.stockfy.model;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Pessoa extends BaseEntity<Long> {

    private static final long serialVersionUID = -6838989188762945586L;

    @Column
    private String nome;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Endereco endereco;

    @Column
    @Email
    private String email;

    /*
     * Expressao regular para validar telefones e celulares do Brasil, com ou sem DDD
     * https://gist.github.com/boliveirasilva/c927811ff4a7d43a0e0c
     */
    @Pattern(regexp = "^(?:(?:\\+|00)?(55)\\s?)?(?:\\(?([1-9][0-9])\\)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})-?(\\d{4}))$")
    private String telefone1;
    @Pattern(regexp = "^(?:(?:\\+|00)?(55)\\s?)?(?:\\(?([1-9][0-9])\\)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})-?(\\d{4}))$")
    private String telefone2;

    public Pessoa() {
    }

    public Pessoa(String nome, @Email String email) {
        this.nome = nome;
        this.email = email;
    }
}
