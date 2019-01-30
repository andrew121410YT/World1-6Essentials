package World16.Events;

import World16.Main.Main;
import World16.Translate.Translate;
import World16.titleapi.TitleAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoinTitleEvent implements Listener {

    private static Main plugin;

    public OnJoinTitleEvent(Main plugin) {
        OnJoinTitleEvent.plugin = plugin;

        OnJoinTitleEvent.plugin.getServer().getPluginManager().registerEvents(this, OnJoinTitleEvent.plugin);
    }

    FileConfiguration file = Main.getPlugin().getConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        TitleAPI.sendTitle(e.getPlayer(), 10, 5 * 20, 10, Translate.chat(file.getString("TittleTOP")),
                Translate.chat(file.getString("TittleBOTTOM")));
        TitleAPI.sendTabTitle(e.getPlayer(), Translate.chat(file.getString("TablistTOP")),
                Translate.chat(file.getString("TablistBOTTOM")));
    }
}
