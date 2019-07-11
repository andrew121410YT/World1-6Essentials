package World16.Main;

import World16.Commands.*;
import World16.Commands.tp.tpa;
import World16.Commands.tp.tpaccept;
import World16.Commands.tp.tpdeny;
import World16.CustomInventorys.CustomInventoryManager;
import World16.Events.*;
import World16.Managers.CustomConfigManager;
import World16.Managers.JailManager;
import World16.Utils.API;
import World16.Utils.Metrics;
import World16.Utils.SetListMap;
import World16.Utils.Translate;
import World16.test.test1;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Main plugin;

    //Managers
    private CustomConfigManager customconfig;
    private CustomInventoryManager customInventoryManager;
    private JailManager jailManager;

    private API api;

    private PluginManager pm = Bukkit.getPluginManager();

    public void onEnable() {
        plugin = this;
        api = new API(plugin);

        regCustomManagers();
        regFileConfigGEN();
        regEvents();
        regCommands();
        regbstats();

        getLogger().info("[World1-6Essentials] is now loaded!");
    }

    public void onDisable() {
        SetListMap.clearSetListMap();
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
        new setjail(this.customconfig, this, this.jailManager);
        new setspawn(this.customconfig, this);
        new jail(this.customconfig, this, this.jailManager);
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
        new waitdo(this.customconfig, this);
        new runCommands(this.customconfig, this);
        new wformat(this.customconfig, this);
        new xyzdxdydz(this.customconfig, this);
        new workbench(this.customconfig, this);
    }

    private void regEvents() {
        //Bukkit.getServer().getPluginManager().registerEvents(this, this);
        new OnPlayerJoinEvent(this);
        new OnPlayerQuitEvent(this);
        //...
        new OnPlayerDeathEvent(this);
        new OnPlayerDamageEvent(this);
        new OnPlayerTeleportEvent(this);
        //...
        new OnPlayerBedEnterEvent(this);
        new OnJoinTitleEvent(this);
        //...
        new OnInventoryClickEvent(this, this.customInventoryManager);
        new OnAsyncPlayerChatEvent(this);
        new OnPlayerInteractEvent(this);
        new OnPlayerMoveEvent(this);
    }

    private void regFileConfigGEN() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();
    }

    private void regCustomManagers() {
        this.customconfig = new CustomConfigManager(this);
        customconfig.registerAllCustomConfigs();

        this.customInventoryManager = new CustomInventoryManager(this);
        this.customInventoryManager.registerAllCustomInventorys();

        this.jailManager = new JailManager(this.customconfig, this);
        this.jailManager.getAllJailsFromConfig();
    }

    public void checkForPlugins() {

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

    public Main getPlugin() {
        return plugin;
    }

    public API getApi() {
        return this.api;
    }

    public CustomInventoryManager getCustomInventoryManager() {
        return customInventoryManager;
    }

    public CustomConfigManager getCustomConfigManager() {
        return customconfig;
    }
}