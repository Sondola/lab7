package data;

import java.io.Serializable;

public class Car implements Serializable {
    private String name;

    public Car(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
