package br.com.uece.frameworks.stockfy.model;

import br.com.uece.frameworks.stockfy.util.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Usuario extends BaseEntity<Long> {

    private static final long serialVersionUID = 2803104519679320865L;

    @Column(unique = true)
    private String login;

    @Column
    @JsonIgnore
    private String senha;

    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonIgnore
    private Calendar dataCriacao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private Set<Permissao> permissoes;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "imagem_id")
    private Imagem imagem;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private Funcionario funcionario;
}
