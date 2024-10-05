package org.example.demo.bo.models;

import org.example.demo.bo.PermissionLevel;

/**
 * Represents a user with a unique identifier, name, and a permission level.
 */
public class User {
    private int uid;
    private String name;
    private PermissionLevel permissionLevel;

    /**
     * Constructs a User with the specified user ID, name, and permission level.
     *
     * @param uid              the unique identifier of the user
     * @param name             the name of the user
     * @param permissionLevel  the permission level of the user
     */
    public User(int uid, String name, PermissionLevel permissionLevel) {
        this.uid = uid;
        this.name = name;
        this.permissionLevel = permissionLevel;
    }

    /**
     * Returns the unique identifier of the user.
     *
     * @return the user's ID
     */
    public int getUid() {
        return uid;
    }

    /**
     * Returns the name of the user.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the permission level of the user.
     *
     * @return the user's permission level
     */
    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }
}
