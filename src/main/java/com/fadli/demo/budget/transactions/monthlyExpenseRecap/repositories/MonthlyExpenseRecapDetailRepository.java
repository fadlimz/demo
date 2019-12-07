package com.fadli.demo.budget.transactions.monthlyExpenseRecap.repositories;

import com.fadli.demo.base.parentClasses.BaseRepository;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.models.MonthlyExpenseRecapDetail;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class MonthlyExpenseRecapDetailRepository extends BaseRepository<MonthlyExpenseRecapDetail> {

    public MonthlyExpenseRecapDetailRepository(EntityManager em) {
        super(MonthlyExpenseRecapDetail.class, em);
    }

    public MonthlyExpenseRecapDetail findByBk(String monthlyExpenseRecapId, Date dateInput, String description) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM monthly_expense_recap_detail \n");
        sb.append("WHERE monthly_expense_recap = :monthlyExpenseRecapId \n");
        sb.append("  AND date_input = :dateInput \n");
        sb.append("  AND description = :description \n");

        Query query = em.createNativeQuery(sb.toString(), MonthlyExpenseRecapDetail.class);
        query.setParameter("monthlyExpenseRecapId", monthlyExpenseRecapId);
        query.setParameter("dateInput", dateInput);
        query.setParameter("description", description);

        try {
            return (MonthlyExpenseRecapDetail) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<MonthlyExpenseRecapDetail> getList(String monthlyExpenseRecapId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM monthly_expense_recap_detail \n");
        sb.append("WHERE monthly_expense_recap = :monthlyExpenseRecapId \n");
        sb.append("ORDER BY date_input DESC \n");

        Query query = em.createNativeQuery(sb.toString(), MonthlyExpenseRecapDetail.class);
        query.setParameter("monthlyExpenseRecapId", monthlyExpenseRecapId);

        return query.getResultList();
    }
}
