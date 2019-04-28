package World16.Utils;

import World16.Objects.KeyObject;
import World16.Objects.LocationObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class SetListMap {

    public static Map<String, KeyObject> keyDataM = new HashMap<>();
    public static Map<String, LocationObject> backM = new HashMap<>();
    public static Map<Player, Player> tpaM = new LinkedHashMap<>();
    public static Map<String, List<String>> tabCompleteMap = new HashMap<>();
    public static Map<String, Map<String, List<Location>>> eRamRaw = new HashMap<>();
    public static Map<String, Location> latestClickedBlocked = new HashMap<>();

    public static List<String> afkList = new ArrayList<>();
    public static List<String> flyList = new ArrayList<>();
    public static List<String> godmList = new ArrayList<>();
    public static List<String> adminList = new ArrayList<>();
    public static List<Player> adminListPlayer = new ArrayList<>();

    public static Map<Player, Location[]> sessions = new HashMap<>();
    public static Map<String, UUID> uuidCache = new HashMap<>();
}
