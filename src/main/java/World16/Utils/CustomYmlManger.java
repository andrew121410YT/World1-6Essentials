package World16.Utils;

import World16.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomYmlManger {

    private Main plugin = Main.getPlugin();

    // Files & File Configs Here.
    private FileConfiguration configcfg;
    private File configfile;

    //Strings
    private String nameoffile;
    //...

    // --------------------------------------------------------------------------------------------------------
    public void setup(String nameoffile) {
        this.nameoffile = nameoffile;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        configfile = new File(plugin.getDataFolder(), this.nameoffile);

        if (!configfile.exists()) {
            try {
                configfile.createNewFile();
                Bukkit.getServer().getConsoleSender()
                        .sendMessage(Translate.chat(API.USELESS + " The {nameoffile} has been created.").replace("{nameoffile}", this.nameoffile));
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender()
                        .sendMessage(Translate
                                .chat(API.USELESS + " The {nameoffile} could not make for some reason.").replace("{nameoffile}", this.nameoffile));
            }
        }

        configcfg = YamlConfiguration.loadConfiguration(configfile);
    }

    public FileConfiguration getConfig() {
        return configcfg;
    }

    public void saveConfig() {
        try {
            configcfg.save(configfile);
            Bukkit.getServer().getConsoleSender()
                    .sendMessage(Translate.chat(API.USELESS + " &aThe {nameoffile} has been saved.").replace("{nameoffile}", this.nameoffile));
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender()
                    .sendMessage(Translate.chat(API.USELESS + " &cThe {nameoffile} has been NOT SAVED..").replace("{nameoffile}", this.nameoffile));
        }
    }

    public void saveConfigSilent() {
        try {
            configcfg.save(configfile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender()
                    .sendMessage(Translate.chat(API.USELESS + " &cThe {nameoffile} has been NOT SAVED..").replace("{nameoffile}", this.nameoffile));
        }
    }

    public void reloadConfig() {
        configcfg = YamlConfiguration.loadConfiguration(configfile);
        Bukkit.getServer().getConsoleSender()
                .sendMessage(Translate.chat(API.USELESS + " &6The {nameoffile} has been reloaded.").replace("{nameoffile}", this.nameoffile));
    }
}
