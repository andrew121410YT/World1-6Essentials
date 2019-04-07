package World16.CustomInventorys;

import World16.CustomInventorys.eram.ERamInsideInventory;
import World16.CustomInventorys.eram.ERamListInventory;
import World16.Main.Main;

public class CustomInventoryManager {

    private TestCustomInventory testMenu = null;
    private ERamListInventory eRamListMenu = null;
    private ERamInsideInventory eRamInsideInventory = null;

    private Main plugin;

    public CustomInventoryManager(Main getPlugin) {
        this.plugin = getPlugin;
    }

    public void registerAllCustomInventorys() {
        //Test
        testMenu = new TestCustomInventory(this, this.plugin);
        testMenu.createCustomInv();

        //Eram List
        eRamListMenu = new ERamListInventory(this, this.plugin);
        eRamListMenu.createCustomInv();

        eRamInsideInventory = new ERamInsideInventory(this, this.plugin);
        eRamInsideInventory.createCustomInv();
    }

    public TestCustomInventory getTestMenu() {
        return testMenu;
    }

    public ERamListInventory geteRamListMenu() {
        return eRamListMenu;
    }

    public ERamInsideInventory geteRamInsideInventory() {
        return eRamInsideInventory;
    }

    //RANDOM
}