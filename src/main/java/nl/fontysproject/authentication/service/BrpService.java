package nl.fontysproject.authentication.service;

import nl.fontysproject.authentication.domain.exception.VerificationException;
import nl.fontysproject.authentication.domain.model.User;
import nl.fontysproject.authentication.web.dto.UserDto;

public interface BrpService {

    User verify(UserDto user) throws VerificationException;

}
