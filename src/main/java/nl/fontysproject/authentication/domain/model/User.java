package nl.fontysproject.authentication.domain.model;

import nl.fontysproject.authentication.domain.validation.annotation.AllowedValues;
import nl.fontysproject.authentication.domain.validation.annotation.Bsn;
import nl.fontysproject.authentication.domain.validation.annotation.DateFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@NamedQuery(name = User.FIND_BY_USERNAME_AND_PASSWORD, query = "SELECT u FROM users u WHERE username = :username AND password = :password")
@Entity(name = "users")
public class User {

    public static final String FIND_BY_USERNAME_AND_PASSWORD = "user.FindByUserNameAndPassword";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private long brpId;

    @Column
    @NotNull
    @NotBlank
    @Bsn
    private String bsn;

    @Column(unique = true)
    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @DateFormat(pattern = "dd-MM-yyyy")
    private String birthday;

    @Column(unique = true)
    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles")
    @NotNull
    @AllowedValues(values = {"driver", "police", "government"})
    private Set<String> roles;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the roles
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the bsn
     */
    public String getBsn() {
        return bsn;
    }

    /**
     * @param bsn the bsn to set
     */
    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }


    public long getBrpId() {
        return brpId;
    }

    public void setBrpId(long brpId) {
        this.brpId = brpId;
    }
}