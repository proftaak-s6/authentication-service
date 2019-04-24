package nl.fontysproject.authentication.domain.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class JwtToken {

    @NotNull
    @NotBlank
    private String token;

    public JwtToken() {

    }
    
    public JwtToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}