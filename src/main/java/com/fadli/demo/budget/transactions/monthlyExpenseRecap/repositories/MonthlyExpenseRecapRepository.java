package com.fadli.demo.budget.transactions.monthlyExpenseRecap.repositories;

import com.fadli.demo.base.constants.BaseConstants;
import com.fadli.demo.base.parentClasses.BaseRepository;
import com.fadli.demo.base.utils.StringUtil;
import com.fadli.demo.budget.transactions.monthlyExpenseRecap.models.MonthlyExpenseRecap;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
public class MonthlyExpenseRecapRepository extends BaseRepository<MonthlyExpenseRecap> {

    public MonthlyExpenseRecapRepository(EntityManager em) {
        super(MonthlyExpenseRecap.class, em);
    }

    public MonthlyExpenseRecap findByBk(String salaryId, String transactionType, String transactionNumber, String transactionYearMonth) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM monthly_expense_recap \n");
        sb.append("WHERE salary_id = :salaryId \n");
        sb.append("  AND transaction_type = :transactionType \n");
        sb.append("  AND transaction_number = :transactionNumber \n");
        sb.append("  AND transaction_year_month = :transactionYearMonth \n");

        Query query = em.createNativeQuery(sb.toString(), MonthlyExpenseRecap.class);
        query.setParameter("salaryId", salaryId);
        query.setParameter("transactionType", transactionType);
        query.setParameter("transactionNumber", transactionNumber);
        query.setParameter("transactionYearMonth", transactionYearMonth);

        try {
            return (MonthlyExpenseRecap) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<MonthlyExpenseRecap> getList(Map<String, String> parameter) {

        String userId = parameter.getOrDefault("userId", BaseConstants.BLANK);
        String salaryId = parameter.getOrDefault("salaryId", BaseConstants.BLANK);
        String transactionNumber = parameter.getOrDefault("transactionNumber", BaseConstants.BLANK);
        String transactionYearMonth = parameter.getOrDefault("transactionYearMonth", BaseConstants.BLANK);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.* \n");
        sb.append("FROM monthly_expense_recap a \n");
        sb.append("     INNER JOIN salary b ON b.id = a.salary_id \n");
        sb.append("     INNER JOIN user c ON c.id = b.user_id \n");
        sb.append("WHERE 1 = 1 \n");

        if (!StringUtil.isBlank(userId)) {
            sb.append("  AND c.id = :userId  \n");
        }
        if (!StringUtil.isBlank(salaryId)) {
            sb.append("  AND b.id LIKE :salaryId  \n");
        }
        if (!StringUtil.isBlank(transactionNumber)) {
            sb.append("  AND a.transaction_number LIKE :transactionNumber  \n");
        }
        if (!StringUtil.isBlank(transactionYearMonth)) {
            sb.append("  AND a.transaction_year_month LIKE :transactionYearMonth  \n");
        }
        sb.append("ORDER BY a.transaction_year_month DESC, a.transaction_number \n");

        Query query = em.createNativeQuery(sb.toString(), MonthlyExpenseRecap.class);
        if (!StringUtil.isBlank(userId)) {
            query.setParameter("userId", userId);
        }
        if (!StringUtil.isBlank(salaryId)) {
            query.setParameter("salaryId", salaryId);
        }
        if (!StringUtil.isBlank(transactionNumber)) {
            query.setParameter("transactionNumber", transactionNumber);
        }
        if (!StringUtil.isBlank(transactionYearMonth)) {
            query.setParameter("transactionYearMonth", transactionYearMonth);
        }

        return query.getResultList();
    }
}
