package bitcamp.menu;

import bitcamp.login.Login;
import bitcamp.login.Logout;
import bitcamp.project3.vo.Book;
import bitcamp.project3.vo.User;
import bitcamp.util.Ansi;
import bitcamp.util.Prompt;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MenuGroup extends AbstractMenu {

  private Stack<String> menuPath;
  private Menu parent;
  private List<Menu> children = new ArrayList<>();;
  private String exitMenuTitle = "이전";
  private Login loginUser = Login.getInstance();
  private List<User> userList;
  private List<Book> bookList;

  public MenuGroup(String title) {
    super(title);
    menuPath = new Stack<>();
  }

  public MenuGroup(String title, List<Book> bookList, List<User> userList) {
    super(title);
    this.bookList = bookList;
    this.userList = userList;
    menuPath = new Stack<>();
  }

  public void execute() {
    if(!title.equals("로그인") && !title.equals("회원가입")) {
      menuPath.push(title);
    }

    while (true) {

      if(title.isEmpty()) {
        printLoginTUI();
        exitMenuTitle = "종료";
      }

      if(title.equals("메인")) {
        printUserMainTUI();
        exitMenuTitle = "로그아웃";
      }

      if(title.equals("관리자")) {
        printAdminMainTUI();
        exitMenuTitle = "로그아웃";
      }

      printMenus();

      String command = Prompt.input("%s>", getMenuPathTitle());
      if (command.equals("menu")) {
        printMenus();
        continue;
      } else if (command.equals("0")) { // 이전 메뉴 선택
        menuPath.pop();
        if(exitMenuTitle.equals("로그아웃")) {
          Logout.executeLogout();
        }
        return;
      }

      try {
        int menuNo = Integer.parseInt(command);
        Menu menu = getMenu(menuNo - 1);
        if (menu == null) {
          System.out.println("유효한 메뉴 번호가 아닙니다.");
          continue;
        }

        menu.execute();

      } catch (NumberFormatException ex) {
        System.out.println("숫자로 메뉴 번호를 입력하세요.");
      }
    }
  }

  private void printMenus() {
//    System.out.printf("[%s]\n", title);
    int i = 1;
    for (Menu menu : children) {
      System.out.printf("%d. %s\n", i++, menu.getTitle());
    }
    System.out.printf("0. %s\n", exitMenuTitle);
  }

  private void printLoginTUI() {
    String str =
        "|------------------------------------------|\n" +
            "|         _____  _____  _____  __  __      |\n" +
            "|        / _  / /    / /    / / / / /      |\n" +
            "|       /   _/ / // / / // / / /_/ /       |\n" +
            "|      / _  | / // / / // / / / \\ \\        |\n" +
            "|     /____/ /____/ /____/ /_/   \\_\\       |\n" +
            "|                                          |\n" +
            "|    비트도서관에 오신 것을 환영합니다.    |\n" +
            "|                                          |\n" +
            "|------------------------------------------|\n";

    Prompt.printBuff();
    System.out.println(str);
  }

  private void printUserMainTUI() {
    String welcome = String.format("'%s'님 환영합니다.\n", loginUser.getName());
    String str =
        "|------------------------------------------|\n" +
        "                                            \n" +
        welcome +
        getOverdueList() +
        "                                            \n" +
        "|------------------------------------------|\n";

    Prompt.printBuff();
    System.out.println(str);
  }

  private String getOverdueList() {
    String str = "\n";
    User currentUser = new User();
    currentUser.setId(loginUser.getId());
    for(User user : userList) {
      if(user.equals(currentUser)) {
        currentUser = user;
      }
    }

    List<Book> myBookList = currentUser.getBorrowedBookList();
    List<Book> overdueBookList = null;
    if(myBookList != null && !myBookList.isEmpty()) {
      overdueBookList = calcOverdueBookList(myBookList);
    }


    if(overdueBookList != null && !overdueBookList.isEmpty()) {
      str += printOverdueBookList(overdueBookList);
    }

    return str;
  }

  private List<Book> calcOverdueBookList(List<Book> myBookList) {
    List<Book> overdueBookList = new ArrayList<>();

    for (Book book : myBookList) {
      if(book.isOverdue()) {
        overdueBookList.add(book);
      }
    }

    return overdueBookList;
  }

  private String printOverdueBookList(List<Book> overdueBookList) {
    String str = "";

    str += Ansi.RED + "*** 연체도서 확인 ***" + Ansi.RESET + "\n";
    for(Book book : overdueBookList) {
      String title = book.getName();
      LocalDate returnDate = book.getReturnDate();
      long overdueDate = ChronoUnit.DAYS.between(returnDate, LocalDate.now());
      str += String.format("'%s' %d일 연체\n", title, overdueDate);
    }

    return str;
  }

  private void printAdminMainTUI() {
    String welcome = String.format("'%s'님 환영합니다.\n", loginUser.getName());
    String str =
        "|------------------------------------------|\n" +
        "                                            \n" +
        welcome +
        getTotalUser() +
        getTotalBook() +
        "                                            \n" +
        "|------------------------------------------|\n";

    Prompt.printBuff();
    System.out.println(str);
  }

  private String getTotalUser() {
    return String.format("전체 사용자 : %d명\n", userList.size());
  }

  private String getTotalBook() {
    return String.format("전체 도서 수 : %d권\n", bookList.size());
  }

  private void setParent(MenuGroup parent) {
    this.parent = parent;
    this.menuPath = parent.menuPath;
  }

  public void add(Menu child) {
    if (child instanceof MenuGroup) {
      ((MenuGroup) child).setParent(this);
    }
    children.add(child);
  }

  public void remove(Menu child) {
    children.remove(child);
  }

  public Menu getMenu(int index) {
    if (index < 0 || index >= children.size()) {
      return null;
    }
    return children.get(index);
  }

  public int countMenus() {
    return children.size();
  }


  private String getMenuPathTitle() {
    StringBuilder strBuilder = new StringBuilder();
    for (int i = 0; i < menuPath.size(); i++) {
      if (strBuilder.length() > 0) {
        strBuilder.append("/");
      }
      strBuilder.append(menuPath.get(i));
    }
    return strBuilder.toString();
  }
}
