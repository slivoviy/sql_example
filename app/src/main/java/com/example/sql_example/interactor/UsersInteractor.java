package com.example.sql_example.interactor;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.sql_example.domain.Friendship;
import com.example.sql_example.domain.User;
import com.example.sql_example.repository.DatabaseRepository;

import java.util.ArrayList;

public class UsersInteractor {
    private DatabaseRepository repository;

    public UsersInteractor(Context context) {
        repository = new DatabaseRepository(context);
    }

    public boolean insertUser(String name, String password) {
        return repository.insertUser(name, password);
    }

    public User getUser(String name, String password) {
        return repository.getUser(name, password);
    }

    public ArrayList<User> getUsers(int limit) {
        return repository.getUsers(limit);
    }

    public ArrayList<User> getUsers() {
        return repository.getUsers();
    }

    public Friendship getFriendship(String firstId, String secondId) {
        return repository.getFriendship(firstId, secondId);
    }

    public void insertFriendship(String firstId, String secondId) {
        repository.insertFriendship(firstId, secondId);
    }

    public byte checkFriendship(String firstId, String secondId) {
        return repository.checkFriendship(firstId, secondId);
    }

    public void confirmFriendship(String firstId, String secondId) {
        repository.confirmFriendship(firstId, secondId);
    }

    public void deleteFriendship(String firstId, String secondId) {
        repository.deleteFriendship(firstId, secondId);
    }
}