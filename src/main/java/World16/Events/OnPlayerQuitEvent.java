package World16.Events;

import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.SetListMap;
import World16.Utils.Translate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuitEvent implements Listener {

    private Main plugin;
    private API api;
    private SetListMap setListMap;

    public OnPlayerQuitEvent(Main getPlugin) {
        this.plugin = getPlugin;
        this.api = new API(this.plugin);

        this.setListMap = this.plugin.getSetListMap();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQUIT(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        //CLEAR Set's and List's and Map's
        setListMap.clearSetListMap(p);

        event.setQuitMessage("");
        Bukkit.broadcastMessage(Translate.chat(API.PREFIX + " &5Bye Bye, " + p.getDisplayName()));

        this.plugin.getDiscordBot().sendLeaveMessage(p);
    }
}
