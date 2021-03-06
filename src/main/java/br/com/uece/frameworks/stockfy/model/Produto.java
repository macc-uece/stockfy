package br.com.uece.frameworks.stockfy.model;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

/**
 * Create by Bruno Barbosa - 13/10/2018
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Produto extends BaseEntity<Long> {

    private static final long serialVersionUID = 10981866523365291L;

    @Column
    private Long referencia;

    @Column
    private String descricao;

    @Column
    private Double preco;

    @Column
    private Integer estoqueMinimo;

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    private Categoria categoria;

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    private Fornecedor fornecedor;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private Imagem imagem;

    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "produto")
    private List<SubProduto> subProdutos;

    public Produto() {
    }

    public Produto(Long referencia, Double preco) {
        this.referencia = referencia;
        this.preco = preco;
    }
}
