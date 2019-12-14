package com.fadli.demo.common.approvalLog.repositories;

import com.fadli.demo.base.parentClasses.BaseRepository;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.models.MonthlyExpenseRecap;
import com.fadli.demo.common.approvalLog.models.ApprovalLog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class ApprovalLogRepository extends BaseRepository<ApprovalLog> {

    public ApprovalLogRepository(EntityManager em) {
        super(ApprovalLog.class, em);
    }

    public ApprovalLog findByBk(String transactionId, String transactionType, String transactionNumber, String transactionYearMonth) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM approval_log \n");
        sb.append("WHERE transaction_id = :transactionId \n");
        sb.append("  AND transaction_code = :transactionType \n");
        sb.append("  AND transaction_number = :transactionNumber \n");
        sb.append("  AND transaction_year_month = :transactionYearMonth \n");

        Query query = em.createNativeQuery(sb.toString(), ApprovalLog.class);
        query.setParameter("transactionId", transactionId);
        query.setParameter("transactionType", transactionType);
        query.setParameter("transactionNumber", transactionNumber);
        query.setParameter("transactionYearMonth", transactionYearMonth);

        try {
            return (ApprovalLog) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
