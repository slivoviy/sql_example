package com.example.sql_example.domain;

public class Friendship {
    public final String id;
    public final String firstId;
    public final String secondId;
    public final boolean isConfirm;

    public Friendship(String id, String firstId, String secondId, boolean isConfirm) {
        this.id = id;
        this.firstId = firstId;
        this.secondId = secondId;
        this.isConfirm = isConfirm;
    }
}
