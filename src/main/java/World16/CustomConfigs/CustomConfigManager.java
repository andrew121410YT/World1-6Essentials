package World16.CustomConfigs;

import World16.Utils.CustomYmlManager;

public class CustomConfigManager {

    private CustomYmlManager shitYml;

    public CustomConfigManager() {

    }

    public void registerAllCustomConfigs() {
        //Shit.yml
        this.shitYml = new CustomYmlManager();
        this.shitYml.setup("shit.yml");
        this.shitYml.saveConfig();
        this.shitYml.reloadConfig();
        //...

    }

    public CustomYmlManager getShitYml() {
        return shitYml;
    }
}
