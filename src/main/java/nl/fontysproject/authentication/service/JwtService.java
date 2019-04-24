package nl.fontysproject.authentication.service;

import nl.fontysproject.authentication.domain.model.JwtToken;
import nl.fontysproject.authentication.domain.model.User;

public interface JwtService {

	JwtToken createToken(User user);

}