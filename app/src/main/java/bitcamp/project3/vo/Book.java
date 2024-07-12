package bitcamp.project3.vo;

import java.time.LocalDate;
import java.util.Objects;

public class Book {

    private static final int LOAN_PERIOD_DAYS = 14; // 대출 기간을 14일로 설정
    private static int seqNo;

    private int no; // 번호
    private String name; // 제목
    private String author; // 저자
    private String category; // 카테고리
    private User borrowedBy;
    private User reservedBy;
    private LocalDate registeredDate;  // 도서 등록일
    private LocalDate borrowedDate;    // 대출일
    private boolean reserved;

    public Book() {
    }

    public Book(int no) {
        this.no = no;
    }

    public Book(int no, String name, String author, String category, User borrowedBy,
                User reservedBy,
                LocalDate registeredDate, LocalDate borrowedDate) {
        this.no = no;
        this.name = name;
        this.author = author;
        this.category = category;
        this.borrowedBy = borrowedBy;
        this.reservedBy = reservedBy;
        this.registeredDate = registeredDate;
        this.borrowedDate = borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public static int getNextSeqNo() {
        return ++seqNo;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isBorrowed() {
        return borrowedBy != null;
    }

    public void setBorrowed(boolean borrowed) {
        if (borrowed) {
            this.borrowedDate = LocalDate.now();
        } else {
            this.borrowedDate = null;
        }
    }

    public boolean isReserved() {
        return reservedBy != null;
    }

    public User getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(User borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public User getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(User reservedBy) {
        this.reservedBy = reservedBy;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDate getReturnDate() {
        return borrowedDate != null ? borrowedDate.plusDays(LOAN_PERIOD_DAYS) : null;
    }

    public boolean isOverdue() {
        return borrowedDate != null && LocalDate.now().isAfter(getReturnDate());
    }

    public void returnBook() {
        this.borrowedBy = null;
        this.borrowedDate = null;
        this.reservedBy = null;
        this.reserved = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return no == book.no;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(no);
    }
}
