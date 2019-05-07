package nl.fontysproject.authentication.service;

import nl.fontysproject.authentication.web.dto.UserDto;

public interface UserService {
	long add(UserDto user) throws Exception;
}