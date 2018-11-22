package br.com.uece.frameworks.stockfy.model;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Create by Bruno Barbosa - 15/11/2018
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class SubProduto extends BaseEntity<Long> {

    private static final long serialVersionUID = 6355062840615242837L;

    @Column
    private String tamanho;

    @Column
    private String cor;

    @Column
    private String codBarras;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public String getDescricaoCompleta() {
        return produto.getDescricao() + " - " + getDescricaoSubProduto();
    }

    public String getDescricaoSubProduto() {
        return "Cor " + getCor() + " / Tamanho " + getTamanho();
    }
}
