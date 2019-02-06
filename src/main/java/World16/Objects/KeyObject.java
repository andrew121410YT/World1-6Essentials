package World16.Objects;

public class KeyObject {

    String playerName;

    String keyName1;
    Integer keyID1;
    String key1;

    String keyName2;
    Integer keyID2;
    String key2;

    String keyName3;
    Integer keyID3;
    String key3;

    String keyName4;
    Integer keyID4;
    String key4;

    String keyName5;
    Integer keyID5;
    String key5;

    public KeyObject() {
    }

    public KeyObject(Integer keyID1, String key1) {
        this("UNKNOWN", "default", keyID1, key1);
    }

    public KeyObject(String keyName1, String key1) {
        this("UNKNOWN", keyName1, 1, key1);
    }

    public KeyObject(String playerName, String keyName1, String key1) {
        this(playerName, keyName1, 1, key1);
    }

    public KeyObject(String playerName, Integer keyID1, String key1) {
        this(playerName, "default", keyID1, key1);
    }

    public KeyObject(String playerName, String keyName1, Integer keyID1, String key1) {
        this.playerName = playerName;
        this.keyName1 = keyName1;
        this.keyID1 = keyID1;
        this.key1 = key1;
    }
    //---------------------------------------------------------------------------

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getKeyName1() {
        return keyName1;
    }

    public void setKeyName1(String keyName1) {
        this.keyName1 = keyName1;
    }

    public Integer getKeyID1() {
        return keyID1;
    }

    public void setKeyID1(Integer keyID1) {
        this.keyID1 = keyID1;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKeyName2() {
        return keyName2;
    }

    public void setKeyName2(String keyName2) {
        this.keyName2 = keyName2;
    }

    public Integer getKeyID2() {
        return keyID2;
    }

    public void setKeyID2(Integer keyID2) {
        this.keyID2 = keyID2;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKeyName3() {
        return keyName3;
    }

    public void setKeyName3(String keyName3) {
        this.keyName3 = keyName3;
    }

    public Integer getKeyID3() {
        return keyID3;
    }

    public void setKeyID3(Integer keyID3) {
        this.keyID3 = keyID3;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getKeyName4() {
        return keyName4;
    }

    public void setKeyName4(String keyName4) {
        this.keyName4 = keyName4;
    }

    public Integer getKeyID4() {
        return keyID4;
    }

    public void setKeyID4(Integer keyID4) {
        this.keyID4 = keyID4;
    }

    public String getKey4() {
        return key4;
    }

    public void setKey4(String key4) {
        this.key4 = key4;
    }

    public String getKeyName5() {
        return keyName5;
    }

    public void setKeyName5(String keyName5) {
        this.keyName5 = keyName5;
    }

    public Integer getKeyID5() {
        return keyID5;
    }

    public void setKeyID5(Integer keyID5) {
        this.keyID5 = keyID5;
    }

    public String getKey5() {
        return key5;
    }

    public void setKey5(String key5) {
        this.key5 = key5;
    }
}