package com.fractured;

import com.fractured.commands.*;
import com.fractured.events.*;
import com.fractured.events.inventory.InventoryClickListener;
import com.fractured.events.inventory.InventoryCloseListener;
import com.fractured.events.world.BreakListener;
import com.fractured.events.world.PlaceListener;
import com.fractured.utilities.Config;
import com.fractured.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FracturedCore extends JavaPlugin {

    public static FracturedCore instance;
    public static Config getDatabase;

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new BreakListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new HungerListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(), this);
        getServer().getPluginManager().registerEvents(new PlaceListener(), this);
    }

    private void registerCommands() {
        getCommand("coords").setExecutor(new CoordsCmd());
        getCommand("gm").setExecutor(new GmCmd());
        getCommand("me").setExecutor(new MeCmd());
        getCommand("teamchat").setExecutor(new TeamChatCmd());
        getCommand("team").setExecutor(new TeamCmd());
    }

    private void establishDatabase() {
        getDatabase = new Config(this, getDataFolder(), "database", "database.yml");
    }

    @Override
    public void onEnable() {
        instance = this;

        registerEvents();
        registerCommands();
        establishDatabase();
    }

    @Override
    public void onDisable() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.kickPlayer(Utils.Color("&eServer restarting..."));
        }
    }
}