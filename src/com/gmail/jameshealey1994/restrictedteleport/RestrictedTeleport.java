package com.gmail.jameshealey1994.restrictedteleport;

import com.gmail.jameshealey1994.restrictedteleport.commands.RTCommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Restricted Teleport Bukkit Plugin.
 *
 * @author JamesHealey94 <jameshealey1994.gmail.com>
 */
public final class RestrictedTeleport extends JavaPlugin {

    @Override
    public void onEnable() {
        // Set command executors and default command
        getCommand("rtp").setExecutor(new RTCommandExecutor(this));
    }
}