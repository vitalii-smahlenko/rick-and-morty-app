package com.gmail.smaglenko.rickandmortyapp.model;

public enum Status {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("unknown");

    private String value;

    Status(String value) {
        this.value = value;
    }
}
