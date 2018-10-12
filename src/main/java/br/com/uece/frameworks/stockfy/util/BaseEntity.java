package br.com.uece.frameworks.stockfy.util;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuppressWarnings("serial")
@Getter
@Setter
public class BaseEntity <PK extends Serializable> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private PK id;

    @CreatedDate
    private Instant createDate;

    @LastModifiedDate
    private Instant modifiedDate;
}
