package World16.Events;

import CCUtils.Storage.ISQL;
import CCUtils.Storage.SQLite;
import World16.Main.Main;
import World16.Objects.KeyObject;
import World16.Objects.LocationObject;
import World16.Utils.API;
import World16.Utils.KeyAPI;
import World16.Utils.SetListMap;
import World16.Utils.Translate;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OnJoinEvent implements Listener {

    private static Main plugin;

    //Maps
    Map<String, KeyObject> keyDataM = SetListMap.keyDataM;
    Map<UUID, LocationObject> backM = SetListMap.backM;
    //...

    //Lists
    List<Player> adminListPlayer = SetListMap.adminListPlayer;
    //...

    private ISQL mysql;
    private API api = new API();
    private KeyAPI keyapi;

    public OnJoinEvent(Main getPlugin) {
        plugin = getPlugin;
        this.mysql = new SQLite(plugin.getDataFolder(), "keys");
        this.keyapi = new KeyAPI(this.mysql);
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
            keyapi.getAllKeysFromMysqlTooRam(p.getDisplayName(), mysql);
        } else {
            plugin.getServer().getConsoleSender()
                    .sendMessage(Translate.chat(API.USELESS_TAG
                            + " Please make sure too put in the mysql details in the config.yml."));
        }

        backM.remove(p.getUniqueId()); //-> Just In Case.
        backM.put(p.getUniqueId(), new LocationObject());

        adminListPlayer.forEach((k) -> {
            p.hidePlayer(k);
            k.sendMessage(Translate.chat("[&9World1-6&r] &9Player: " + p.getDisplayName() + " &cnow cannot see you,"));
        });
    }

    public void version(Player p) {
        p.sendMessage(Translate.chat("&4World1-6Ess Last Time Updated Was " + API.DATE_OF_VERSION));
    }
}