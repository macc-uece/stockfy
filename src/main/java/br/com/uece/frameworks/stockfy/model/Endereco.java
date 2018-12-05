package br.com.uece.frameworks.stockfy.model;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Endereco extends BaseEntity<Long> {

    private static final long serialVersionUID = 5986466479496786155L;

    @Column
    private String logradouro;

    @Column
    private Integer numero;

    @Column
    private String complemento;

    @Column
    private String cep;

    @Column
    private String bairro;

    @Column
    private String localidade;

    @Column
    private String uf;

    public Endereco() {
    }

    public Endereco(String logradouro, Integer numero, String complemento, String cep, String bairro, String localidade, String uf) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
    }

}
