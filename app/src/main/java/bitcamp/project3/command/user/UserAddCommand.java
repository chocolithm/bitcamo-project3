package bitcamp.project3.command.user;

import bitcamp.project3.command.Command;
import bitcamp.project3.vo.User;
import bitcamp.util.Prompt;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserAddCommand implements Command {
  List<User> userList;

  public UserAddCommand(List<User> list) {
    this.userList = list;
  }

  @Override
  public void execute(String menuName) {
    System.out.printf("[%s]\n", menuName);

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
}
