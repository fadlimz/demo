package com.fadli.demo.base.parentClasses;

import com.fadli.demo.base.exceptions.BusinessException;
import com.fadli.demo.base.localThread.LocalErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpServerErrorException;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public abstract class BaseService<E extends BaseEntity> {

    @Autowired private LocalErrors errorManager;

    protected BaseRepository<E> repository;

    protected abstract void initRepository();

    protected void valMustExists(E entity) {}
    protected void valMustNotExists(E entity) {}
    protected void persistReferenceEntity(E entity) {}
    protected void defineValueOnAdd(E entity) {}
    protected void setEditedValues(E entity, E entityFromDb) {}
    protected void processValuesBeforeSave(E entity) {}
    protected void valOnAdd(E entity) {}
    protected void valOnEdit(E entity) {}
    protected void valOnDelete(E entity) {}
    protected void valOnAddAndEdit(E entity) {}

    @PostConstruct
    protected void init() {
        initRepository();
    }

    @Transactional
    public E add(E entity) {
        defineValueOnAdd(entity);

        persistReferenceEntity(entity);

        valMustNotExists(entity);
        throwBatchError();

        valOnAdd(entity);
        throwBatchError();

        valOnAddAndEdit(entity);
        throwBatchError();

        processValuesBeforeSave(entity);

        setIdOnAdd(entity);

        setVersion(entity);

        return repository.add(entity);
    }

    @Transactional
    public E edit(E entity) {
        valMustExists(entity);
        throwBatchError();

        persistReferenceEntity(entity);

        valOnEdit(entity);
        throwBatchError();

        valOnAddAndEdit(entity);
        throwBatchError();

        processValuesBeforeSave(entity);

        E entityFromDb = findById(entity.getId());
        setEditedValues(entity, entityFromDb);

        incrementVersion(entityFromDb);

        return repository.edit(entityFromDb);
    }

    @Transactional
    public void delete(E entity) {
        valMustExists(entity);
        throwBatchError();

        valOnDelete(entity);
        throwBatchError();

        repository.delete(entity);
    }

    @Transactional
    public void deleteAll(List<E> entities) {
        repository.deleteAll(entities);
    }

    public E findById(String id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    private  <E extends BaseEntity> void setIdOnAdd(E entity) {
        entity.setId(UUID.randomUUID().toString());
    }

    private void setVersion(E entity) {
        entity.setVersion(1L);
    }

    protected void incrementVersion(E entity) {
        Long newVersion = entity.getVersion() + 1;

        entity.setVersion(newVersion);
    }

    protected LocalErrors error(String code, Object... args) {
        throw new BusinessException(code, args);
    }

    protected LocalErrors batchError(String code, Object... args) {
        errorManager.addError(code, args);
        return errorManager;
    }

    protected void throwBatchError() {
        errorManager.throwBatchError();
    }

    protected void throwMultiBatchError() {
        errorManager.throwMultiBatchError();
        LocalErrors.removeMulti();
    }

}
