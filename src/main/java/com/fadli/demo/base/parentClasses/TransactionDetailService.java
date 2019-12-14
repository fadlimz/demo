package com.fadli.demo.base.parentClasses;

import com.fadli.demo.base.enums.ConfirmationStatus;
import com.fadli.demo.common.approvalLog.models.ApprovalLog;
import com.fadli.demo.common.approvalLog.services.ApprovalLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
public abstract class TransactionDetailService<E extends BaseEntity, R extends TransactionEntity> extends BaseService<E> {
    @Autowired
    private ApprovalLogService approvalLogService;

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
        valApprovalOnRoot(rootEntity.get());
        throwBatchError();

        entity = super.add(entity);

        updateRootEntity();

        return entity;
    }

    @Override
    @Transactional
    public E edit(E entity) {
        valApprovalOnRoot(rootEntity.get());
        throwBatchError();

        entity = super.edit(entity);

        updateRootEntity();

        return entity;
    }

    @Override
    @Transactional
    public void delete(E entity) {
        valApprovalOnRoot(rootEntity.get());
        throwBatchError();

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

    private void valApprovalOnRoot(R rootEntity) {
        R rootFromDb = rootRepository.findById(rootEntity.getId()).get();

        ApprovalLog approvalLog = approvalLogService.findByBk(rootFromDb.getId(), rootFromDb.getTransactionType().getTransactionCode(),
                rootFromDb.getTransactionNumber(), rootFromDb.getTransactionYearMonth());

        if (approvalLog.getConfirmationStatus().equals(ConfirmationStatus.Confirmed.name())) {
            batchError("transactionDetailService.rootTransaction.has.been.confirmed", rootFromDb.getTransactionNumber());
        }
    }
}
