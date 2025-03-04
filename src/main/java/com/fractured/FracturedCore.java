package com.fractured;

import com.fractured.cevents.EventManager;
import com.fractured.commands.*;
import com.fractured.commands.home.DelHomeCommand;
import com.fractured.commands.home.HomeCommand;
import com.fractured.commands.home.SetHomeCommand;
import com.fractured.commands.kits.CreateKitCommand;
import com.fractured.commands.kits.DeleteKitCommand;
import com.fractured.commands.kits.KitCommand;
import com.fractured.commands.kits.KitsCommand;
import com.fractured.commands.messages.MessageCommand;
import com.fractured.commands.messages.ReplyCommand;
import com.fractured.commands.messages.SocialSpyCommand;
import com.fractured.commands.team.TeamChatCommand;
import com.fractured.commands.team.TeamCommand;
import com.fractured.commands.tpa.TpaCommand;
import com.fractured.config.Config;
import com.fractured.enchants.EnchantManager;
import com.fractured.events.*;
import com.fractured.events.home.HomeManager;
import com.fractured.events.inventory.InventoryClickListener;
import com.fractured.events.inventory.InventoryCloseListener;
import com.fractured.kits.KitManager;
import com.fractured.menu.MenuManager;
import com.fractured.shields.ShieldManager;
import com.fractured.storage.Storage;
import com.fractured.user.UserManager;
import com.fractured.util.EmptyClass;
import com.fractured.util.PAPIExpansion;
import com.fractured.util.globals.ConfigKeys;
import com.fractured.util.globals.Messages;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public final class FracturedCore extends JavaPlugin {
    /* I've switched this to a more functional design. Instead of relying on the
       FracturedCore plugin instance to gain access to these, or just passing an
       instance of these to where ever it's needed during initialization, and
       then storing those references in the class that it's passed to (dependency
       injection), I've made all of these static, since there only ever needs
       to exist one of these objects in the code at a time. */

    private static Config config;
    private static Config messages;
    private static Config kits;
    private static Config homes;
    private static Storage storage;

    /**
     * This is just used for scheduling, please don't access this anywhere outside of this class.
     */
    private static FracturedCore plugin;

    public FracturedCore()
    {
        plugin = this;
    }

    // You no longer need to pass the plugin to the scheduler
    public static BukkitTask runAsync(Runnable runnable)
    {
        // todo switch to virtual threads?
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public static BukkitTask runDelay(Runnable runnable, long delay)
    {
        return Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }

    public static BukkitTask runDelay(BukkitRunnable runnable, long delay)
    {
        return runnable.runTaskLater(plugin, delay);
    }

    public static NamespacedKey newNamespacedKey(String key)
    {
        return new NamespacedKey(plugin, key);
    }

    public static Config getFracturedConfig()
    {
        return config;
    }

    public static Config getMessages()
    {
        return messages;
    }

    public static Config getKits()
    {
        return kits;
    }

    public static Config getHomes()
    {
        return homes;
    }

    public static Storage getStorage()
    {
        return storage;
    }

    private static MenuManager menuManager;

    private void registerEvents() {
        final PluginManager manager = getServer().getPluginManager();

        manager.registerEvents(new UserManager(), this);
        manager.registerEvents(menuManager, this);
        manager.registerEvents(new ShieldManager(), this);
        manager.registerEvents(new WorldManager(), this);
        manager.registerEvents(new EnchantManager(), this);
        manager.registerEvents(new KitManager(), this);
        manager.registerEvents(new HomeManager(), this);

        manager.registerEvents(new ChatListener(), this);
        manager.registerEvents(new CommandListener(), this);
        manager.registerEvents(new DeathListener(), this);
        manager.registerEvents(new HealthListener(), this);
        manager.registerEvents(new ExplodeListener(), this);
        manager.registerEvents(new InventoryCloseListener(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new LeaveListener(), this);
        manager.registerEvents(new InventoryClickListener(), this);

        manager.registerEvents(new RespawnListener(), this);
        manager.registerEvents(new SleepListener(), this);
    }

    private void registerCommands()
    {
        getCommand("coords").setExecutor(CoordsCommand::coords);
        getCommand("teamchat").setExecutor(TeamChatCommand::teamchat);
        getCommand("team").setExecutor(TeamCommand::team);
        getCommand("configs").setExecutor(ConfigsCommand::configs);
        getCommand("discord").setExecutor(DiscordCommand::discord);
        getCommand("upgrades").setExecutor(UpgradesCommand::upgrades);
        getCommand("confirm").setExecutor(ConfirmationManager::confirm);
        getCommand("world").setExecutor(WorldCommand::world);
        getCommand("home").setExecutor(HomeCommand::home);
        getCommand("event").setExecutor(EventCommand::event);
        getCommand("shield").setExecutor(ShieldCommand::shield);
        getCommand("customenchant").setExecutor(CustomEnchantCommand::customEnchant);
        getCommand("bypassregions").setExecutor(BypassRegionsCommand::bypassRegions);
        getCommand("setmaxhealth").setExecutor(SetMaxHealthCommand::setMaxHealth);
        getCommand("spawn").setExecutor(SpawnCommand::spawn);
        getCommand("tpa").setExecutor(TpaCommand::tpa);
        getCommand("endroom").setExecutor(EndCommand::end);
        getCommand("createkit").setExecutor(CreateKitCommand::createkit);
        getCommand("kit").setExecutor(KitCommand::kit);
        getCommand("kits").setExecutor(KitsCommand::kits);
        getCommand("deletekit").setExecutor(DeleteKitCommand::deletekit);
        getCommand("message").setExecutor(MessageCommand::message);
        getCommand("reply").setExecutor(ReplyCommand::reply);
        getCommand("socialspy").setExecutor(SocialSpyCommand::socialSpy);
        getCommand("sethome").setExecutor(SetHomeCommand::sethome);
        getCommand("home").setExecutor(HomeCommand::home);
        getCommand("delhome").setExecutor(DelHomeCommand::delhome);
        getCommand("enchant").setExecutor(EnchantCommand::enchant);
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable()
    {
        config = new Config(this, ConfigKeys.class, "config.yml");
        messages = new Config(this, Messages.class, "messages.yml");
        kits = new Config(this, EmptyClass.class, "kits.yml");
        homes = new Config(this, EmptyClass.class, "home.yml");
        storage = Storage.newStorage(config);

        menuManager = new MenuManager();

        registerEvents();
        registerCommands();

        // After we've regsitered events and managers (LIke MenuManager)
        storage.initServerResources();

        // Register custom events (cevents)
        EventManager.init();

        // Cache kits
        KitManager.initKits();

        // Register placeholderapi
        if( Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new PAPIExpansion().register();
        }
    }

    @Override
    public void onDisable()
    {
        // fixme wait for db to flush? Consult the bukkit async scheduler probably
        // Also make any additional db updates for saving like the team cache maybe?

        if (ShieldManager.pooledBlocks.size() > 0)
        {
            ShieldManager.clearAllParameters();
        }

        for (Player players : Bukkit.getOnlinePlayers())
        {
            players.kickPlayer(Messages.PLUGIN_RESTARTING);
        }
    }
}
