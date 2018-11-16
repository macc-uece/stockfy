package br.com.uece.frameworks.stockfy.model;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Calendar;

/**
 * Create by Bruno Barbosa - 13/10/2018
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Despesa extends BaseEntity<Long> {

    private static final long serialVersionUID = 8619128003585177006L;

    @Column
    private String descricao;

    @Column
    private Double valor;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy") // lembrar de re-adicionar os horários
    private Calendar data;

    public Despesa() {
    }
}
