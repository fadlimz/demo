package com.fadli.demo.base.parentClasses;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public abstract class BaseTransactionService<E extends BaseEntity> extends BaseService<E> {

    @Override
    protected void initRepository() {}
    protected abstract void setTrxCode(E entity);

    @PostConstruct
    protected void init() {
        initRepository();
    }

}
