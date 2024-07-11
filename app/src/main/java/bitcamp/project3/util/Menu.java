package bitcamp.project3.util;

import static bitcamp.project3.util.Prompt.keyboardScanner;

public class Menu {
  String[] loginMenu = {"로그인", "회원가입"};
  String[] userMainMenu = {"도서검색", "신간도서", "대출현황", "이용안내"};
  String[] adminMainMenu = {"사용자관리", "도서관리", "대출기록"};
  String loginUser;

  Menu() {

  }

  private static Menu m;

  public static Menu getInstance() {
    if (m == null) {
      m = new Menu();
    }

    return m;
  }

  public static void freeInstance() {
    m = null;
  }

  public void menu() {
    //loginMenu
    for (;;) {
      for(int i = 0; i < loginMenu.length; i++) {
        System.out.printf("%d. %s\n", (i + 1), loginMenu[i]);
      }
      System.out.println("0. 종료");

      switch (Prompt.input("입력>")){
        case "1": //login
          if(login()){
            mainMenu();
          }
          continue;
        case "2": //join
          System.out.println("회원가입 화면입니다.");
          continue;
        case "0":
          System.out.println("시스템을 종료합니다.");
          return;
        default:
          System.out.println("유효한 숫자를 입력해주세요.");
      }
    }
  }

  public boolean login() {
    String id = Prompt.input("id?");
    String pw = Prompt.input("pw?");

    if(id.equals("root") && pw.equals("0000") || id.equals("test") && pw.equals("0000")) {
      Login.getInstance().setName(id);
      loginUser = id;
      return true;
    } else {
      System.out.println("로그인 정보를 확인하세요.");
      return false;
    }
  }

  public void mainMenu() {
    if(loginUser.equals("root")) {
      System.out.println("관리자 계정으로 로그인합니다.\n");

      adminMainMenu();
    }

    if(loginUser.equals("test")) {
      System.out.println("사용자 계정으로 로그인합니다.\n");

      userMainMenu();
    }
  }

  public void adminMainMenu() {
    for (; ; ) {
      for (int i = 0; i < adminMainMenu.length; i++) {
        System.out.printf("%d. %s\n", (i + 1), adminMainMenu[i]);
      }
      System.out.println("0. 로그아웃");

      switch (Prompt.input("입력>")) {
        case "1":
          System.out.println("사용자관리");
          continue;
        case "2":
          System.out.println("도서관리");
          continue;
        case "3":
          System.out.println("대출기록");
          continue;
        case "0":
          System.out.println("로그아웃합니다.");
          Logout.performLogout();
          this.loginUser = null;
          return;
        default:
          System.out.println("유효한 숫자를 입력해주세요.");
      }
    }
  }

  public void userMainMenu() {
    for (;;) {
      for(int i = 0; i < userMainMenu.length; i++) {
        System.out.printf("%d. %s\n", (i + 1), userMainMenu[i]);
      }
      System.out.println("0. 로그아웃");

      switch (Prompt.input("입력>")){
        case "1":
          System.out.println("도서검색");
          continue;
        case "2":
          System.out.println("신간도서");
          continue;
        case "3":
          System.out.println("대출현황");
          continue;
        case "4":
          System.out.println("이용안내");
          continue;
        case "0":
          System.out.println("로그아웃합니다.");
          return;
        default:
          System.out.println("유효한 숫자를 입력해주세요.");
      }
    }
  }
}
