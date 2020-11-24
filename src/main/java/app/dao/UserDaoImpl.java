package app.dao;

import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;*/
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository//не проксируются, объявление бинами @Bean
public class UserDaoImpl implements UserDao {

    @Autowired
    EntityManager entityManager;

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(findUserById(id));
    }

    @Override
    public User findUserById(long id) {
        System.out.println(entityManager.find(User.class, id));
        return entityManager.find(User.class, id);
    }

    @Override
    public User findUserByLogin(String login) {
        TypedQuery<User> query = entityManager.createQuery(
                "from User u where u.login = :login ", User.class);
        query.setParameter("login", login);
//        User user = query.getSingleResult();
 /*       List<GrantedAuthority> authorities =
                user.getAuthorityList()
                        .stream()
                        .map(e -> new SimpleGrantedAuthority(e.getAuthority()))
                        .collect(Collectors.toList());*/
        return query.getSingleResult();
//        user;
    }
}