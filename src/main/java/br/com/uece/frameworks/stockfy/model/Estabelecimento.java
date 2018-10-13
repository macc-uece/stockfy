package br.com.uece.frameworks.stockfy.model;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Create by Bruno Barbosa - 13/10/2018
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Estabelecimento extends BaseEntity<Long> {

    private static final long serialVersionUID = 6077435869260773383L;

    @Column
    private String nome;

    @CNPJ
    private String cnpj;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private Endereco endereco;

    public Estabelecimento() {
    }
}
