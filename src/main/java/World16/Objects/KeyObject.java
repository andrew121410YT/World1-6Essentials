package World16.Objects;

public class KeyObject {

    String playername;

    String keyname1;
    String key1;

    String keyname2;
    String key2;

    String keyname3;
    String key3;

    public KeyObject() {
    }

    public KeyObject(String key1) {
        this("UNKNOWN", "default", key1);
    }

    public KeyObject(String playerName, String key1) {
        this(playerName, "default", key1);
    }

    public KeyObject(String playerName, String keyName1, String key1) {
        this.playername = playerName;
        this.keyname1 = keyName1;
        this.key1 = key1;
    }

    public String getPlayerName() {
        return this.playername;
    }

    public String getKeyName1() {
        return this.keyname1;
    }

    public String getKey1() {
        return this.key1;
    }
//----------------------------------------------------------
    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public String getKeyname2() {
        return keyname2;
    }

    public void setKeyname2(String keyname2) {
        this.keyname2 = keyname2;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKeyname3() {
        return keyname3;
    }

    public void setKeyname3(String keyname3) {
        this.keyname3 = keyname3;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }
}
