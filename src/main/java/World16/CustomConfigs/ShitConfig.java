package World16.CustomConfigs;

import World16.Utils.CustomYmlManager;

public class ShitConfig {

    private CustomYmlManager ymlManger;

    public ShitConfig() {
        this.ymlManger = new CustomYmlManager();

        this.ymlManger.setup("shit.yml");
        this.ymlManger.saveConfig();
        this.ymlManger.reloadConfig();
    }

    public CustomYmlManager getInstance() {
        return this.ymlManger;
    }
}