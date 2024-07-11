package bitcamp.project3.vo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class User {
  String id;
  String pw;
  String name;
  boolean isAdmin;
  LocalDate joinDate;
  List<Book> borrowedBookList;

  public User() {

  }

  public User(String id) {
    this.id = id;
  }

  public User(String id, String pw, String name, boolean isAdmin, LocalDate joinDate,
      List<Book> borrowedBookList) {
    this.id = id;
    this.pw = pw;
    this.name = name;
    this.isAdmin = isAdmin;
    this.joinDate = joinDate;
    this.borrowedBookList = borrowedBookList;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPw() {
    return pw;
  }

  public void setPw(String pw) {
    this.pw = pw;
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

  public LocalDate getJoinDate() {
    return joinDate;
  }

  public void setJoinDate(LocalDate joinDate) {
    this.joinDate = joinDate;
  }

  public List<Book> getBorrowedBookList() {
    return borrowedBookList;
  }

  public void setBorrowedBookList(List<Book> borrowedBookList) {
    this.borrowedBookList = borrowedBookList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
