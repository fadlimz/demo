package com.fadli.demo.common.approvalLog.services;

import com.fadli.demo.base.parentClasses.BaseService;
import com.fadli.demo.common.approvalLog.models.ApprovalLog;
import com.fadli.demo.common.approvalLog.repositories.ApprovalLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalLogService extends BaseService<ApprovalLog> {

    @Autowired private ApprovalLogRepository approvalLogRepository;

    @Override
    protected void initRepository() {
        repository = approvalLogRepository;
    }

    public ApprovalLog findByBk(String transactionId, String transactionType, String transactionNumber, String transactionYearMonth) {
        return approvalLogRepository.findByBk(transactionId, transactionType, transactionNumber, transactionYearMonth);
    }

    @Override
    protected void setEditedValues(ApprovalLog entity, ApprovalLog entityFromDb) {
        entityFromDb.setConfirmationStatus(entity.getConfirmationStatus());
        entityFromDb.setConfirmationDate(entity.getConfirmationDate());
    }
}
