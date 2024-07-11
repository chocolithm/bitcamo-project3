package bitcamp.project3.command;

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
      case "변경":
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
      return;
    }
    user.setPw(Prompt.input("PW?"));
    user.setName(Prompt.input("이름?"));
    user.setAdmin(false);
    user.setJoinDate(LocalDate.now());
    user.setBorrowedBookList(new ArrayList<>());
    userList.add(user);

    System.out.println("등록되었습니다.");
  }

  private void listUser() {
    System.out.println("ID 이름 등록일");
    for (User user : userList) {
      System.out.printf("%s %s %s\n", user.getId(), user.getName(), user.getJoinDate());
    }
  }

  private void updateUser() {
    String userId = Prompt.input("ID?");
    int index = userList.indexOf(new User(userId));
    if (index == -1) {
      System.out.println("없는 회원입니다.");
      return;
    }

    User user = userList.get(index);

    // 차후 구현
    System.out.println("변경 했습니다.");
  }

  private void deleteUser() {
    String userId = Prompt.input("ID?");
    int index = userList.indexOf(new User(userId));
    if (index == -1) {
      System.out.println("없는 회원입니다.");
      return;
    }

    User deletedUser = userList.remove(index);
    System.out.printf("'%s' 회원을 삭제 했습니다.\n", deletedUser.getName());
  }
}
