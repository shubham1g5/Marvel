package com.example.shubham.marvel.model;

public class Author {
    private final String name;
    private final String role;

    public Author(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }
}
