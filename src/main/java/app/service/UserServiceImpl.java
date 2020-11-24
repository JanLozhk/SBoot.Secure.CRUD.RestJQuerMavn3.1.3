package app.service;

import app.dao.AuthorityDaoImpl;
import app.dao.UserDao;
import app.model.Authority;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service//не проксируются, объявление бинами @Bean
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthorityDaoImpl authorityDao;

    @Transactional
    @Override
    public void create(User user) {
        updateAuthorityList(user);
        userDao.create(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public void update(Long id, User user) {
        User old = userDao.findUserById(id);
        old.setFirstName(user.getFirstName());
        old.setLastName(user.getLastName());
        old.setAge(user.getAge());
        old.setEmail(user.getEmail());
        old.setLogin(user.getLogin());
        old.setPassword(user.getPassword());
        old.setAuthorityList(user.getAuthorityList());
        updateAuthorityList(old);
        userDao.update(old);
    }

    @Transactional
    @Override
    public void delete(long id) {
        userDao.delete(id);
    }

    @Transactional//transactional javax?
    @Override
    public User findUserById(long id) {
        return userDao.findUserById(id);
    }

    @Transactional
    @Override
    public User findByLogin(String login) {
        return userDao.findUserByLogin(login);
    }

    private void updateAuthorityList(User user) {
        List<Authority> authorityList = new ArrayList<>();
        for (Authority authority : user.getAuthorityList()) {
            authorityList.add(authorityDao.findByAuthority(authority.getAuthority()));
        }
        user.setAuthorityList(authorityList);
    }
}
