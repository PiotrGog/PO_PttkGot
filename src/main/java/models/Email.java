package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Embeddable
public class Email {
    private static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    @Column
    private String email;

    public Email(String email) throws IllegalArgumentException
    {
        setEmail(email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException
    {
        if (isValid(email))
            this.email = email;
        else
            throw new IllegalArgumentException("Invalid email address");
    }

    private boolean isValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }
}
