package models;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User {
    private static final String INVALID_SURNAME_MSG = "Invalid surname";
    private static final String INVALID_NAME_MSG = "Invalid name";
    private static final String NAME_REGEX = "[a-zA-Z]+";


    @Id
    private String login;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(unique = true, nullable = false)
    private Email email;

    @Column(unique = true, nullable = false)
    private Pesel pesel;

    public User(){}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(isNameValid(name))
            this.name = name;
        else
            throw new IllegalArgumentException(INVALID_NAME_MSG);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if(isNameValid(surname))
            this.surname = surname;
        else throw new IllegalArgumentException(INVALID_SURNAME_MSG);
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = new Email(email);
    }

    public Pesel getPesel() {
        return pesel;
    }

    public void setPesel(Pesel pesel) {
        this.pesel = pesel;
    }


    private boolean isNameValid(String name)
    {
        return name.matches(NAME_REGEX);
    }


}
