package nl.fontysproject.authentication.domain.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@ConfigBundle("appConfig")
public class AppConfig {

    private String[] allowedRoles;

    @Inject
    private JwtConfig jwt;

    @Inject
    private ServicesConfig services;

    public JwtConfig jwt() {
        return jwt;
    }
    public ServicesConfig services() {return services; }

    public String[] getAllowedRoles() {
        return allowedRoles;
    }

    public void setAllowedRoles(String[] allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    public void setJwt(JwtConfig jwt) {
        this.jwt = jwt;
    }


}
