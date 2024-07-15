package bitcamp.project3.command.user;

import bitcamp.login.Login;
import bitcamp.project3.command.Command;
import bitcamp.project3.vo.User;
import bitcamp.util.Prompt;
import java.util.List;

public class UserDeleteCommand implements Command {
  List<User> userList;

  public UserDeleteCommand(List<User> list) {
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
