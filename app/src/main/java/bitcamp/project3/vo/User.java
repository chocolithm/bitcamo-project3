package bitcamp.project3.vo;

import java.awt.print.Book;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class User {
  String id;
  String pw;
  String name;
  boolean isAdmin;
  Date joinDate;
  List<Book> borrowedBookList;

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

  public Date getJoinDate() {
    return joinDate;
  }

  public void setJoinDate(Date joinDate) {
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
