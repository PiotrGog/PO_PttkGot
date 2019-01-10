package pttk.service;

import java.util.ArrayList;
import java.util.List;

public class StringContainer {
    public List<StringWrapper> getStringList() {
        return stringList;
    }

    public void setStringList(List<StringWrapper> stringList) {
        this.stringList = stringList;
    }

    public StringContainer() {
        stringList = new ArrayList<>();
    }

    public StringContainer(List<StringWrapper> stringList) {

        this.stringList = stringList;
    }

    private List<StringWrapper> stringList;
}
