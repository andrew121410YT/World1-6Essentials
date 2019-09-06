package World16DiscordBot;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import World16.Utils.API;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import org.bukkit.entity.Player;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    private Main plugin;
    private CustomConfigManager customConfigManager;

    private JDA jda;

    public DiscordBot(Main plugin, CustomConfigManager customConfigManager) {
        this.plugin = plugin;
        this.customConfigManager = customConfigManager;
        setup();
    }

    public void setup() {
        String token = this.plugin.getConfig().getString("discord-token");

        if (token == null || token.equalsIgnoreCase("")) {
            this.plugin.getServer().broadcastMessage(API.EMERGENCY_TAG + " DISCORD BOT FUCKED UP");
            return;
        }

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .setEventManager(new AnnotatedEventManager())
                    .addEventListeners(this)
                    .build()
                    .awaitReady();
        } catch (InterruptedException | LoginException e) {
            e.printStackTrace();
        }

        jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.of(Activity.ActivityType.DEFAULT, "World1-6"));
    }

    private Guild getWorld16DiscordServer() {
        return this.jda.getGuildById("536266396884271114");
    }

    public void sendJoinMessage(Player player) {
        Guild guild = getWorld16DiscordServer();

        TextChannel textChannel = guild.getTextChannels().stream().filter(textChannel1 -> textChannel1.getName().equalsIgnoreCase("minecraft-joins")).findFirst().orElse(null);

        if (textChannel == null) return;

        textChannel.sendMessage(player.getDisplayName() + " has JOINED the server!").queue();
    }

    public void sendLeaveMessage(Player player) {
        Guild guild = getWorld16DiscordServer();

        TextChannel textChannel = guild.getTextChannels().stream().filter(textChannel1 -> textChannel1.getName().equalsIgnoreCase("minecraft-leaves")).findFirst().orElse(null);

        if (textChannel == null) return;

        textChannel.sendMessage(player.getDisplayName() + " has LEFT the server!").queue();
    }
}
