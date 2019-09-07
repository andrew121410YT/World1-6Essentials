package World16.Events.PluginEvents;

import World16.Main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EasyBackupEvent implements Listener {

    private Main plugin;

    public EasyBackupEvent(Main plugin) {
        this.plugin = plugin;

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }


    @EventHandler
    public void onEasyBackup(me.forseth11.easybackup.api.EasyBackupEvent event) {
        this.plugin.getDiscordBot().sendEasyBackupEvent(event.getEvent());
    }
}
