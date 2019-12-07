package com.fadli.demo.common.transactionType.repositories;

import com.fadli.demo.base.parentClasses.BaseRepository;
import com.fadli.demo.common.transactionType.models.TransactionType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class TransactionTypeRepository extends BaseRepository<TransactionType> {

    public TransactionTypeRepository(EntityManager em) {
        super(TransactionType.class, em);
    }

    public TransactionType findByBk(String transactionCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM transaction_type \n");
        sb.append("where transaction_code = :transactionCode \n");

        Query query = em.createNativeQuery(sb.toString(), TransactionType.class);
        query.setParameter("transactionCode", transactionCode);

        try {
            return (TransactionType) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
