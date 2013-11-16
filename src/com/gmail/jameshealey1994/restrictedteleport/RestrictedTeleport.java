package com.gmail.jameshealey1994.restrictedteleport;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Restricted Teleport Bukkit Plugin.
 *
 * @author JamesHealey94 <jameshealey1994.gmail.com>
 */
public final class RestrictedTeleport extends JavaPlugin {

    /**
     * String signifying the silent flag.
     */
    public final static String SILENT_FLAG = "-s";

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
        if (!(cmd.getName().equalsIgnoreCase("rtp"))) {
            return false;
        }

        final String teleporterName;
        final String targetName;
        final boolean silent;

        switch (args.length) {
            case 1: {
                // /tp as1lv3rn1nja
                if (!(sender instanceof Player)) {
                    return false;
                }

                teleporterName = ((Player) sender).getName();
                targetName = args[0];
                silent = false;
                break;
            }
            case 2: {
                if (args[1].equals(SILENT_FLAG)) {
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
                    if ((sender instanceof Player) && (!(((Player) sender).hasPermission("rtp.teleport.others")))) {
                        sender.sendMessage(ChatColor.RED + "You do not have permissions to teleport other players");
                        sender.sendMessage(ChatColor.GRAY + "Perhaps you meant /" + commandLabel + " username");
                        return false;
                    }
                    teleporterName = args[0];
                    targetName = args[1];
                    silent = false;
                }
                break;
            }
            case 3: {
                // /tp as1lv3rn1nja as1lv3rn1nja -s             as1lv3rn1nja        as1lv3rn1nja        silent
                // /tp as1lv3rn1nja skillchickenz75 -s          as1lv3rn1nja        skillchickenz75     silent
                if ((sender instanceof Player) && (!(((Player) sender).hasPermission("rtp.teleport.others")))) {
                    sender.sendMessage(ChatColor.RED + "You do not have permissions to teleport other players");
                    sender.sendMessage(ChatColor.GRAY + "Perhaps you meant /" + commandLabel + " username");
                    return false;
                }
                teleporterName = args[0];
                targetName = args[1];
                silent = (args[2].equals(SILENT_FLAG));
                break;
            }
            default: {
                return false;
            }
        }

        final Player teleporter = getServer().getPlayer(teleporterName);
        if (teleporter == null) {
            sender.sendMessage(ChatColor.RED + "Player '" + teleporterName + "' is not online or is invalid");
            return false;
        }

        final Player target = getServer().getPlayer(targetName);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player '" + targetName + "' is not online or is invalid");
            return false;
        }

        if (!(teleporter.hasPermission("rtp.teleport") || teleporter.hasPermission("rtp.teleport.override"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permissions to teleport right now");
            return true;
        }
        if (!(target.hasPermission("rtp.beteleportedto") || teleporter.hasPermission("rtp.teleport.override"))) {
            sender.sendMessage(ChatColor.RED + target.getDisplayName() + " cannot be teleported to right now");
            return true;
        }

        if (silent) {
            if ((!(sender instanceof Player)) || ((sender instanceof Player) && (((Player) sender).hasPermission("rtp.teleport.silent")))) {
                teleporter.teleport(target.getLocation());
                teleporter.sendMessage(ChatColor.GOLD + "Teleported to " + target.getDisplayName() + " (Silently)");
                if (!(teleporter.equals(target) || teleporter.equals(sender))) {
                    sender.sendMessage(ChatColor.GRAY + "Teleported " + teleporter.getDisplayName() + " to " + target.getDisplayName() + " (Silently)");
                }
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permissions to teleport silently");
                return false;
            }
        } else {
            teleporter.teleport(target.getLocation());
            teleporter.sendMessage(ChatColor.GOLD + "Teleported to " + target.getDisplayName());
            target.sendMessage(ChatColor.GRAY + teleporter.getDisplayName() + " teleported to you");
            if (!(teleporter.equals(target) || teleporter.equals(sender))) {
                sender.sendMessage(ChatColor.GRAY + "Teleported " + teleporter.getDisplayName() + " to " + target.getDisplayName());
            }
            return true;
        }
    }
}