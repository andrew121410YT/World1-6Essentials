package Main;

import Commands.afk;
import Commands.bed;
import Commands.colors;
import Commands.commandblock;
import Commands.day;
import Commands.debug;
import Commands.echest;
import Commands.feed;
import Commands.fly;
import Commands.flyspeed;
import Commands.gmc;
import Commands.gms;
import Commands.gmsp;
import Commands.heal;
import Commands.isafk;
import Commands.jail;
import Commands.night;
import Commands.ram;
import Commands.setjail;
import Commands.setspawn;
import Commands.sign;
import Commands.spawn;
import Events.OnBedEnter;
import Events.OnJoin;
import Events.OnJoinTitle;
import Events.OnLeave;
import KeyCommands.Key;
import KeyCommands.MutiKeys;
import Translate.Translate;
import Utils.API;
import Utils.CustomYmlManger;
import Utils.Metrics;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import test.test;
import test.test1;

public class Main extends JavaPlugin {//implements Listener {

    private static Main plugin;
    private CustomYmlManger customyml;
    private API api;

    //ARRAY LIST AND HASH MAPS
    ArrayList<String> Afk = afk.Afk;
    ArrayList<String> Fly = fly.Fly;
    HashMap<String, String> keyDataM = OnJoin.keyDataM;
    //END
    // GOT THE MYSQL API AT https://www.spigotmc.org/resources/simple-easy-mysql-api.36447/
    // GOT THE TITLE API AT https://www.spigotmc.org/resources/titleapi-1-8-1-13.1325/
    // FROM https://www.spigotmc.org/resources/api-pluginupdater-with-website.5578/
    PluginManager pm = Bukkit.getPluginManager();

    public void onEnable() {
        plugin = this;
        regCustomYmlConfigGEN();
        regFileConfigGEN();
        regAPIS();
        regEvents();
        regCommands();
        regbstats();
        getLogger().info("[World1-6Essentials] is now loaded!");
    }

    public void onDisable() {
        this.clear();
        getLogger().info("[World1-6Essentials] is now disabled.");
    }

    public void clear() {
        Afk.clear();
        Fly.clear();
        keyDataM.clear();
    }

    public void regCommands() {
        new gmc(this);
        new gms(this);
        new gmsp(this);
        new day(this);
        new night(this);
        new feed(this);
        new heal(this);
        new fly(this);
        new debug(customyml, this);
        new commandblock(this);
        new bed(this);
        new ram(this);
        new spawn(customyml, this);
        new echest(this);
        new sign(this);
        new Key(this); //KEY COMMAND
        new MutiKeys(this); //MKEY COMMAND
        new colors(this);
        new setjail(customyml, this);
        new setspawn(customyml, this);
        new jail(customyml, this);
        new afk(this);
        new flyspeed(customyml, this);
        new isafk(customyml, this);

        new test(customyml, this);
        new test1(customyml, this);
    }

    public void regEvents() {
        //Bukkit.getServer().getPluginManager().registerEvents(this, this);
        new OnJoin(this);
        new OnLeave(this);
        //...
        new OnBedEnter(this);
        new OnJoinTitle(this);
    }

    public void regFileConfigGEN() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();
    }

    public void regCustomYmlConfigGEN() {
        customyml = new CustomYmlManger();
        // Shit.yml
        customyml.setupshit();
        customyml.saveshit();
        customyml.reloadshit();
        // END OF Shit.yml
    }

    public void regAPIS() {
        api = new API();
    }

    public void regbstats() {
        Metrics metrics = new Metrics(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        {
            Player p = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("World1-6Essentials")) {
                if (args.length >= 0) {
                    p.sendMessage(Translate.chat("&6Made By Andrew121410 My -> Discord: Andrew121410#2035"));
                    return true;
                }
            }
        }
        return true;
    }

    public static Main getPlugin() {
        return plugin;
    }

    public API getApi() {
        return api;
    }
}