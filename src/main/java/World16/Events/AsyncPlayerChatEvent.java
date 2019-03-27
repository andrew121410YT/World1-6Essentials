package World16.Events;

import World16.Main.Main;
import World16.Utils.API;
import World16.Utils.Translate;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsyncPlayerChatEvent implements Listener {

    private Main plugin;
    private API api;

    //Lists
    public static List<String> adminList = new ArrayList<>();
    //...

    //Maps
    public static List<Player> adminListPlayer = new ArrayList<>();
    //...

    public AsyncPlayerChatEvent(Main getPlugin) {
        this.plugin = getPlugin;

        this.api = new API();

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        adminList.add("andrew121410");
        adminList.add("Andrzej_Przybyla");
    }

    @EventHandler
    public void ChatEvent(org.bukkit.event.player.AsyncPlayerChatEvent event) {

        Player p = event.getPlayer();

        String cmd = event.getMessage();

        //if msg contains username from anyone on the server then ping them.
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    if (cmd.contains(player.getDisplayName())) {
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.0f, 1.0f);
                    }
                }
            }
        }.runTask(this.plugin);


        if (!cmd.startsWith(":")) {
            return;
        }

        String[] args = cmd.split(" ");
        if (args == null) {
            return;
        }

        if (!adminList.contains(p.getDisplayName())) {
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
                    String messageFrom = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
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
        if (args[0].equalsIgnoreCase(":oktp")) {
            event.setCancelled(true);
            if (!p.hasPermission("world16.oktp")) {
                api.PermissionErrorMessage(p);
                return;
            }
            if (args.length == 1) {
                p.sendMessage(Translate.chat("&cUsage: :oktp <Player>"));
            } else if (args.length == 2) {
                Player pTarget = this.plugin.getServer().getPlayerExact(args[1]);
                if (args[1] != null && pTarget != null && pTarget.isOnline()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            adminListPlayer.add(p);
                            plugin.getServer().getOnlinePlayers().forEach(player -> player.hidePlayer(p));
                            if (!pTarget.canSee(p)) {
                                p.teleport(pTarget.getLocation());
                                p.sendMessage(Translate.chat("&bOk..."));
                                p.sendMessage(Translate.chat("Too unhide use :okunhide"));
                            }
                        }
                    }.runTask(this.plugin);
                }
            }
        }
        if (args[0].equalsIgnoreCase(":okunhide")) {
            event.setCancelled(true);
            if (!p.hasPermission("world16.okunhide")) {
                api.PermissionErrorMessage(p);
                return;
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    adminListPlayer.remove(p);
                    plugin.getServer().getOnlinePlayers().forEach(player -> player.showPlayer(p));
                    p.sendMessage(Translate.chat("&bOk..."));
                }
            }.runTask(this.plugin);
        }
        if (args[0].equalsIgnoreCase(":okhide")) {
            event.setCancelled(true);
            if (!p.hasPermission("world16.okhide")) {
                api.PermissionErrorMessage(p);
                return;
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    adminListPlayer.add(p);
                    plugin.getServer().getOnlinePlayers().forEach(player -> player.hidePlayer(p));
                    p.sendMessage(Translate.chat("&bOk..."));
                }
            }.runTask(this.plugin);
        }

        if (args[0].equalsIgnoreCase(":oklist")) {
            event.setCancelled(true);
            for (Player player : adminListPlayer) {
                p.sendMessage("This player is hidden -> " + player.getDisplayName());
            }
        }
    }
}