package nl.fontysproject.authentication.domain;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@ConfigBundle("appConfig")
public class AppConfig {

    @Inject
    private JwtConfig jwt;

    public JwtConfig jwt() {
        return jwt;
    }

    public void setJwt(JwtConfig jwt) {
        this.jwt = jwt;
    }
}