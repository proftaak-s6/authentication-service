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
    public long add(UserDto dto) {
        User user = dtoToUser(dto);
        
        manager.persist(user);

        return user.getId();
    }

    private User dtoToUser(UserDto dto) {
        User user = new User();

        user.setBirthday(dto.getBirthday());
        user.setBsn(dto.getBsn());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRoles(dto.getRoles());
        user.setUsername(dto.getUsername());

        return user;
    }
}
