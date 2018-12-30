package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;


@Embeddable
public class Pesel {
    private final static int[] pesel_coefs = {9, 7, 3, 1};

    @Column
    private String pesel;


    public Pesel(String pesel) throws IllegalArgumentException
    {
        setPesel(pesel);
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) throws IllegalArgumentException
    {
        if(isValid(pesel))
            this.pesel = pesel;
        else
            throw new IllegalArgumentException("Wrong pesel");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pesel pesel1 = (Pesel) o;
        return Objects.equals(pesel, pesel1.pesel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pesel);
    }

    private boolean isValid(String pesel)
    {
        boolean isCorrect = false;
        char[] tokens = pesel.toCharArray();
        if(tokens.length == 11)
        {
            try {
                int checksum_control = Integer.parseInt(String.valueOf(tokens[tokens.length - 1]));
                int sum = getChecksum(tokens);
                if(sum == checksum_control)
                    isCorrect = true;
            } catch (NumberFormatException exc){}
        }
        return isCorrect;
    }

    private int getChecksum(char[] pesel_tokens)
    {
        int sum = 0;
        for(int i=0; i< pesel_tokens.length-1 && sum!=-1; i++)
        {
            try
            {
                sum += pesel_coefs[i%pesel_coefs.length] * Integer.parseInt(String.valueOf(pesel_tokens[i]));
            }
            catch (NumberFormatException exc)
            {
                sum = -1;
            }
        }
        return sum%10;
    }
}
