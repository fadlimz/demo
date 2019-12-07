package com.fadli.demo.budget.masters.salary.repositories;

import com.fadli.demo.base.constants.BaseConstants;
import com.fadli.demo.base.parentClasses.BaseRepository;
import com.fadli.demo.base.utils.StringUtil;
import com.fadli.demo.budget.masters.salary.models.Salary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
public class SalaryRepository extends BaseRepository<Salary> {

    public SalaryRepository(EntityManager em) {
        super(Salary.class, em);
    }

    public Salary findByBk(String userId) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * \n");
        sb.append("FROM salary \n");
        sb.append("WHERE user_id = :userId \n");

        Query query = em.createNativeQuery(sb.toString(), Salary.class);
        query.setParameter("userId", userId);

        try {
            return (Salary) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Salary> getList(Map<String, String> parameter) {

        String userId = parameter.getOrDefault("userId", BaseConstants.BLANK);
        String userCode = parameter.getOrDefault("userCode", BaseConstants.BLANK);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.* \n");
        sb.append("FROM salary a \n");
        sb.append("     INNER JOIN user b ON b.id = a.user_id \n");
        sb.append("WHERE 1 = 1 \n");

        if (!StringUtil.isBlank(userId)) {
            sb.append("  AND a.user_id = :userId  \n");
        }
        if (!StringUtil.isBlank(userCode)) {
            sb.append("  AND b.user_code LIKE :userCode  \n");
        }
        sb.append("ORDER BY b.user_code \n");

        Query query = em.createNativeQuery(sb.toString(), Salary.class);
        if (!StringUtil.isBlank(userId)) {
            query.setParameter("userId", userId);}

        if (!StringUtil.isBlank(userCode)) {
            query.setParameter("userCode", userCode);
        }

        return query.getResultList();
    }
}
