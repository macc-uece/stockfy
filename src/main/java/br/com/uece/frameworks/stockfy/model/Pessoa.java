package br.com.uece.frameworks.stockfy.model;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

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

    @Column
    private String cnpj;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Endereco endereco;

    @ElementCollection
    @CollectionTable(name="pessoa_fones", joinColumns=@JoinColumn(name="pessoa_id"))
    @Column(name="fone")
    private Set<String> fones;

    @Column
    private String email;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Calendar dataCriacao;

    public Pessoa() {
    }
}
