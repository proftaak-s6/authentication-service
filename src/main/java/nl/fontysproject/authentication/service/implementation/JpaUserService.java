package nl.fontysproject.authentication.service.implementation;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import nl.fontysproject.authentication.domain.model.User;
import nl.fontysproject.authentication.service.UserService;

import java.util.List;

@ApplicationScoped
public class JpaUserService implements UserService {

    @PersistenceContext(name = "PU")
    private EntityManager manager;

    @Override
    @Transactional
    public long add(User user) {
        manager.persist(user);

        return user.getId();
    }

    @Override
    public User find(long id) {
        return manager.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return manager
                .createQuery("SELECT u FROM users u", User.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void update(User user) {
        manager.merge(user);
    }
}
