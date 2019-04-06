package World16.CustomInventorys;

import World16.CustomInventorys.eram.ERamListInventory;

public class CustomInventoryManager {

    private TestCustomInventory testMenu = null;
    private ERamListInventory eRamListMenu = null;

    public CustomInventoryManager() {

    }

    public void registerAllCustomInventorys() {
        //Test
        testMenu = new TestCustomInventory();
        testMenu.createCustomInv();

        //Eram
        eRamListMenu = new ERamListInventory();
        eRamListMenu.createCustomInv();
    }

    public TestCustomInventory getTestMenu() {
        return testMenu;
    }

    public ERamListInventory geteRamListMenu() {
        return eRamListMenu;
    }
}