package Utils;

import Translate.Translate;
import World16.World16.World16.Main;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CustomYmlManger {

    private Main plugin = Main.getPlugin();

    API api = new API();
    // Files & File Configs Here.
    public FileConfiguration shitcfg;
    public File shitfile;

    // --------------------------------------------------------------------------------------------------------
    public void setupshit() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        shitfile = new File(plugin.getDataFolder(), "shit.yml");

        if (!shitfile.exists()) {
            try {
                shitfile.createNewFile();
                Bukkit.getServer().getConsoleSender()
                    .sendMessage(Translate.chat(api.USELESS + " The shit.yml has been created."));
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender()
                    .sendMessage(Translate
                        .chat(api.USELESS + " The shit.yml could not make for some reason."));
            }
        }

        shitcfg = YamlConfiguration.loadConfiguration(shitfile);
    }

    public FileConfiguration getshit() {
        return shitcfg;
    }

    public void saveshit() {
        try {
            shitcfg.save(shitfile);
            Bukkit.getServer().getConsoleSender()
                .sendMessage(Translate.chat(api.USELESS + " &aThe shit.yml has been saved."));
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender()
                .sendMessage(Translate.chat(api.USELESS + " &cThe shit.yml has been NOT SAVED.."));
        }
    }

    public void reloadshit() {
        shitcfg = YamlConfiguration.loadConfiguration(shitfile);
        Bukkit.getServer().getConsoleSender()
            .sendMessage(Translate.chat(api.USELESS + " &6The shit.yml has been reloaded."));
//        // END OF SHIT YML
//        // ****************************************************************************************************
    }

    private String spawnname;
    private String spawnname1;
    //API FOR SPAWN
    public Location apiGetSpawn(String spawnname) {
        this.spawnname = spawnname.toLowerCase();
        double x = this.getshit().getInt("Spawn." + this.spawnname + ".Data.X");
        double y = this.getshit().getInt("Spawn." + this.spawnname + ".Data.Y");
        double z = this.getshit().getInt("Spawn." + this.spawnname + ".Data.Z");
        float yaw = (float) this.getshit().getInt("Spawn." + this.spawnname + ".Data.Yaw");
        float pitch = (float) this.getshit().getInt("Spawn." + this.spawnname + ".Data.Pitch");
        World world = Bukkit
            .getWorld(this.getshit().getString("Spawn." + this.spawnname + ".Data.World"));

        Location spawn = new Location(world, x, y, z, yaw, pitch);
        return spawn;
    }

    public void apiSetSpawn(Player p, double x, double y, double z, double yaw, double pitch,
        String worldname, String spawnname) {
        this.spawnname1 = spawnname.toLowerCase();
        this.getshit().set("Spawn." + this.spawnname1 + ".Data.X", x);
        this.getshit().set("Spawn." + this.spawnname1 + ".Data.Y", y);
        this.getshit().set("Spawn." + this.spawnname1 + ".Data.Z", z);
        this.getshit().set("Spawn." + this.spawnname1 + ".Data.Yaw", yaw);
        this.getshit().set("Spawn." + this.spawnname1 + ".Data.Pitch", pitch);
        this.getshit().set("Spawn." + this.spawnname1 + ".Data.World", worldname);
        this.getshit().set("Spawn." + this.spawnname1 + ".Player.Data.NAME", p.getDisplayName());
        this.getshit()
            .set("Spawn." + this.spawnname1 + ".Player.Data.UUID", p.getUniqueId().toString());
        this.saveshit();
    }
}
