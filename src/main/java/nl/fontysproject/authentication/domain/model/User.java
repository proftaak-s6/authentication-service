package nl.fontysproject.authentication.domain.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = User.FIND_BY_USERNAME_AND_PASSWORD, query = "SELECT u FROM users u WHERE username = :username AND password = :password")
@Entity(name = "users")
public class User {

    public static final String FIND_BY_USERNAME_AND_PASSWORD = "user.FindByUserNameAndPassword";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long brpId;

    @Column
    private String bsn;

    @Column(unique = true)
    private String email;

    @Column
    private String birthday;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles")
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