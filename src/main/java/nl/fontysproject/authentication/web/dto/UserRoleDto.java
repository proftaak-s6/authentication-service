package nl.fontysproject.authentication.web.dto;

import nl.fontysproject.authentication.domain.model.User;

public class UserRoleDto {

    private long id;
    private String firstName;
    private String lastName;
    private String[] roles;

    public UserRoleDto(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        roles = user.getRoles().toArray(new String[0]);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
