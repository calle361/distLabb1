package org.example.demo.ui.facades;

import org.example.demo.bo.PermissionLevel;

public class UserInfo {
    private int uid;
    private String name;
    private PermissionLevel permissionLevel;

    public UserInfo(int uid, String name, PermissionLevel permissionLevel) {
        this.uid = uid;
        this.name = name;
        this.permissionLevel = permissionLevel;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
}
