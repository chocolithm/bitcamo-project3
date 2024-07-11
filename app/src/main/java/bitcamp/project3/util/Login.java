package bitcamp.project3.util;

public class Login {
  String name;

  Login() {

  }

  private static Login loginUser;

  public static Login getInstance() {
    loginUser = new Login();

    return loginUser;
  }

  public static void freeInstance() {
    loginUser = null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
