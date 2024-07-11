package bitcamp.project3.util;

public class Login {
  private String id;
  private String name;
  private boolean isAdmin;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  public boolean isLoggedIn() {
    return name != null && !name.isEmpty();
  }
}
