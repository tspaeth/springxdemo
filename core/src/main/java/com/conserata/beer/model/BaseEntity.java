package com.conserata.beer.model;

import org.springframework.hateoas.Identifiable;

import javax.annotation.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 */
@MappedSuperclass
public class BaseEntity implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    protected BaseEntity() {
        this.id = null;
    }

    @Override
    public Long getId() {
        return id;
    }
}
