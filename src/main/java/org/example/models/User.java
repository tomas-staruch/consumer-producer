package org.example.models;

import java.util.Objects;

public class User {
    private final int id;
    private final String guid;
    private final String name;

    public User(int id, String guid, String name) {
        this.id = id;
        this.guid = guid;
        this.name = name;
    }

    public User(Object[] objects) {
        this.id = (int) objects[0];
        this.guid = (String) objects[1];
        this.name = (String) objects[2];
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGuid() {
        return guid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id && Objects.equals(guid, user.guid) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, guid, name);
    }

    @Override
    public String toString() {
        return "User {" + id + ", " + guid + ", " + name + '}';
    }
}
