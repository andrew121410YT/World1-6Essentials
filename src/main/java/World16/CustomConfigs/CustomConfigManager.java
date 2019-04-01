package World16.CustomConfigs;

import World16.Utils.CustomYmlManager;

public class CustomConfigManager {

    private CustomYmlManager shitYml;
    private CustomYmlManager eRamYml;

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

    }

    public CustomYmlManager getShitYml() {
        return shitYml;
    }

    public CustomYmlManager getERamYML() {
        return eRamYml;
    }
}
