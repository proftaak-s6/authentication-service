package nl.fontysproject.authentication.service.implementation;

import nl.fontysproject.authentication.domain.exception.UnauthorizedException;
import nl.fontysproject.authentication.domain.model.User;
import nl.fontysproject.authentication.service.AuthenticationService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class JpaAuthenticationService implements AuthenticationService {

    @PersistenceContext(name = "PU")
    private EntityManager manager;

    @Override
    public User authenticate(String username, String password) throws UnauthorizedException {
        try {
            User user = manager.createNamedQuery(User.FIND_BY_USERNAME_AND_PASSWORD, User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();

            return user;
        } catch (NoResultException e) {
            throw new UnauthorizedException();
        }
    }
}
