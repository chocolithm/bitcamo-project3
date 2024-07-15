package bitcamp.project3.command.user;

import bitcamp.project3.command.Command;
import bitcamp.project3.vo.User;
import bitcamp.util.Prompt;
import java.util.List;

public class UserUpdateCommand implements Command {
  List<User> userList;

  public UserUpdateCommand(List<User> list) {
    this.userList = list;
  }

  @Override
  public void execute(String menuName) {
    System.out.printf("[%s]\n", menuName);

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
          System.out.println("관리자 권한을 부여합니다.");
          Prompt.loading(1000);
          return;
        } else {
          System.out.println("관리자 권한을 회수합니다.");
          Prompt.loading(1000);
          return;
        }
      default:
        System.out.println("없는 항목입니다.");
        Prompt.loading(1000);
        return;
    }

    Prompt.printUpdateComplete();
  }
}
