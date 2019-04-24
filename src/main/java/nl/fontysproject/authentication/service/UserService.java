package nl.fontysproject.authentication.service;

import nl.fontysproject.authentication.domain.model.User;

public interface UserService {
	long add(User user) throws Exception;
}