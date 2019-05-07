package nl.fontysproject.authentication.service.implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import nl.fontysproject.authentication.domain.AppConfig;
import nl.fontysproject.authentication.domain.model.JwtToken;
import nl.fontysproject.authentication.domain.model.User;
import nl.fontysproject.authentication.service.JwtService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class DefaultJwtService implements JwtService {

    @Inject
    private AppConfig config;

    private Algorithm algorithm;

    @PostConstruct
    private void init() {
        algorithm = Algorithm.HMAC256(config.jwt().getSecret());
    }

    @Override
    public JwtToken createToken(User user) {

        String token = JWT.create()
                .withIssuer(config.jwt().getIssuer())
                .withAudience(config.jwt().getAudience())
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId())
                .withClaim("brpId", user.getBrpId())
                .withArrayClaim("roles", user.getRoles().toArray(new String[0]))
                .withIssuedAt(new Date())
                .withExpiresAt(convertToDate(LocalDate.now().plusDays(1)))
                .sign(algorithm);

        return new JwtToken(token);
    }

    private static Date convertToDate(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
