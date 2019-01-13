package pttk.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import pttk.validators.PeselConstraint;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@MappedSuperclass
public abstract class User {
    @Transient
    private static final String INVALID_SURNAME_MSG = "Invalid surname";
    @Transient
    private static final String INVALID_NAME_MSG = "Invalid name";
    @Transient
    private static final String NAME_REGEX = "[a-zA-Z]+";

    @NotNull
    @Length(min = 1, max = 15)
    private String login;

    @Column(name = "pass")
    @NotNull
    @Length(min = 1, max = 15)
    private String password;

    @Column
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Imię powinno zawierać tylko znaki alfabetu")
    @Length(min = 1, max = 20)
    private String name;

    @Column
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Nazwisko powinno zawierać tylko znaki alfabetu")
    @Length(min = 1, max = 20)
    private String surname;

    @Column(unique = true, nullable = false)
    @javax.validation.constraints.Email(message = "Błędny adres email")
    private String email;

    @Column(unique = true, nullable = false)
    @PeselConstraint(message = "Błedny numer pesel")
    private String pesel;

    public User() {
    }

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
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getPesel() {
        return pesel;
    }


}
