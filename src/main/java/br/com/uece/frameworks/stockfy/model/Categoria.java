package br.com.uece.frameworks.stockfy.model;

import javax.persistence.Entity;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Categoria
 */

@Entity
@Getter
@Setter
public class Categoria extends BaseEntity<Long>{

    private String nome;

    public Categoria() {
    }

    public Categoria(String nome) {
        this.nome = nome;
    }
    
}