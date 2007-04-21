package org.appfuse.model;

import java.util.Date;

public class User extends BaseObject {
    private static final long serialVersionUID = 3257568390917667126L;
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return Returns firstName and lastName
     */
    public String getFullName() {
        return firstName + ' ' + lastName;
    }
}
