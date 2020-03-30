package com.example.sql_example.repository;

public class SQLScripts {
    static String initUsersDbScript() {
        return "create table user(" +
                "id integer primary key autoincrement," +
                "name text not null," +
                "password text not null" +
                ");";
    }

    static String initFriendshipDbScript() {
        return "create table friendship(" +
                "id integer primary key autoincrement, " +
                "firstId integer, " +
                "secondId integer, " +
                "isConfirm integer," +
                "foreign key (firstId) references user (id), " +
                "foreign key(secondId) references user (id) " +
                ");";
    }

    static String insertUserScript(String name, String password) {
        String _name = "\"" + name + "\"";
        String _password = "\"" + password + "\"";

        return "insert into user" +
                "(name, password)" +
                "values" +
                "(" + _name + "," + _password +
                ");";
    }

    static String getUserScript(String name, String password) {
        String _name = "\"" + name + "\"";
        String _password = "\"" + password + "\"";

        return "select * from user" +
                " where name = " + _name +
                " and password = " + _password +
                ";";
    }

    static String getAllUsersScript() {
        return "select * from user;";
    }

    static String getAllUsersScript(int limit) {
        return "select * from user" +
                " limit " + limit +
                ";";
    }

    static String insertFriendshipScript(int firstId, int secondId) {
        return "insert into friendship " +
                "(firstId, secondId, isConfirm)" +
                "values" +
                "(" + firstId + "," + secondId + ", 0" +
                ");";
    }

    static String getFriendshipScript(int firstId, int secondId) {
        return "select * from friendship " +
                "where firstId = " + firstId +
                " and secondId = " + secondId +
                " or firstId = " + secondId +
                " and secondId = " + firstId +
                ";";
    }

    static String updateIsConfirmScript(int firstId, int secondId) {
        return "update friendship " +
                "set isConfirm = 1 " +
                "where firstId = " + firstId +
                " and secondId = " + secondId +
                " or firstId = " + secondId +
                " and secondId = " + firstId +
                ";";
    }

    static String deleteFriendshipScript(int firstId, int secondId) {
        return "delete from friendship " +
                "where firstId = " + firstId +
                " and secondId = " + secondId +
                " or firstId = " + secondId +
                " and secondId = " + firstId +
                ";";
    }

    static String getAllFriendshipsScript() {
        return "select * from friendship";
    }

}