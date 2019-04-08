package World16.CustomInventorys;

import World16.Main.Main;

public class CustomInventoryManager {

    private TestCustomInventory testMenu;

    private Main plugin;

    public CustomInventoryManager(Main getPlugin) {
        this.plugin = getPlugin;
    }

    public void registerAllCustomInventorys() {
        //Test
        testMenu = new TestCustomInventory(this, this.plugin);
        testMenu.createCustomInv();
    }

    public TestCustomInventory getTestMenu() {
        return testMenu;
    }

    //RANDOM
}