package com.fadli.demo.base.parentClasses;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

public abstract class BaseDetailService<E extends BaseEntity, R extends BaseEntity> extends BaseService<E> {

    protected BaseRepository<R> rootRepository;
    protected ThreadLocal<R> rootEntity = new ThreadLocal<>();

    @Override protected abstract void initRepository();
    protected abstract void initRootRepository();
    public abstract void initRootEntity(R rootEntity);

    @Override
    @PostConstruct
    protected void init() {
        super.init();
        initRootRepository();
    }

    @Override
    @Transactional
    public E add(E entity) {
        entity = super.add(entity);

        updateRootEntity();

        return entity;
    }

    @Override
    @Transactional
    public E edit(E entity) {
        entity = super.edit(entity);

        updateRootEntity();

        return entity;
    }

    @Override
    @Transactional
    public void delete(E entity) {
        super.delete(entity);
        updateRootEntity();
    }

    protected void updateRootEntity() {
        incrementRootVersion(rootEntity.get());
        rootRepository.edit(rootEntity.get());
    }

    private void incrementRootVersion(R rootEntity) {
        Long newVersion = rootEntity.getVersion() + 1;

        rootEntity.setVersion(newVersion);
    }
}
