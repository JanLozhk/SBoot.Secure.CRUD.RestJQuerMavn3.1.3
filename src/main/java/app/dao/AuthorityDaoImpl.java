package app.dao;

import app.model.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository //не проксируются, объявление бинами @Bean
public class AuthorityDaoImpl {

    @Autowired
    EntityManager entityManager;

    public Authority findByAuthority(String auth) {
        TypedQuery<Authority> query = entityManager.createQuery(
                "from Authority a where a.authority = :auth ", Authority.class);
        query.setParameter("auth", auth);
        return query.getResultList().get(0);
    }

}
