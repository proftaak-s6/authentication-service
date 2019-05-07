package nl.fontysproject.authentication.web.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import nl.fontysproject.authentication.domain.validation.annotation.AllowedValues;
import nl.fontysproject.authentication.domain.validation.annotation.Bsn;
import nl.fontysproject.authentication.domain.validation.annotation.DateFormat;

public class UserDto {

    @NotNull
    private long brpId;

    @NotNull
    @NotBlank
    @Bsn
    private String bsn;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @DateFormat(pattern = "dd-MM-yyyy")
    private String birthday;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @AllowedValues(values = { "driver", "police", "government" })
    private Set<String> roles;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    public long getBrpId() {
        return brpId;
    }

    public void setBrpId(long brpId) {
        this.brpId = brpId;
    }
}