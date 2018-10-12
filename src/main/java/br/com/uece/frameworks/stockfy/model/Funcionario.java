package br.com.uece.frameworks.stockfy.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Funcionario extends Pessoa{

    private static final long serialVersionUID = 8482515312488421990L;

    @Column
    private String cargo;

    @Column
    private String cpf;

    public Funcionario() {
    }
}
