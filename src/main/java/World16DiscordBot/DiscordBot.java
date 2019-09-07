package World16DiscordBot;

import World16.Main.Main;
import World16.Managers.CustomConfigManager;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DiscordBot {

    private Main plugin;
    private CustomConfigManager customConfigManager;

    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;

    public DiscordBot(Main plugin, CustomConfigManager customConfigManager) {
        this.plugin = plugin;
        this.customConfigManager = customConfigManager;
        setup();
    }

    public void setup() {
        try {
            socket = new Socket("192.168.1.26", 2020);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendJoinMessage(Player player) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "PlayerJoin");
        jsonObject.put("Player", player.getDisplayName());
        out.println(jsonObject.toJSONString());
    }

    public void sendLeaveMessage(Player player) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "PlayerQuit");
        jsonObject.put("Player", player.getDisplayName());
        out.println(jsonObject.toJSONString());
    }

    public void sendEasyBackupEvent(me.forseth11.easybackup.api.Event event) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "EasyBackup");
        jsonObject.put("EasyBackupTYPE", event.getType().name());
        jsonObject.put("Message", event.getMessage());
        jsonObject.put("Time", event.getTime());
        out.println(jsonObject.toJSONString());
    }
}
