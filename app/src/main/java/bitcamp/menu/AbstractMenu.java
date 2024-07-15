package bitcamp.menu;

import java.util.Objects;

public abstract class AbstractMenu implements Menu {
  String title;

  public AbstractMenu(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof AbstractMenu that))
      return false;
    return Objects.equals(title, that.title);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(title);
  }

  public String getTitle() {
    return title;
  }
}
