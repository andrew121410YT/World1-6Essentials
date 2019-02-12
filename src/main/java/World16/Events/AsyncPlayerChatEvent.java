package World16.Events;

import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.Translate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncPlayerChatEvent implements Listener {

    private Main plugin;
    private API api;

    public AsyncPlayerChatEvent(Main getPlugin) {
        this.plugin = getPlugin;

        this.api = new API();

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void ChatEvent(org.bukkit.event.player.AsyncPlayerChatEvent event) {

        Player p = event.getPlayer();

        String cmd = event.getMessage();

        if (!cmd.startsWith(":")) {
            return;
        }

        String[] args = cmd.split(" ");
        if (args == null) {
            return;
        }

        if (args[0].equalsIgnoreCase(":msg") || args[0].equalsIgnoreCase(":emsg")) {
            event.setCancelled(true);
            if (!p.hasPermission("world16.msg")) {
                api.PermissionErrorMessage(p);
                return;
            }
            if (args.length == 1) {
                p.sendMessage(Translate.chat("&cUsage: :msg <Player> <Message>"));
            } else if (args.length >= 3) {
                Player ptarget = this.plugin.getServer().getPlayerExact(args[1]);
                if (args[1] != null && args[2] != null && ptarget != null && ptarget.isOnline()) {
                    String messageFrom = args[2];
                    p.sendMessage(Translate.chat("&2[&a{me} &6->&c {target}&2]&9 ->&r {message}").replace("{me}", "me").replace("{target}", ptarget.getDisplayName()).replace("{message}", messageFrom));
                    ptarget.sendMessage(Translate.chat("&2[&a{me} &6->&c {target}&2]&9 ->&r {message}").replace("{me}", p.getDisplayName()).replace("{target}", "me").replace("{message}", messageFrom));
                } else {
                    p.sendMessage(Translate.chat("&4Something went wrong."));
                    p.sendMessage(Translate.chat("-> &cUsage: :msg <Player> <Message>"));
                }
            } else {
                p.sendMessage(Translate.chat("&4Something went wrong."));
                p.sendMessage(Translate.chat("-> &cUsage: :msg <Player> <Message>"));
            }
        }
    }
}