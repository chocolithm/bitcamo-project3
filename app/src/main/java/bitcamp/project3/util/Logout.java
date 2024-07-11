package bitcamp.project3.util;

public class Logout {
    private Logout() {
    }

    public static void performLogout() {
        Login loginUser = Login.getInstance();
        if (loginUser.isLoggedIn()) {
            loginUser.setName(null);
        }
    }
}