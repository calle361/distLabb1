package org.example.demo.ui.facades;

import org.example.demo.bo.PermissionLevel;

/**
 * Represents a user with associated details such as user ID,
 * name, and permission level.
 */
public class UserInfo {

    private int uid;                    // Unique identifier for the user
    private String name;                // Name of the user
    private PermissionLevel permissionLevel;  // User's permission level

    /**
     * Constructs a UserInfo object with the specified user details.
     *
     * @param uid The unique identifier for the user.
     * @param name The name of the user.
     * @param permissionLevel The permission level of the user.
     */
    public UserInfo(int uid, String name, PermissionLevel permissionLevel) {
        this.uid = uid;
        this.name = name;
        this.permissionLevel = permissionLevel;
    }

    /**
     * Gets the user ID.
     *
     * @return The unique identifier for the user.
     */
    public int getUid() {
        return uid;
    }

    /**
     * Sets the user ID.
     *
     * @param uid The unique identifier for the user to set.
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * Gets the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name of the user to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the permission level of the user.
     *
     * @return The permission level of the user.
     */
    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    /**
     * Sets the permission level of the user.
     *
     * @param permissionLevel The permission level to set for the user.
     */
    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
}