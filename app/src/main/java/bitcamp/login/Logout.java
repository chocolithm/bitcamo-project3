package bitcamp.login;

import bitcamp.util.Prompt;

public class Logout {
    private Logout() {
    }

    public static void executeLogout() {
        Login loginUser = Login.getInstance();
        if (loginUser.isLoggedIn()) {
            Login.freeInstance();
            Prompt.printLogout();
        } else {
            System.out.println("이미 로그아웃 상태입니다.");
        }
    }
}
