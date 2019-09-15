package World16.Managers;

import World16.Main.Main;

public class CustomConfigManager {

    private Main plugin;

    private CustomYmlManager shitYml;
    private CustomYmlManager eRamYml;
    private CustomYmlManager jailsYml;
    private CustomYmlManager elevatorsYml;

    public CustomConfigManager(Main plugin) {
        this.plugin = plugin;
    }

    public void registerAllCustomConfigs() {
        //Shit.yml
        this.shitYml = new CustomYmlManager(this.plugin);
        this.shitYml.setup("shit.yml");
        this.shitYml.saveConfig();
        this.shitYml.reloadConfig();
        //...

        //eram.yml
        this.eRamYml = new CustomYmlManager(this.plugin);
        this.eRamYml.setup("eram.yml");
        this.eRamYml.saveConfig();
        this.eRamYml.reloadConfig();
        //...

        //jails.yml
        this.jailsYml = new CustomYmlManager(this.plugin);
        this.jailsYml.setup("jails.yml");
        this.jailsYml.saveConfig();
        this.jailsYml.reloadConfig();
        //...

        //elevators.yml
        this.elevatorsYml = new CustomYmlManager(this.plugin);
        this.elevatorsYml.setup("elevators.yml");
        this.elevatorsYml.saveConfig();
        this.elevatorsYml.reloadConfig();
        //...

    }

    public CustomYmlManager getShitYml() {
        return shitYml;
    }

    public CustomYmlManager getERamYML() {
        return eRamYml;
    }

    public CustomYmlManager getJailsYml() {
        return jailsYml;
    }

    public CustomYmlManager getElevatorsYml() {
        return elevatorsYml;
    }
}
