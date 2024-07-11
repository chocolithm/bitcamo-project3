package bitcamp.project3.util;

public class Login {
  private String name;
  private static Login loginUser;

  private Login() {
  }

  public static Login getInstance() {
    if (loginUser == null) {
      loginUser = new Login();
    }
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

  public boolean isLoggedIn() {
    return name != null && !name.isEmpty();
  }
}
