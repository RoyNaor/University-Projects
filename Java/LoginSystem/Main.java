package LoginSystem;

public class Main {
    public static void main(String[] args) {
        ID_Passwords idPasswords = new ID_Passwords();

        new Login(idPasswords.getLoginInfo());
    }
}