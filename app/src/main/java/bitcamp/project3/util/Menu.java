package bitcamp.project3.util;

import bitcamp.project3.command.BookCommand;
import bitcamp.project3.command.LibraryCommand;
import bitcamp.project3.command.UserCommand;
import bitcamp.project3.vo.Book;
import bitcamp.project3.vo.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Menu {
  String[] loginMenu = {"로그인", "회원가입"};
  String[] userMainMenu = {"도서검색", "신간도서", "대출현황", "이용안내"};
  String[] adminMainMenu = {"사용자관리", "도서관리", "대출기록"};
  Login loginUser;
  Stack<String> menuPath = new Stack<>();

  List<User> userList = new ArrayList<>();
  List<Book> bookList = new ArrayList<>();

  UserCommand userCommand = new UserCommand("사용자관리", userList);
  BookCommand bookCommand = new BookCommand("도서관리", bookList);
  LibraryCommand libraryCommand;

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
    //dummy
    DummyData dummy = new DummyData();
    dummy.addDummyUser();
    dummy.addDummyBook();
    dummy.borrowDummy();

    //loginMenu
    for (;;) {
      for(int i = 0; i < loginMenu.length; i++) {
        System.out.printf("%d. %s\n", (i + 1), loginMenu[i]);
      }
      System.out.println("0. 종료");

      switch (Prompt.input("%s>", getMenuPathTitle(menuPath))){
        case "1": //login
          if(login()){
            mainMenu();
          }
          continue;
        case "2": //join
          userCommand.addUser();
          continue;
        case "0":
          System.out.println("시스템을 종료합니다.");
          return;
        default:
          Prompt.printNumberException();
      }
    }
  }

  public boolean login() {
    String id = Prompt.input("id?");
    String pw = Prompt.input("pw?");

    User user = new User();
    user.setId(id);

    if(!userList.contains(user)) {
      System.out.println("로그인 정보를 확인하세요.");
      return false;
    }

    user = userList.get(userList.indexOf(user));

    if(user.getPw().equals(pw)) {
      libraryCommand = new LibraryCommand("", bookList, userList, user);

      Login login = Login.getInstance();
      login.setId(id);
      login.setName(user.getName());
      login.setAdmin(user.isAdmin());

      loginUser = login;
      return true;
    } else {
      System.out.println("로그인 정보를 확인하세요.");
      return false;
    }
  }

  public void mainMenu() {
    menuPath.push("메인");
    if(loginUser.isAdmin()) {
      adminMainMenu();
    } else {
      userMainMenu();
    }
  }

  public void adminMainMenu() {
    for (; ; ) {
      for (int i = 0; i < adminMainMenu.length; i++) {
        System.out.printf("%d. %s\n", (i + 1), adminMainMenu[i]);
      }
      System.out.println("0. 로그아웃");

      switch (Prompt.input("%s>", getMenuPathTitle(menuPath))) {
        case "1":
          userCommand.execute(menuPath);
          continue;
        case "2":
          bookCommand.execute(menuPath);
          continue;
        case "3":
          System.out.println("대출기록");
          continue;
        case "0":
          Logout.performLogout();
          this.loginUser = null;
          menuPath.pop();
          return;
        default:
          Prompt.printNumberException();
      }
    }
  }

  public void userMainMenu() {
    libraryCommand.execute(menuPath);
//    for (;;) {
//      for(int i = 0; i < userMainMenu.length; i++) {
//        System.out.printf("%d. %s\n", (i + 1), userMainMenu[i]);
//      }
//      System.out.println("0. 로그아웃");
//
//      switch (Prompt.input("%s>", getMenuPathTitle(menuPath))){
//        case "1":
//
//          continue;
//        case "2":
//          System.out.println("신간도서");
//          continue;
//        case "3":
//          System.out.println("대출현황");
//          continue;
//        case "4":
//          System.out.println("이용안내");
//          continue;
//        case "0":
//          Logout.performLogout();
//          this.loginUser = null;
//          menuPath.pop();
//          return;
//        default:
//          Prompt.printNumberException();
//      }
//    }
  }

  private String getMenuPathTitle(Stack<String> menuPath) {
    StringBuilder strBuilder = new StringBuilder();
    for (int i = 0; i < menuPath.size(); i++) {
      if (strBuilder.length() > 0) {
        strBuilder.append("/");
      }
      strBuilder.append(menuPath.get(i));
    }
    return strBuilder.toString();
  }

  public class DummyData {
    public void addDummyUser() {
      User user;
      user = new User("root", "0000", "관리자", true, LocalDate.now(), new ArrayList<>());
      userList.add(user);
      user = new User("test", "0000", "사용자1", false, LocalDate.now(), new ArrayList<>());
      userList.add(user);
      user = new User("test2", "0000", "사용자2", false, LocalDate.now(), new ArrayList<>());
      userList.add(user);
    }

    public void addDummyBook() {
      Book book;
      book = new Book(Book.getNextSeqNo(), "인터스텔라", "홍길동", "과학", null, null, LocalDate.of(2024, 7, 10), null);
      bookList.add(book);
      book = new Book(Book.getNextSeqNo(), "군주론", "마키아벨리", "인문", null, null, LocalDate.of(2024, 6, 25), null);
      bookList.add(book);
      book = new Book(Book.getNextSeqNo(), "자바의신", "엄진영", "컴퓨터과학", null, null, LocalDate.of(2024, 6, 20), null);
      bookList.add(book);
    }

    public void borrowDummy() {
      List<Book> dummyBookList;
      List<Book> myBookList;
      User user;
      Book book;

      dummyBookList = new ArrayList<>();
      user = userList.get(1);
      myBookList = user.getBorrowedBookList();
      book = bookList.get(1);
      dummyBookList.add(book);
      dummyBookList.getFirst().setBorrowedBy(user);
      dummyBookList.getFirst().setBorrowed(true);
      myBookList.add(book);
      user.setBorrowedBookList(myBookList);

      dummyBookList = new ArrayList<>();
      user = userList.get(1);
      myBookList = user.getBorrowedBookList();
      book = bookList.get(2);
      dummyBookList.add(book);
      dummyBookList.getFirst().setBorrowedBy(user);
      dummyBookList.getFirst().setBorrowed(true);
      myBookList.add(book);
      user.setBorrowedBookList(myBookList);
    }
  }
}


