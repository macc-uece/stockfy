package br.com.uece.frameworks.stockfy.model;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.Calendar;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Imagem extends BaseEntity<Long> {

    private static final long serialVersionUID = 107474500158629209L;

    @Column(nullable = false)
    private String nome;

    @Lob
    @JsonIgnore
    private byte[]  image;

    @Column
    private String extensao;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Calendar dataCriacao;

    public Imagem() {
    }
}
