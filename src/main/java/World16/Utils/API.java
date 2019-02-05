package World16.Utils;

import World16.Commands.afk;
import World16.Commands.back;
import World16.Commands.fly;
import World16.Commands.god;
import World16.Commands.tp.tpa;
import World16.CustomExceptions.CustomYmlManagerInstanceException;
import World16.Events.OnJoinEvent;
import World16.Main.Main;
import World16.Objects.KeyObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class API {

    // Maps
    HashMap<String, KeyObject> keyDataM = OnJoinEvent.keyDataM;
    LinkedHashMap<String, Location> backm = back.backm;
    LinkedHashMap<Player, Player> tpam = tpa.tpam;
    //...

    // Lists
    ArrayList<String> Afk1 = afk.Afk;
    ArrayList<String> Fly1 = fly.Fly;
    ArrayList<String> GodM = god.godm;
    //...

    private static Main plugin = Main.getPlugin();
    private CustomYmlManger configinstance = null;

    ViaAPI viaapi = Via.getAPI(); // https://docs.viaversion.com/display/VIAVERSION/Basic+API+usage

    //Finals
    public static final Integer VERSION = 6;
    public static final String DATE_OF_VERSION = "2/2/2019";
    public static final String PREFIX = "[&9World1-6Ess&r]";
    public static final String USELESS = "" + PREFIX + "->[&bUSELESS&r]";
    public static final String TOO_DAMN_OLD = "Your mc version is too damn old 1.11 up too 1.13.2 please.";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong.";
    //...

    // FOR MYSQL
    private String HOST = plugin.getConfig().getString("MysqlHOST");
    private String DATABASE = plugin.getConfig().getString("MysqlDATABASE");
    private String USER = plugin.getConfig().getString("MysqlUSER");
    private String PASSWORD = plugin.getConfig().getString("MysqlPASSWORD");
    private String PORT = plugin.getConfig().getString("MysqlPORT");
    // END MYSQL

    // MAIN
    public API() {
    }

    public API(CustomYmlManger configinstance) {
        this.configinstance = configinstance;
    }

    // END MAIN
    // START OF MYSQL

    public String getHOST() {
        if (this.HOST != null) {
            return this.HOST;
        } else {
            return null;
        }
    }

    public String getDATABASE() {
        if (this.DATABASE != null) {
            return this.DATABASE;
        } else {
            return null;
        }
    }

    public String getUSER() {
        if (this.USER != null) {
            return this.USER;
        } else {
            return null;
        }
    }

    public String getPASSWORD() {
        if (this.PASSWORD != null) {
            return this.PASSWORD;
        } else {
            return null;
        }
    }

    public String getPORT() {
        if (this.PORT != null) {
            return this.PORT;
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

    public ArrayList<String> getAfkArrayList() {
        return afk.Afk;
    }

    public ArrayList<String> getFlyArrayList() {
        return fly.Fly;
    }

    public ArrayList<String> getGodArrayList() {
        return god.godm;
    }

    public void clearArrayListAndHashMaps(Player p) {
        clearAllHashMaps(p);
        clearAllArrayList(p);
    }

    public void clearArrayListAndHashMaps() {
        clearAllHashMaps();
        clearAllArrayList();
    }

    public void clearAllHashMaps(Player p) {
        keyDataM.remove(p.getDisplayName());

        backm.remove(p.getDisplayName());
        backm.remove(p.getDisplayName() + "death");
        backm.remove(p.getDisplayName() + "tp");
        backm.remove(p.getDisplayName() + "set");

        tpam.remove(p);

        if (this.isDebug()) {
            ClearHashMapMessage("World16.Events.OnJoinEvent.keyDataM", p);
            ClearHashMapMessage("World16.Commands.back.backm", p);
            ClearHashMapMessage("World16.Commands.tp.tpa.tpam", p);
        }
    }

    public void clearAllHashMaps() {
        keyDataM.clear();

        backm.clear();

        tpam.clear();

        if (this.isDebug()) {
            ClearHashMapMessage("World16.Events.OnJoinEvent.keyDataM");
            ClearHashMapMessage("World16.Commands.back.backm");
            ClearHashMapMessage("World16.Commands.tp.tpa.tpam");
        }
    }

    public void clearAllArrayList(Player p) {
        Afk1.remove(p.getDisplayName());

        Fly1.remove(p.getDisplayName());

        GodM.remove(p.getDisplayName());

        if (this.isDebug()) {
            ClearArrayListMessage("World16.Commands.afk.Afk", p);
            ClearArrayListMessage("World16.Commands.fly.Fly", p);
            ClearArrayListMessage("World16.Commands.god.godm", p);
        }
    }

    public void clearAllArrayList() {
        Afk1.clear();

        Fly1.clear();

        GodM.clear();

        if (this.isDebug()) {
            ClearArrayListMessage("World16.Commands.afk.Afk");
            ClearArrayListMessage("World16.Commands.fly.Fly");
            ClearArrayListMessage("World16.Commands.god.godm");
        }
    }

    public String FormatTime(LocalDateTime time) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String formattedDate = time.format(myFormatObj);

        return formattedDate;
    }

    public String Time() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String formattedDate = time.format(myFormatObj);

        return formattedDate;
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
        String realUUID =
                uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-"
                        + uuid.substring(16, 20) + "-" + uuid.substring(20, 32);
        return realUUID;
    }

    private String locationname;
    private String Path;
    private CustomYmlManger configinstance2;

    private String locationname2;
    private String Path2;
    private CustomYmlManger configinstance3;

    public Location getLocationFromFile(CustomYmlManger configinstance, String Path, String nameoflocation) {
        this.configinstance2 = configinstance;
        this.Path = Path;
        this.locationname = nameoflocation.toLowerCase();

        if (configinstance == null && this.configinstance != null) {
            this.configinstance2 = this.configinstance;
        }
        if (this.configinstance2 != null) {
            double x = this.configinstance2.getConfig().getInt(this.Path + "." + this.locationname + ".Data.X");
            double y = this.configinstance2.getConfig().getInt(this.Path + "." + this.locationname + ".Data.Y");
            double z = this.configinstance2.getConfig().getInt(this.Path + "." + this.locationname + ".Data.Z");
            float yaw = Float.parseFloat(this.configinstance2.getConfig().getString(this.Path + "." + this.locationname + ".Data.Yaw"));
            float pitch = Float.parseFloat(this.configinstance2.getConfig()
                    .getString(this.Path + "." + this.locationname + ".Data.Pitch"));
            World world = Bukkit
                    .getWorld(
                            this.configinstance2.getConfig().getString(this.Path + "." + this.locationname + ".Data.World"));

            Location location = new Location(world, x, y, z, yaw, pitch);
            return location;
        } else {
            try {
                throw new CustomYmlManagerInstanceException("In World16.Utils.API this.configinstance2 == null");
            } catch (CustomYmlManagerInstanceException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setLocationToFile(CustomYmlManger configinstance, String path, String nameoflocation, Player p, double x, double y, double z, double yaw, double pitch,
                                  String worldname) {
        this.locationname2 = nameoflocation.toLowerCase();
        this.Path2 = path;
        this.configinstance3 = configinstance;

        if (configinstance == null && this.configinstance != null) {
            this.configinstance3 = this.configinstance;
        }

        if (this.configinstance3 != null) {
            this.configinstance3.getConfig().set(this.Path2 + "." + this.locationname2 + ".Data.X", x);
            this.configinstance3.getConfig().set(this.Path2 + "." + this.locationname2 + ".Data.Y", y);
            this.configinstance3.getConfig().set(this.Path2 + "." + this.locationname2 + ".Data.Z", z);
            this.configinstance3.getConfig().set(this.Path2 + "." + this.locationname2 + ".Data.Yaw", yaw);
            this.configinstance3.getConfig().set(this.Path2 + "." + this.locationname2 + ".Data.Pitch", pitch);
            this.configinstance3.getConfig().set(this.Path2 + "." + this.locationname2 + ".Data.World", worldname);
            this.configinstance3.getConfig()
                    .set(this.Path2 + "." + this.locationname2 + ".Player.Data.NAME", p.getDisplayName());
            this.configinstance3.getConfig()
                    .set(this.Path2 + "." + this.locationname2 + ".Player.Data.UUID", p.getUniqueId().toString());
            this.configinstance3.saveConfig();
        } else {
            try {
                throw new CustomYmlManagerInstanceException("In World16.Utils.API this.configinstance3 == null");
            } catch (CustomYmlManagerInstanceException e) {
                e.printStackTrace();
            }
        }
    }

    public void PermissionErrorMessage(Player p) {
        p.sendMessage(
                Translate.chat(PREFIX + " &cYou Do Not Have Permission To Use This Command."));
    }

    public void ClearHashMapMessage(String place) {
        plugin.getServer().getConsoleSender().sendMessage(Translate.chat(USELESS
                + " Class: World16.Utils.API has cleared the HashMap of " + place + " For EVERY PLAYER"));
    }

    public void ClearHashMapMessage(String place, Player p) {
        plugin.getServer().getConsoleSender().sendMessage(Translate.chat(USELESS
                + " Class: World16.Utils.API has cleared the HashMap of " + place + " For Player: " + p
                .getDisplayName()));
    }

    public void ClearArrayListMessage(String place) {
        plugin.getServer().getConsoleSender().sendMessage(Translate.chat(USELESS
                + " Class: World16.Utils.API has cleared the ArrayList of " + place + " For EVERY PLAYER"));
    }

    public void ClearArrayListMessage(String place, Player p) {
        plugin.getServer().getConsoleSender().sendMessage(Translate.chat(USELESS
                + " Class: World16.Utils.API has cleared the ArrayList of " + place + " For Player: " + p
                .getDisplayName()));
    }
}
