package World16.Events;

import CCUtils.Storage.ISQL;
import CCUtils.Storage.SQLite;
import World16.Main.Main;
import World16.Managers.HomeManager;
import World16.Managers.KeyManager;
import World16.Objects.KeyObject;
import World16.Objects.LocationObject;
import World16.Utils.API;
import World16.Utils.Translate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OnPlayerJoinEvent implements Listener {

    private Main plugin;

    //Maps
    private Map<String, KeyObject> keyDataM;
    private Map<UUID, LocationObject> backM;
    private Map<UUID, Map<String, Location>> homesMap;
    //...

    //Lists
    private List<Player> adminListPlayer;
    //...

    private ISQL isqlKeys;
    private ISQL isqlHomes;

    private API api;
    private KeyManager keyapi;
    private HomeManager homeManager;

    public OnPlayerJoinEvent(Main getPlugin) {
        this.plugin = getPlugin;

        this.keyDataM = this.plugin.getSetListMap().getKeyDataM();
        this.backM = this.plugin.getSetListMap().getBackM();
        this.homesMap = this.plugin.getSetListMap().getHomesMap();
        this.adminListPlayer = this.plugin.getSetListMap().getAdminListPlayer();

        this.api = new API(this.plugin);

        //ISQL
        this.isqlKeys = new SQLite(plugin.getDataFolder(), "keys");
        this.isqlHomes = new SQLite(this.plugin.getDataFolder(), "Homes");
        //...

        this.keyapi = new KeyManager(this.plugin, this.isqlKeys);
        this.homeManager = new HomeManager(this.plugin, this.isqlHomes);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        //JOIN MESSAGE STUFF
        event.setJoinMessage("");

        Bukkit.broadcastMessage(Translate.chat(API.PREFIX + " &6Welcome Back! " + p.getDisplayName()));
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.0f, 1.0f);
        version(p);
        //...

        if (!api.getMysql_HOST().equals("null")) {
            keyDataM.remove(p.getDisplayName()); //<-- just incase
            keyDataM.put(p.getDisplayName(), new KeyObject(p.getDisplayName(), 1, "null"));
            keyapi.getAllKeysISQL(p.getDisplayName(), isqlKeys);
        } else {
            plugin.getServer().getConsoleSender().sendMessage(Translate.chat(API.USELESS_TAG + " Please make sure too put in the isqlKeys details in the config.yml."));
        }

        //Just a safety thing put in place if Bukkit doesn't want to work correctly.
        if (backM.get(p.getUniqueId()) != null) {
            this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(API.EMERGENCY_TAG + " " + "&cMAP UNLOADER BACK ISN'T WORKING: CLASS: " + this.getClass()));
            backM.remove(p.getUniqueId());
            backM.put(p.getUniqueId(), new LocationObject());
        } else {
            backM.put(p.getUniqueId(), new LocationObject());
        }

        if (homesMap.get(p.getUniqueId()) != null) {
            this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(API.EMERGENCY_TAG + " " + "&cMAP UNLOADER HOMES ISN'T WORKING: CLASS: " + this.getClass()));
            homesMap.remove(p.getUniqueId());
            this.homeManager.getAllHomesFromISQL(this.isqlHomes, p);
        } else {
            this.homeManager.getAllHomesFromISQL(this.isqlHomes, p);
        }

        adminListPlayer.forEach((k) -> {
            p.hidePlayer(k);
            k.sendMessage(Translate.chat("[&9World1-6&r] &9Player: " + p.getDisplayName() + " &cnow cannot see you,"));
        });

        this.plugin.getDiscordBot().sendJoinMessage(p);
    }

    public void version(Player p) {
        p.sendMessage(Translate.chat("&4World1-6Ess Last Time Updated Was " + API.DATE_OF_VERSION));
    }
}