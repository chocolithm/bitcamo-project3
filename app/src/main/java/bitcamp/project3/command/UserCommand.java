package bitcamp.project3.command;

import bitcamp.project3.vo.User;
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
//        this.listUser();
        break;
      case "변경":
//        this.updateUser();
        break;
      case "삭제":
//        this.deleteUser();
        break;
    }
  }
}
