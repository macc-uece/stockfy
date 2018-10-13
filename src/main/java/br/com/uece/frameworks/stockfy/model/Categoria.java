package br.com.uece.frameworks.stockfy.model;

import javax.persistence.Entity;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Categoria
 */

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Categoria extends BaseEntity<Long>{

    private static final long serialVersionUID = 4649463011906215428L;

    private String nome;

    public Categoria() {
    }

    public Categoria(String nome) {
        this.nome = nome;
    }
    
}