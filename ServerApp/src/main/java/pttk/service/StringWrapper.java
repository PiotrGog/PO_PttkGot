package pttk.service;

public class StringWrapper {
    private String string;

    public StringWrapper() {
        string = "";
    }

    public StringWrapper(String string) {

        this.string = string;
    }

    public String getString() {

        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
