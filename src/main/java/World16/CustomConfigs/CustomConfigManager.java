package World16.CustomConfigs;

import World16.Utils.CustomYmlManager;

public class CustomConfigManager {

    private CustomYmlManager shitYml;
    private CustomYmlManager eRamYml;
    private CustomYmlManager jailsYml;

    public CustomConfigManager() {

    }

    public void registerAllCustomConfigs() {
        //Shit.yml
        this.shitYml = new CustomYmlManager();
        this.shitYml.setup("shit.yml");
        this.shitYml.saveConfig();
        this.shitYml.reloadConfig();
        //...

        //eram.yml
        this.eRamYml = new CustomYmlManager();
        this.eRamYml.setup("eram.yml");
        this.eRamYml.saveConfig();
        this.eRamYml.reloadConfig();
        //...

        //jails.yml
        this.jailsYml = new CustomYmlManager();
        this.jailsYml.setup("jails.yml");
        this.jailsYml.saveConfig();
        this.jailsYml.reloadConfig();
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
}
