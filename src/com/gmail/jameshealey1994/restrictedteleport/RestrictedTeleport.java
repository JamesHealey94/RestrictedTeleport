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
     * @param sender        sender of the command
     * @param cmd           command the user sent
     * @param commandLabel  exact command the user sent
     * @param args          arguments given with the command
     * @return              false if usage message needs to be shown, else true
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(cmd.getName().equalsIgnoreCase("rtp"))) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by online players");
            return true;
        }

        if (args.length > 0) {
            final Player target = getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player '" + args[0] + "' is not online or is invalid");
                return false;
            }

            final Player player = (Player) sender;
            if (!(player.hasPermission("rtp.teleport") || player.hasPermission("rtp.teleport.override"))) {
                sender.sendMessage(ChatColor.RED + "You do not have permissions to teleport right now");
                return true;
            }
            if (!(target.hasPermission("rtp.beteleportedto") || player.hasPermission("rtp.teleport.override"))) {
                sender.sendMessage(ChatColor.RED + target.getDisplayName() + " cannot be teleported to right now");
                return true;
            }

            final boolean silent = args.length > 1 && args[1].equalsIgnoreCase(SILENT_FLAG);
            if (silent) {
                if (player.hasPermission("rtp.teleport.silent")) {
                    sender.sendMessage(ChatColor.GOLD + "Teleported to " + target.getDisplayName() + " (Silently)");
                    player.teleport(target.getLocation());
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have permissions to teleport silently");
                    return false;
                }
            } else {
                player.teleport(target.getLocation());
                sender.sendMessage(ChatColor.GOLD + "Teleported to " + target.getDisplayName());
                target.sendMessage(ChatColor.GRAY + player.getDisplayName() + " teleported to you");
            }
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Please specify a player to teleport to");
            return false;
        }
    }
}