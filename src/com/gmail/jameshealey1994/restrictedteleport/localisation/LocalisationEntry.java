package com.gmail.jameshealey1994.restrictedteleport.localisation;

/**
 * Enum to represent a single entry in a Localisation.
 * Holds the config key, the usage, and the default value.
 *
 * @author JamesHealey94 <jameshealey1994.gmail.com>
 */
public enum LocalisationEntry {

    /**
     * Message telling player they do not have permission to use a command.
     */
    ERR_PERMISSION_DENIED (
            "MsgPermissionDenied",
            null,
            "&cPermission denied"),

    /**
     * Message telling player they do not have permission to teleport.
     */
    ERR_PERMISSION_DENIED_TELEPORT (
            "MsgPermissionDeniedTeleport",
            null,
            "&cYou do not have permissions to teleport right now"),

    /**
     * Message telling player they do not have permission to teleport others.
     */
    ERR_PERMISSION_DENIED_TELEPORT_OTHERS (
            "MsgPermissionDeniedTeleportOthers",
            null,
            "&cYou do not have permissions to teleport other players"),

    /**
     * Message telling player they do not have permission to teleport silently.
     */
    ERR_PERMISSION_DENIED_TELEPORT_SILENT (
            "MsgPermissionDeniedTeleportSilent",
            null,
            "&cYou do not have permissions to teleport silently"),

    /**
     * Message telling player the target they specified cannot be teleported to.
     * %1$s - target player name
     */
    ERR_PLAYER_CANNOT_BE_TELEPORTED_TO (
            "MsgPlayerCannotBeTeleportedTo",
            "%1$s - target player name",
            "&c'%1$s' cannot be teleported to right now"),

    /**
     * Message telling user the command can only be used by players.
     */
    ERR_PLAYER_ONLY_COMMAND (
            "MsgPlayerOnlyCommand",
            null,
            "&cOnly players can use this command"),

    /**
     * Message telling user that the player specified cannot be found.
     * %1$s - invalid player name
     */
    ERR_PLAYER_NOT_FOUND (
            "MsgPlayerNotFound",
            "%1$s - invalid player name",
            "&cPlayer '%1$s' is not online or is invalid"),

    /**
     * Message telling user that they need to specify a player.
     */
    ERR_SPECIFY_PLAYER (
            "MsgSpecifyPlayer",
            null,
            "&cPlease specify a player"),

    /**
     * Message telling user that they have sent too many arguments
     * for a command.
     */
    ERR_TOO_MANY_ARGUMENTS (
            "MsgTooManyArguments",
            null,
            "&cToo many arguments"),

    /**
     * Message telling user that a shorter command is available when they
     * use the command /tp senderName targetName.
     * %1$s - command the user sent
     * %2$s - target name
     */
    MSG_SHORTER_COMMAND_AVAILABLE (
            "MsgShorterCommandAvailable",
            "%1$s - command the user sent",
            "&7Note: You can just do /%1$s %2$s"),

    /**
     * Message to sender confirming they teleported a player to another player
     * silently.
     * %1$s - teleporter name
     * %2$s - target name
     */
    MSG_TELEPORTED_PLAYER_TO_PLAYER_SILENTLY (
            "MsgTeleportedPlayerSilently",
            "%1$s - teleporter name\n# %2$s - target name",
            "&7Teleported '%1$s' to '%2$s' (Silently)"),

    /**
     * Message to player confirming they teleported to another player.
     * %1$s - target name
     */
    MSG_TELEPORTED (
            "MsgTeleportedToPlayer",
            "%1$s - target name",
            "&6Teleported to '%1$s'"),

    /**
     * Message telling player that they teleported to a player silently.
     * %1$s - target name
     */
    MSG_TELEPORTED_SILENTLY (
            "MsgTeleportedSilently",
            "%1$s - target name",
            "&6Teleported to '%1$s' (Silently)"),

    /**
     * Message telling player that they have been teleported to by another
     * player.
     * %1$s - teleporter name
     */
    MSG_TELEPORTED_TO_BY_PLAYER (
            "MsgTeleportedToByPlayer",
            "%1$s - teleporter name",
            "&7'%1$s' teleported to you"),

    /**
     * Message to sender confirming they teleported a player to another player.
     * %1$s - teleporter name
     * %2$s - target name
     */
    MSG_TELEPORTED_PLAYER_TO_PLAYER (
            "MsgTeleportedPlayer",
            "%1$s - teleporter name",
            "&7Teleported '%1$s' to '%2$s'");

    /**
     * The name of the entry, as found in the localisation file.
     */
    private String name;

    /**
     * The usage for the entry, used as a comment in the localisation file to
     * help the user.
     * An empty string should be ignored when writing to file.
     */
    private String usage;

    /**
     * The default value of the entry, as found in the localisation file.
     */
    private String defaultValue;

    /**
     * Private constructor.
     *
     * @param name          name of the entry, as found in the localisation file
     * @param usage         usage for the entry, used as a comment in the
     *                      localisation file to help the user, can be empty
     * @param defaultValue  default value of the entry, as found in the
     *                      localisation file
     */
    private LocalisationEntry(String name, String usage, String defaultValue) {
        this.name = name;
        this.usage = usage;
        this.defaultValue = defaultValue;
    }

    /**
     * Returns the name of the LocalistionEntry.
     *
     * @return  name of the LocalistionEntry
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the usage of the LocalistionEntry.
     *
     * @return  usage of the LocalistionEntry, empty string for no usage.
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Returns the default value of the LocalisationEntry.
     *
     * @return  default value of the LocalisationEntry
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        if (getUsage() == null) {
            return "\n" + getName() + ": '" + getDefaultValue().replace("'", "''") + "'\n";
        } else {
            return "\n# " + getUsage() + "\n" + getName() + ": '" + getDefaultValue().replace("'", "''") + "'\n";
        }
    }
}