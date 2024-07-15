package bitcamp.login;

import bitcamp.menu.MenuGroup;
import bitcamp.project3.command.Command;
import bitcamp.project3.vo.User;
import bitcamp.util.Prompt;
import java.util.List;

public class LoginCommand implements Command {
  List<User> userList;
  Login loginUser = Login.getInstance();
  MenuGroup adminMainMenu;
  MenuGroup userMainMenu;

  public LoginCommand(List<User> userList, MenuGroup adminMainMenu, MenuGroup userMainMenu) {
    this.userList = userList;
    this.adminMainMenu = adminMainMenu;
    this.userMainMenu = userMainMenu;
  }

  public void execute(String menuName) {
    String id = Prompt.input("id?");
    String pw = Prompt.input("pw?");

    User user = new User();
    user.setId(id);

    if(!userList.contains(user)) {
      Prompt.printLoginException();
      return;
    }

    user = userList.get(userList.indexOf(user));

    if(user.getPw().equals(pw)) {

      Login login = Login.getInstance();
      login.setId(id);
      login.setName(user.getName());
      login.setAdmin(user.isAdmin());

      loginUser = login;

      if(loginUser.isAdmin()) {
        adminMainMenu.execute();
      } else {
        userMainMenu.execute();
      }
    } else {
      Prompt.printLoginException();
    }
  }
}
