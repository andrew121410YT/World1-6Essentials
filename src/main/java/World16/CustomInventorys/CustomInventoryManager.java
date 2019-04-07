package World16.CustomInventorys;

import World16.CustomInventorys.eram.ERamInsideInventory;
import World16.CustomInventorys.eram.ERamListInventory;

public class CustomInventoryManager {

    private TestCustomInventory testMenu = null;
    private ERamListInventory eRamListMenu = null;
    private ERamInsideInventory eRamInsideInventory = null;

    public CustomInventoryManager() {
    }

    public void registerAllCustomInventorys() {
        //Test
        testMenu = new TestCustomInventory(this);
        testMenu.createCustomInv();

        //Eram List
        eRamListMenu = new ERamListInventory(this);
        eRamListMenu.createCustomInv();

        eRamInsideInventory = new ERamInsideInventory(this);
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