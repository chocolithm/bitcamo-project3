package bitcamp.project3.command;

import bitcamp.project3.util.Login;
import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserCommand extends AbstractCommand {
  List<User> userList;
  String[] menus = {"목록", "수정", "삭제"};

  public UserCommand(String menuTitle, List<User> list) {
    super(menuTitle);
    this.userList = list;
  }

  @Override
  protected String[] getMenus() {
    return menus;
  }

  @Override
  protected void processMenu(String menuName) {
    System.out.printf("[%s]\n", menuName);
    switch (menuName) {
      case "목록":
        this.listUser();
        break;
      case "수정":
        this.updateUser();
        break;
      case "삭제":
        this.deleteUser();
        break;
    }
  }

  public void addUser() {
    User user = new User();
    user.setId(Prompt.input("ID?"));
    if(userList.contains(user)) {
      System.out.println("이미 존재하는 ID입니다.");
      Prompt.loading(1000);
      return;
    }
    user.setPw(Prompt.input("PW?"));
    user.setName(Prompt.input("이름?"));
    user.setAdmin(false);
    user.setJoinDate(LocalDate.now());
    user.setBorrowedBookList(new ArrayList<>());
    userList.add(user);

    Prompt.printAddComplete();
  }

  private void listUser() {
    System.out.printf("ID%s이름%s등록일%s대출여부\n",
        Prompt.getSpaces(12, "ID"),
        Prompt.getSpaces(12, "이름"),
        Prompt.getSpaces(12, "등록일")
    );
    for (User user : userList) {
      System.out.printf("%s%s%s%s%s%s%s\n",
          user.getId(), Prompt.getSpaces(12, user.getId()),
          user.getName(), Prompt.getSpaces(12, user.getName()),
          user.getJoinDate(), Prompt.getSpaces(12, String.valueOf(user.getJoinDate())),
          user.isAdmin() ? "관리자" : String.format("%d권", user.getBorrowedBookList().size())
      );
    }
    Prompt.loading(1000);
  }

  private void updateUser() {
    String userId = Prompt.input("ID?");
    int index = userList.indexOf(new User(userId));
    if (index == -1) {
      System.out.println("없는 회원입니다.");
      Prompt.loading(1000);
      return;
    }

    User user = userList.get(index);
    String[] updateMenu = {"비밀번호", "이름", "관리자여부"};
    for(int i = 0; i < updateMenu.length; i++) {
      System.out.printf("%d. %s\n", (i + 1), updateMenu[i]);
    }
    switch (Prompt.input("번호?")) {
      case "1":
        user.setPw(Prompt.input("비밀번호?"));
        break;
      case "2":
        user.setName(Prompt.input("이름?(%s)", user.getName()));
        break;
      case "3":
        user.setAdmin(!user.isAdmin());
        if(user.isAdmin()) {
          System.out.println("관리자 권한 부여");
          Prompt.loading(1000);
        } else {
          System.out.println("관리자 권한 해제");
          Prompt.loading(1000);
        }
        break;
      default:
        System.out.println("없는 항목입니다.");
        Prompt.loading(1000);
    }

    Prompt.printUpdateComplete();
  }

  private void deleteUser() {
    String userId = Prompt.input("ID?");
    int index = userList.indexOf(new User(userId));

    if (index == -1) {
      System.out.println("없는 회원입니다.");
      Prompt.loading(1000);
      return;
    }

    String currentUserId = Login.getInstance().getId();
    User deletedUser = userList.get(index);

    if(deletedUser.getId().equals(currentUserId)) {
      System.out.println("본인은 삭제할 수 없습니다.");
      Prompt.loading(1000);
      return;
    }

    if(!deletedUser.getBorrowedBookList().isEmpty()) {
      System.out.println("현재 대출 중인 회원은 삭제할 수 없습니다.");
      Prompt.loading(1000);
      return;
    }

    userList.remove(index);
    Prompt.printDeleteComplete(deletedUser.getName(), "회원");
  }
}
