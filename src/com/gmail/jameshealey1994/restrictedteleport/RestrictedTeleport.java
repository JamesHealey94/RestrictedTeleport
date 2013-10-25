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
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("rtp")) {
                if (args.length > 0) {
                    final Player target = getServer().getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Player '" + args[0] + "' is not online or is invalid");
                        return false;
                    } else {
                        if (!(player.hasPermission("rtp.teleport") || player.hasPermission("rtp.teleport.override"))) {
                            player.sendMessage(ChatColor.RED + "You do not have permissions to teleport right now");
                            return true;
                        }
                        if (!(target.hasPermission("rtp.beteleportedto") || player.hasPermission("rtp.teleport.override"))) {
                            player.sendMessage(ChatColor.RED + target.getDisplayName() + " cannot be teleported to right now");
                            return true;
                        }

                        player.teleport(target.getLocation());
                        player.sendMessage(ChatColor.GOLD + "Teleported to " + target.getDisplayName());

                        if (!player.hasPermission("rtp.teleport.silent")) {
                            target.sendMessage(ChatColor.GRAY + player.getDisplayName() + " teleported to you");
                        }
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Please specify a player to teleport to");
                    return false;
                }
            }
        }

        sender.sendMessage(ChatColor.RED + "This command can only be used by online players");
        return true;
    }
}