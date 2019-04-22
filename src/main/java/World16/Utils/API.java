package World16.Utils;

import World16.CustomConfigs.CustomConfigManager;
import World16.Main.Main;
import World16.Objects.KeyObject;
import World16.Objects.LocationObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * The Bass API for World1-6Ess
 *
 * @author Andrew121410
 */
public class API {

    // Maps
    Map<String, KeyObject> keyDataM = SetListMap.keyDataM;
    Map<String, LocationObject> backm = SetListMap.backM;
    Map<Player, Player> tpam = SetListMap.tpaM;
    Map<String, List<String>> tabCompleteMap = SetListMap.tabCompleteMap;
    Map<String, Map<String, List<Location>>> eRamManager = SetListMap.eRamRaw;
    Map<String, Location> latestClickedBlocked = SetListMap.latestClickedBlocked;
    Map<String, Map<String, String>> tagsMap = Tag.tagsMap;
    Map<Player, Location[]> sessions = SetListMap.sessions;
    //...

    // Lists
    List<String> Afk1 = SetListMap.afkList;
    List<String> Fly1 = SetListMap.flyList;
    List<String> GodM = SetListMap.godmList;
    List<String> adminList = SetListMap.adminList;
    List<Player> adminListPLayer = SetListMap.adminListPlayer;
    //...

    private static Main plugin = Main.getPlugin();
    private CustomYmlManager configinstance = null;

    private ViaAPI viaapi;

    //Finals
    public static final String CUSTOM_COMMAND_FORMAT = "`";
    public static final Integer VERSION = 255;
    public static final String DATE_OF_VERSION = "4/20/2019";
    public static final String PREFIX = "[&9World1-6Ess&r]";
    public static final String USELESS_TAG = "" + PREFIX + "->[&bUSELESS&r]";
    public static final String EMERGENCY_TAG = "" + PREFIX + "->&c[EMERGENCY]&r";
    public static final String TOO_DAMN_OLD = "Your mc version is too damn old 1.11 up too 1.13.2 please.";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong.";
    //...

    // FOR MYSQL
    private String mysql_HOST = plugin.getConfig().getString("MysqlHOST");
    private String mysql_DATABASE = plugin.getConfig().getString("MysqlDATABASE");
    private String mysql_USER = plugin.getConfig().getString("MysqlUSER");
    private String mysql_PASSWORD = plugin.getConfig().getString("MysqlPASSWORD");
    private String mysql_PORT = plugin.getConfig().getString("MysqlPORT");
    // END MYSQL

    // MAIN
    public API() {
        regPlugins();
    }

    @Deprecated
    public API(CustomYmlManager configInstance) {
        this.configinstance = configInstance;

        regPlugins();
    }

    public API(CustomConfigManager configManager) {
        regPlugins();
    }

    // END MAIN

    private void regPlugins() {
        regPluginViaVersion();
    }

    private void regPluginViaVersion() {
        if (isClass("us.myles.ViaVersion.api.ViaAPI")) {
            viaapi = Via.getAPI();
        }
    }
    // START OF MYSQL

    public String getMysql_HOST() {
        if (this.mysql_HOST != null) {
            return this.mysql_HOST;
        } else {
            return null;
        }
    }

    public String getMysql_DATABASE() {
        if (this.mysql_DATABASE != null) {
            return this.mysql_DATABASE;
        } else {
            return null;
        }
    }

    public String getMysql_USER() {
        if (this.mysql_USER != null) {
            return this.mysql_USER;
        } else {
            return null;
        }
    }

    @Deprecated
    public String getMysql_PASSWORD() {
        if (this.mysql_PASSWORD != null) {
            return this.mysql_PASSWORD;
        } else {
            return null;
        }
    }

    public String getMysql_PORT() {
        if (this.mysql_PORT != null) {
            return this.mysql_PORT;
        } else {
            return null;
        }
    }
    // END OF MYSQL

    public boolean isAfk(Player p) {
        return Afk1.contains(p.getDisplayName());
    }

    public boolean isFlying(Player p) {
        return Fly1.contains(p.getDisplayName()) || p.isFlying();
    }

    public boolean isGod(Player p) {
        return GodM.contains(p.getDisplayName());
    }

    public boolean isDebug() {
        return plugin.getConfig().getString("debug").equalsIgnoreCase("true");
    }

    public void clearListAndMaps(Player p) {
        clearAllMaps(p);
        clearAllLists(p);
    }

    public void clearListAndMaps() {
        clearAllMaps();
        clearAllLists();
    }

    public void clearAllMaps(Player p) {
        keyDataM.remove(p.getDisplayName());

        backm.remove(p.getDisplayName());

        tpam.remove(p);

        eRamManager.remove(p.getDisplayName());

        latestClickedBlocked.remove(p.getDisplayName());

        tagsMap.remove(p.getDisplayName());

        sessions.remove(p);

        if (this.isDebug()) {
            ClearHashMapMessage("World16.Events.OnJoinEvent.keyDataM", p);
            ClearHashMapMessage("World16.Commands.back.backm", p);
            ClearHashMapMessage("World16.Commands.tp.tpa.tpam", p);
            ClearHashMapMessage("World16.test.ERamManager.stringRawLocationObjectHashMap", p);
            ClearHashMapMessage("World16.Events.PlayerInteractEvent.latestClickedBlocked", p);
            ClearHashMapMessage("World16.Utils.Tag.tagsMap", p);
        }
    }

    public void clearAllMaps() {
        keyDataM.clear();

        backm.clear();

        tpam.clear();

        eRamManager.clear();

        latestClickedBlocked.clear();

        tagsMap.clear();

        sessions.clear();

        if (this.isDebug()) {
            ClearHashMapMessage("World16.Events.OnJoinEvent.keyDataM");
            ClearHashMapMessage("World16.Commands.back.backm");
            ClearHashMapMessage("World16.Commands.tp.tpa.tpam");
            ClearHashMapMessage("World16.test.ERamManager.stringRawLocationObjectHashMap");
            ClearHashMapMessage("World16.Events.PlayerInteractEvent.latestClickedBlocked");
            ClearHashMapMessage("World16.Utils.Tag.tagsMap");
        }
    }

    public void clearAllLists(Player p) {
        Afk1.remove(p.getDisplayName());

        Fly1.remove(p.getDisplayName());

        GodM.remove(p.getDisplayName());

        adminListPLayer.remove(p);

        if (this.isDebug()) {
            ClearArrayListMessage("World16.Commands.afk.Afk", p);
            ClearArrayListMessage("World16.Commands.fly.Fly", p);
            ClearArrayListMessage("World16.Commands.god.godm", p);
            ClearArrayListMessage("World16.Events.AsyncPlayerChatEvent.adminListPlayer", p);
        }
    }

    public void clearAllLists() {
        Afk1.clear();

        Fly1.clear();

        GodM.clear();

        adminListPLayer.clear();

        if (this.isDebug()) {
            ClearArrayListMessage("World16.Commands.afk.Afk");
            ClearArrayListMessage("World16.Commands.fly.Fly");
            ClearArrayListMessage("World16.Commands.god.godm");
            ClearArrayListMessage("World16.Events.AsyncPlayerChatEvent.adminListPlayer");
        }
    }

    public String FormatTime(LocalDateTime time) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        return time.format(myFormatObj);
    }

    public String Time() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        return time.format(myFormatObj);
    }

    public String getServerVersion() {
        String version = plugin.getServer().getVersion();
        if (version.contains("1.13") || version.contains("1.13.1") || version.contains("1.13.2")) {
            return "1.13";
        }
        if (version.contains("1.12") || version.contains("1.12.1") || version.contains("1.12.2")) {
            return "1.12";
        }
        if (version.contains("1.11") || version.contains("1.11.1") || version.contains("1.11.2")) {
            return "1.11";
        }
        return TOO_DAMN_OLD;
    }

    public String getTheSumPlayerVersion(Player p) {
        if (viaapi == null) {
            return SOMETHING_WENT_WRONG;
        }
        switch (viaapi.getPlayerVersion(p)) {
            case 404:
            case 401:
            case 393:
                return "1.13";
            case 340:
            case 338:
            case 335:
                return "1.12";
            case 316:
            case 315:
                return "1.11";
            case 210:
                return "1.10";
            case 110:
            case 109:
            case 108:
            case 107:
            case 106:
            case 105:
            case 104:
            case 103:
                return "1.9";
            case 47:
                return "1.8";
            default:
                return TOO_DAMN_OLD;
        }
    }

    public String getPlayerVersion(Player p) {
        switch (viaapi.getPlayerVersion(p)) {
            case 404:
                return "1.13.2";
            case 401:
                return "1.13.1";
            case 393:
                return "1.13";
            case 340:
                return "1.12.2";
            case 338:
                return "1.12.1";
            case 335:
                return "1.12";
            case 316:
                return "1.11.1 & 1.11.2";
            case 315:
                return "1.11";
            case 270:
                return "1.10 & 1.10.1 1.10.2";
            case 110:
                return "1.9.3 & 1.9.4";
            case 109:
                return "1.9.2";
            case 108:
                return "1.9.1";
            case 107:
                return "1.9";
            default:
                return TOO_DAMN_OLD;
        }
    }

    public int getPlayerProtocolVersion(Player p) {
        return viaapi.getPlayerVersion(p);
    }

    public String getUUIDFromMojangAPI(String playername) throws IOException, ParseException {
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playername);
        String uuid = (String) ((JSONObject) new JSONParser()
                .parse(new InputStreamReader(url.openStream()))).get("id");
        return uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-"
                + uuid.substring(16, 20) + "-" + uuid.substring(20, 32);
    }

    public Location getLocationFromFile(CustomYmlManager configinstance, String path) {
        if (configinstance == null || path == null) {
            return null;
        }

        return (Location) configinstance.getConfig().get(path);
    }

    public void setLocationToFile(CustomYmlManager configinstance, String path, Location location) {
        if (configinstance == null || path == null || location == null) {
            return;
        }

        configinstance.getConfig().set(path, location);
        configinstance.saveConfigSilent();
    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Integer asIntOrDefault(String input, int default1) {
        try {
            Integer.parseInt(input);
            return Integer.valueOf(input);
        } catch (Exception e) {
            return default1;
        }
    }

    public Long asLongOrDefault(String input, long default1) {
        try {
            Long.parseLong(input);
            return Long.valueOf(input);
        } catch (Exception e) {
            return default1;
        }
    }

    public boolean isClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public boolean asBooleanOrDefault(String boolean1, boolean default1) {
        try {
            Boolean.parseBoolean(boolean1);
            return Boolean.valueOf(boolean1);
        } catch (Exception e) {
            return default1;
        }
    }

    public void PermissionErrorMessage(Player p) {
        p.sendMessage(
                Translate.chat(PREFIX + " &cYou Do Not Have Permission To Use This Command."));
    }

    public void ClearHashMapMessage(String place) {
        plugin.getServer().getConsoleSender().sendMessage(Translate.chat(USELESS_TAG
                + " Class: World16.Utils.API has cleared the HashMap of " + place + " For EVERY PLAYER"));
    }

    public void ClearHashMapMessage(String place, Player p) {
        plugin.getServer().getConsoleSender().sendMessage(Translate.chat(USELESS_TAG
                + " Class: World16.Utils.API has cleared the HashMap of " + place + " For Player: " + p
                .getDisplayName()));
    }

    public void ClearArrayListMessage(String place) {
        plugin.getServer().getConsoleSender().sendMessage(Translate.chat(USELESS_TAG
                + " Class: World16.Utils.API has cleared the ArrayList of " + place + " For EVERY PLAYER"));
    }

    public void ClearArrayListMessage(String place, Player p) {
        plugin.getServer().getConsoleSender().sendMessage(Translate.chat(USELESS_TAG
                + " Class: World16.Utils.API has cleared the ArrayList of " + place + " For Player: " + p
                .getDisplayName()));
    }
}
