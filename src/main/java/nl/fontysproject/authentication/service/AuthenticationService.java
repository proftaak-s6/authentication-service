package nl.fontysproject.authentication.service;

import nl.fontysproject.authentication.domain.exception.UnauthorizedException;
import nl.fontysproject.authentication.domain.model.User;

public interface AuthenticationService {

	User authenticate(String username, String password) throws UnauthorizedException;
    
}