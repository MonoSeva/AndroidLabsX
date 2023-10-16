package com.example.appnanrj;

import java.io.Serializable;

public class Name implements Serializable {
    private String name;

    public Name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}