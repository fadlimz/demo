package com.fadli.demo.base.parentClasses;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;

@NoRepositoryBean
public class BaseRepository<T> extends SimpleJpaRepository<T, String> {

    protected final EntityManager em;

    public BaseRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

    public T add(T entity) {
        return saveAndFlush(entity);
    }

    public T edit(T entity) {
        return saveAndFlush(entity);
    }
}
