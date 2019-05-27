package nl.fontysproject.authentication.web.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import nl.fontysproject.authentication.domain.validation.annotation.AllowedValues;
import nl.fontysproject.authentication.domain.validation.annotation.Bsn;
import nl.fontysproject.authentication.domain.validation.annotation.DateFormat;

public class UserDto {

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
    @Pattern(regexp = "^(((\\+31|0|0031)6)[1-9][0-9]{7})$", message = "Ongeldig telefoonnummer.")
    private String phoneNumber;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{3} ?(?!sa|sd|ss)[a-z]{2}$", flags = {Pattern.Flag.CASE_INSENSITIVE}, message = "Ongeldige postcode")
    private String zipCode;

    @NotNull
    @NotBlank
    @DateFormat(pattern = "dd/MM/yyyy")
    private String birthday;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @AllowedValues(values = {"user", "driver", "police", "government"})
    private Set<String> roles;

    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}