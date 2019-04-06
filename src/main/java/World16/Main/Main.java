package World16.Main;

import World16.Commands.*;
import World16.Commands.tp.tpa;
import World16.Commands.tp.tpaccept;
import World16.Commands.tp.tpdeny;
import World16.CustomConfigs.CustomConfigManager;
import World16.CustomInventorys.CustomInventoryManager;
import World16.Events.*;
import World16.Utils.API;
import World16.Utils.Metrics;
import World16.Utils.Translate;
import World16.test.test1;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {

    private static Main plugin;

    //Managers
    private CustomConfigManager customconfig;
    private CustomInventoryManager customInventoryManager;

    private API api;

    //Maps
    public static Map<String, List<String>> tabCompleteMap = new HashMap<>();

    PluginManager pm = Bukkit.getPluginManager();

    public void onEnable() {
        plugin = this;
        api = new API();

        regCustomManagers();
        regFileConfigGEN();
        regAPIS();
        regEvents();
        regCommands();
        regbstats();

        getLogger().info("[World1-6Essentials] is now loaded!");
    }

    public void onDisable() {
        getLogger().info("[World1-6Essentials] is now disabled.");
    }

    private void regCommands() {
        new gmc(this);
        new gms(this);
        new gmsp(this);
        new gma(this);

        new day(this);
        new night(this);
        new feed(this);
        new heal(this);
        new fly(this);
        new debug(this);
        new commandblock(this);
        new bed(this);
        new ram(this);
        new spawn(this.customconfig, this);
        new echest(this);
        new sign(this);
        new key(this); //KEY COMMAND
        new colors(this);
        new setjail(this.customconfig, this);
        new setspawn(this.customconfig, this);
        new jail(this.customconfig, this);
        new afk(this);
        new flyspeed(this.customconfig, this);
        new isafk(this.customconfig, this);
        new back(this);
        new broadcast(this.customconfig, this);
        new god(this);
        new msg(customconfig, this);

        new tpa(this.customconfig, this);
        new tpaccept(this.customconfig, this);
        new tpdeny(this.customconfig, this);

        new test1(customconfig, this);
        new eram(this.customconfig, this, this.customInventoryManager);
    }

    private void regEvents() {
        //Bukkit.getServer().getPluginManager().registerEvents(this, this);
        new OnJoinEvent(this);
        new OnLeaveEvent(this);
        //...
        new OnDeathEvent(this);
        new PlayerDamageEvent(this);
        new OnTpEvent(this);
        //...
        new OnBedEnterEvent(this);
        new OnJoinTitleEvent(this);
        //...
        new InventoryClickEvent(this, this.customInventoryManager);
        new AsyncPlayerChatEvent(this);
        new PlayerInteractEvent(this);
        new CustomInventoryManager().registerAllCustomInventorys();
    }

    private void regFileConfigGEN() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();
    }

    private void regCustomManagers() {
        this.customconfig = new CustomConfigManager();
        customconfig.registerAllCustomConfigs();

        this.customInventoryManager = new CustomInventoryManager();
        this.customInventoryManager.registerAllCustomInventorys();
    }

    public void checkForPlugins() {

    }

    private void regAPIS() {
        api = new API();
    }

    private void regbstats() {
        Metrics metrics = new Metrics(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        {
            Player p = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("World1-6Essentials")) {
                p.sendMessage(Translate.chat("&6Made By Andrew121410 My -> Discord: Andrew121410#2035"));
                return true;
            }
        }
        return true;
    }

    public static Main getPlugin() {
        return plugin;
    }

    public API getApi() {
        return this.api;
    }

    public CustomInventoryManager getCustomInventoryManager() {
        return customInventoryManager;
    }
}