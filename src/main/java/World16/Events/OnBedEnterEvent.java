package World16.Events;

import World16.Main.Main;
import World16.Translate.Translate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class OnBedEnterEvent implements Listener {

    private static Main plugin;

    public OnBedEnterEvent(Main plugin) {
        OnBedEnterEvent.plugin = plugin;

        OnBedEnterEvent.plugin.getServer().getPluginManager().registerEvents(this, OnBedEnterEvent.plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerBedEnter(PlayerBedEnterEvent e) {

        Player p = e.getPlayer();
        // ANOTHER V

        p.getLocation().getWorld().setTime(1000);
        Bukkit.broadcastMessage(Translate.chat("[&9World1-6&r]&6 Waky Waky Eggs And Baky&r."));

        /**
         * //Log Command off ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
         * String command = "gamerule sendCommandFeedback false"; Bukkit.dispatchCommand(console,
         * command); //Make it day ConsoleCommandSender console1 =
         * Bukkit.getServer().getConsoleSender(); String command1 = "time set day";
         * Bukkit.dispatchCommand(console1, command1); //Message ConsoleCommandSender console2 =
         * Bukkit.getServer().getConsoleSender(); String command2 = "setblock 984 28 967
         * redstone_block"; Bukkit.dispatchCommand(console2, command2);
         *
         */
    }
}
