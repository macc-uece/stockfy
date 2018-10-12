package br.com.uece.frameworks.stockfy.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
@MappedSuperclass
@SuppressWarnings("serial")
@Getter
@Setter
public class BaseEntity <PK extends Serializable> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private PK id;
}
