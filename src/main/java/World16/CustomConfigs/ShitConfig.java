package World16.CustomConfigs;

import World16.Utils.CustomYmlManger;

public class ShitConfig {

    private CustomYmlManger ymlManger;

    public ShitConfig() {
        this.ymlManger = new CustomYmlManger();

        this.ymlManger.setup("shit.yml");
        this.ymlManger.saveConfig();
        this.ymlManger.reloadConfig();
    }

    public CustomYmlManger getInstance() {
        return this.ymlManger;
    }
}