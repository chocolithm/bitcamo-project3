package bitcamp.project3.command.user;

import bitcamp.project3.command.Command;
import bitcamp.project3.vo.User;
import bitcamp.util.Prompt;
import java.util.List;

public class UserListCommand implements Command {
  List<User> userList;

  public UserListCommand(List<User> list) {
    this.userList = list;
  }

  @Override
  public void execute(String menuName) {
    System.out.printf("[%s]\n", menuName);

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

    Prompt.input("(엔터 입력 시 종료)");
  }
}
