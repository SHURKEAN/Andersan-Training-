package com.db;

public class DbSmoke {
    public static void main(String[] args) throws Exception {
        try (var c = Db.get()) {
            System.out.println("Connected: " + !c.isClosed());
        }
    }
}
