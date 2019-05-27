package nl.fontysproject.authentication.service;

import nl.fontysproject.authentication.domain.model.User;

import java.util.List;

public interface UserService {
	long add(User user) throws Exception;
    User find(long id);
    List<User> getAll();
    void update(User user);
}