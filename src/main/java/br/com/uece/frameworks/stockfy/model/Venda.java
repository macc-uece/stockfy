package br.com.uece.frameworks.stockfy.model;

import br.com.uece.frameworks.stockfy.enums.TipoPagamento;
import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Create by Bruno Barbosa - 21/11/2018
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Venda extends BaseEntity<Long> {

    private static final long serialVersionUID = 7546926266528780447L;

    @Column
    private String descricaoProduto;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    @Column
    private Integer quantidadeItens;

    @Column
    private BigDecimal totalVenda;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Calendar dataVenda;

    @ManyToOne
    private Estabelecimento estabelecimento;
}
