package com.gmail.jameshealey1994.restrictedteleport.commands;

import com.gmail.jameshealey1994.restrictedteleport.localisation.Localisation;
import com.gmail.jameshealey1994.restrictedteleport.localisation.LocalisationEntry;
import com.gmail.jameshealey1994.restrictedteleport.permissions.RTPermission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * CommandExecutor for Restricted Teleport plugin.
 *
 * @author JamesHealey94 <jameshealey1994.gmail.com>
 */
public class RTCommandExecutor implements CommandExecutor {

    /**
     * Plugin with associated server.
     */
    private final Plugin plugin;

    /**
     * Constructor - Initialises plugin.
     *
     * @param plugin    Plugin with associated server
     */
    public RTCommandExecutor(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Executes commands.
     *
     * @param sender            sender of the command
     * @param cmd               command the user sent
     * @param commandLabel      exact command the user sent
     * @param args              arguments given with the command
     * @return                  false if usage message needs to be shown, else
     *                          true
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        final Localisation localisation = new Localisation(plugin);
        final String teleporterName;
        final String targetName;
        final boolean silent;

        switch (args.length) {
            case 0: {
                sender.sendMessage(localisation.get(LocalisationEntry.ERR_SPECIFY_PLAYER));
                return true;
            }
            case 1: {
                // /tp as1lv3rn1nja
                if (!(sender instanceof Player)) {
                    sender.sendMessage(localisation.get(LocalisationEntry.ERR_PLAYER_ONLY_COMMAND));
                    return true;
                }

                teleporterName = ((Player) sender).getName();
                targetName = args[0];
                silent = false;
                break;
            }
            case 2: {
                if (args[1].equals(Flag.SILENT_FLAG.getString())) {
                    // /tp as1lv3rn1nja -s                      (Player) sender     as1lv3rn1nja        silent
                    if (!(sender instanceof Player)) {
                        return false;
                    }
                    teleporterName = ((Player) sender).getName();
                    targetName = args[0];
                    silent = true;
                } else {
                    // /tp as1lv3rn1nja as1lv3rn1nja            as1lv3rn1nja        as1lv3rn1nja
                    // /tp as1lv3rn1nja skillchickenz75         as1lv3rn1nja        skillchickenz75
                    teleporterName = args[0];
                    targetName = args[1];
                    silent = false;
                }
                break;
            }
            case 3: {
                // /tp as1lv3rn1nja as1lv3rn1nja -s             as1lv3rn1nja        as1lv3rn1nja        silent
                // /tp as1lv3rn1nja skillchickenz75 -s          as1lv3rn1nja        skillchickenz75     silent
                teleporterName = args[0];
                targetName = args[1];
                silent = (args[2].equals(Flag.SILENT_FLAG.getString()));
                break;
            }
            default: {
                sender.sendMessage(localisation.get(LocalisationEntry.ERR_TOO_MANY_ARGUMENTS));
                return false;
            }
        }

        // Check teleporter is a valid and online player.
        final Player teleporter = plugin.getServer().getPlayer(teleporterName);
        if (teleporter == null) {
            sender.sendMessage(localisation.get(LocalisationEntry.ERR_PLAYER_NOT_FOUND, teleporterName));
            return true;
        }

        // Check target is a valid and online player.
        final Player target = plugin.getServer().getPlayer(targetName);
        if (target == null) {
            sender.sendMessage(localisation.get(LocalisationEntry.ERR_PLAYER_NOT_FOUND, targetName));
            return true;
        }

        // Initial permissions check
        if (!sender.equals(target)) {
            if (sender.equals(teleporter)) {
                if (!teleporter.hasPermission(RTPermission.TELEPORT_OVERRIDE.getPermission())) {
                    if (!teleporter.hasPermission(RTPermission.TELEPORT.getPermission())) {
                        sender.sendMessage(localisation.get(LocalisationEntry.ERR_PERMISSION_DENIED_TELEPORT));
                        return true;
                    }
                    if (!target.hasPermission(RTPermission.BE_TELEPORTED_TO.getPermission())) {
                        sender.sendMessage(localisation.get(LocalisationEntry.ERR_PLAYER_CANNOT_BE_TELEPORTED_TO, target.getDisplayName()));
                        return true;
                    }
                }
            } else if (!sender.hasPermission(RTPermission.TELEPORT_OTHERS.getPermission())) {
                sender.sendMessage(localisation.get(LocalisationEntry.ERR_PERMISSION_DENIED_TELEPORT_OTHERS));
                return true;
            }
        }

        // Secondary permissions check, and actual teleporting.
        if (silent) {
            if (canSilentlyTeleport(sender)) {
                teleporter.teleport(target.getLocation());
                teleporter.sendMessage(localisation.get(LocalisationEntry.MSG_TELEPORTED_SILENTLY, target.getDisplayName()));
                if (!teleporter.equals(sender)) {
                    sender.sendMessage(localisation.get(LocalisationEntry.MSG_TELEPORTED_PLAYER_TO_PLAYER_SILENTLY, teleporter.getDisplayName(), target.getDisplayName()));
                }
                return true;
            } else {
                sender.sendMessage(localisation.get(LocalisationEntry.ERR_PERMISSION_DENIED_TELEPORT_SILENT));
                return false;
            }
        } else {
            teleporter.teleport(target.getLocation());
            teleporter.sendMessage(localisation.get(LocalisationEntry.MSG_TELEPORTED, target.getDisplayName()));
            target.sendMessage(localisation.get(LocalisationEntry.MSG_TELEPORTED_TO_BY_PLAYER, teleporter.getDisplayName()));
            if (!teleporter.equals(sender)) {
                sender.sendMessage(localisation.get(LocalisationEntry.MSG_TELEPORTED_PLAYER_TO_PLAYER, teleporter.getDisplayName(), target.getDisplayName()));
            }
            return true;
        }
    }

    /**
     * Returns if the sender has permissions to teleport silently.
     * 
     * @param sender    the sender to have permissions checked
     * @return          if the sender has permissions to teleport silently
     */
    public boolean canSilentlyTeleport(CommandSender sender) {
        return sender.hasPermission(RTPermission.TELEPORT_SILENT.getPermission());
    }
}