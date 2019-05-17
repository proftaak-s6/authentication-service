package nl.fontysproject.authentication.service.implementation;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import nl.fontysproject.authentication.domain.model.User;
import nl.fontysproject.authentication.service.UserService;
import nl.fontysproject.authentication.web.dto.UserDto;

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
}
