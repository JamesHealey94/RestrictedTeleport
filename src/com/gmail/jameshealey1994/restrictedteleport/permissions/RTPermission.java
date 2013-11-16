package com.gmail.jameshealey1994.restrictedteleport.permissions;

import org.bukkit.permissions.Permission;

/**
 * Enum containing Permission values.
 *
 * @author JamesHealey94 <jameshealey1994.gmail.com>
 */
public enum RTPermission {

    /**
     * Allows players to teleport to players who have the BE_TELEPORTED_TO
     * permission.
     */
    TELEPORT ("rtp.teleport"),

    /**
     * Allows you to be teleported to.
     */
    BE_TELEPORTED_TO ("rtp.beteleportedto"),

    /**
     * Allows you to teleport silently (assuming you have permissions to teleport).
     */
    TELEPORT_SILENT ("rtp.teleport.silent"),

    /**
     * Allows you to teleport anyone to anyone, regardless of permissions.
     */
    TELEPORT_OTHERS ("rtp.teleport.others"),

    /**
     * Allows you to teleport to anyone, regardless of their permissions.
     */
    TELEPORT_OVERRIDE ("rtp.teleport.override");

    /**
     * Permission value of the enum.
     */
    private Permission permission;

    /**
     * Constructor - Initialises permission value.
     *
     * @param permission    the string value of the permission
     */
    private RTPermission(final String permission) {
        this.permission = new Permission(permission);
    }

    /**
     * Returns the permission value of the enum.
     *
     * @return      the permission value of the enum
     */
    public Permission getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return permission.getName();
    }
}