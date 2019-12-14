package com.fadli.demo.base.parentClasses;

import com.fadli.demo.base.enums.ConfirmationStatus;
import com.fadli.demo.base.utils.DateUtil;
import com.fadli.demo.common.approvalLog.models.ApprovalLog;
import com.fadli.demo.common.approvalLog.services.ApprovalLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
public abstract class TransactionService<E extends TransactionEntity> extends BaseService<E> {

    @Autowired private ApprovalLogService approvalLogService;

    protected abstract void setTransactionType(E entity);
    protected abstract E doWhenConfirmation(E entity);
    protected abstract E doWhenCancelConfirmation(E entity);

    @Override
    @PostConstruct
    protected void initRepository() {
        super.init();
    }

    @Override
    @Transactional
    public E add(E entity) {
        setTransactionType(entity);
        entity = super.add(entity);

        addApprovalLog(entity);

        return entity;
    }

    @Override
    @Transactional
    public E edit(E entity) {
        valTransactionHasConfirmed(entity);
        throwBatchError();

        return super.edit(entity);
    }

    @Override
    @Transactional
    public void delete(E entity) {
        valTransactionHasConfirmed(entity);
        throwBatchError();

        super.delete(entity);

        deleteApprovalLog(entity);
        throwBatchError();
    }

    @Transactional
    public E confirm(E entity) {
        entity = doWhenConfirmation(entity);
        throwBatchError();

        ApprovalLog approvalLog = approvalLogService.findByBk(entity.getId(), entity.getTransactionType().getTransactionCode(),
                entity.getTransactionNumber(), entity.getTransactionYearMonth());

        approvalLog.setConfirmationStatus(ConfirmationStatus.Confirmed.name());
        approvalLog.setConfirmationDate(DateUtil.getSystemDate());

        approvalLogService.edit(approvalLog);

        return super.edit(entity);
    }

    @Transactional
    public E cancelConfirm(E entity) {
        entity = doWhenCancelConfirmation(entity);
        throwBatchError();

        ApprovalLog approvalLog = approvalLogService.findByBk(entity.getId(), entity.getTransactionType().getTransactionCode(),
                entity.getTransactionNumber(), entity.getTransactionYearMonth());

        approvalLog.setConfirmationStatus(ConfirmationStatus.Unconfirmed.name());
        approvalLog.setConfirmationDate(null);

        approvalLogService.edit(approvalLog);

        return edit(entity);
    }

    private void addApprovalLog(E entity) {
        ApprovalLog approvalLog = new ApprovalLog();
        approvalLog.setTransactionId(entity.getId());
        approvalLog.setTransactionCode(entity.getTransactionType().getTransactionCode());
        approvalLog.setTransactionNumber(entity.getTransactionNumber());
        approvalLog.setTransactionYearMonth(entity.getTransactionYearMonth());
        approvalLog.setConfirmationStatus(ConfirmationStatus.Unconfirmed.name());

        approvalLogService.add(approvalLog);
    }

    private void deleteApprovalLog(E entity) {
        ApprovalLog approvalLog = approvalLogService.findByBk(entity.getId(), entity.getTransactionType().getTransactionCode(),
                entity.getTransactionNumber(), entity.getTransactionYearMonth());

        approvalLogService.delete(approvalLog);
    }

    private void valTransactionHasConfirmed(E entity) {
        ApprovalLog approvalLog = approvalLogService.findByBk(entity.getId(), entity.getTransactionType().getTransactionCode(),
                entity.getTransactionNumber(), entity.getTransactionYearMonth());

        if (approvalLog.getConfirmationStatus().equals(ConfirmationStatus.Confirmed.name())) {
            batchError("transactionService.transaction.has.been.confirmed", entity.getTransactionNumber());
        }
    }
}
