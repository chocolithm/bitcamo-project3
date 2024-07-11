package bitcamp.project3.util;

public class Logout {
    private Logout() {
    }

    public static void performLogout() {
        Login loginUser = Login.getInstance();
        if (loginUser.isLoggedIn()) {
            loginUser.setName(null);
            Prompt.printLogout();
        } else {
            System.out.println("이미 로그아웃 상태입니다.");
        }
    }
}