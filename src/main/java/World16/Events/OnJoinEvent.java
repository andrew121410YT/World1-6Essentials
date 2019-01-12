package World16.Events;

import World16.Main.Main;
import World16.MysqlAPI.MySQL;
import World16.Translate.Translate;
import World16.Utils.API;
import World16.Utils.KeyAPI;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoinEvent implements Listener {

    private Main plugin;

    public static HashMap<String, String> keyDataM = new HashMap<String, String>();
    MySQL mysql = new MySQL();
    API api = new API();
    KeyAPI keyapi = new KeyAPI();

    public OnJoinEvent(Main getPlugin) {
        this.plugin = getPlugin;

        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        if (p.getDisplayName().equals("andrew121410")) {
            p.sendMessage(Translate.chat("&4Hello, Owner."));
        }
        if (p.getDisplayName().equals("AlphaGibbon43")) {
            p.sendMessage(Translate.chat("&4Hello, Owner."));
        }
        event.setJoinMessage("");

        Bukkit.broadcastMessage(Translate.chat(api.PREFIX+" &6Welcome Back! " + p.getDisplayName()));
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.0f, 1.0f);
        version(p);

        if (!api.getHOST().equals("null")) {
            String giveKeyReturnTEMP = keyapi.giveKeyReturn(p, mysql);

            if (giveKeyReturnTEMP == null) {
                this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat(
                    api.USELESS + " &9The Player: " + p.getDisplayName()
                        + " is not in the Database so gonna add them..."));
                keyapi.SetKey(mysql, 1, p, "null");
            } else {
                //GETS THE 1 KEY FROM THE PLAYER AND THEN IT STORES IT IN RAM FOR EASY ACCESS
                keyDataM.put(p.getDisplayName(), giveKeyReturnTEMP);
            }
        }
    }

    public void version(Player p) {
        p.sendMessage(Translate.chat("&4World1-6Ess Last Time Updated Was " + api.DATE_OF_VERSION));
    }
}