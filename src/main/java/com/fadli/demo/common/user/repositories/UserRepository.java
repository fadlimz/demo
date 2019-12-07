package com.fadli.demo.common.user.repositories;

import com.fadli.demo.base.parentClasses.BaseRepository;
import com.fadli.demo.common.user.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepository extends BaseRepository<User> {

    public UserRepository(EntityManager em) {
        super(User.class, em);
    }

    public User findByBk(String userCode) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM user \n");
        sb.append("where user_code = :userCode \n");

        Query query = em.createNativeQuery(sb.toString(),User.class);
        query.setParameter("userCode", userCode);

        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> getList() {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * \n");
        sb.append("FROM user \n");
        sb.append("ORDER BY user_code \n");

        Query query = em.createNativeQuery(sb.toString(), User.class);

        return query.getResultList();
    }
}
