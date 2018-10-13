package br.com.uece.frameworks.stockfy.model;

import br.com.uece.frameworks.stockfy.enums.TipoOperacao;
import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Create by Bruno Barbosa - 13/10/2018
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Operacao extends BaseEntity<Long> {

    private static final long serialVersionUID = -1205735106169001818L;

    @Column
    private Long referencia;

    @Column
    private Integer quantidade;

    @ManyToOne
    private Estabelecimento estabelecimento;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Calendar dataEntrada;

    @Column
    @Enumerated(EnumType.STRING)
    private TipoOperacao tipoOperacao;

    public Operacao() {
    }
}
