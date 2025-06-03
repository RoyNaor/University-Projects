package LoginSystem;

import java.util.HashMap;

public class ID_Passwords {
    HashMap<String, String> loginInfo = new HashMap<String, String>();

    public ID_Passwords() {
        loginInfo.put("roy", "123qwe");
        loginInfo.put("ofek", "qweasd");
        loginInfo.put("yuval", "tal");
    }

    protected HashMap<String, String> getLoginInfo() {
        return loginInfo;
    }
}
