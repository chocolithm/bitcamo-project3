package bitcamp.project3.vo;

import java.time.LocalDate;

public class Book {

    private static int seqNo;

    private int no; // 번호
    private String name; //제목
    private String author; // 저자
    private String category; // 카테고리
    private boolean isBorrowed; // 대출 상태
    private boolean isReserved; // 예약 상태
//    String 예약한사람
    private LocalDate registerDate; // 등록일
    private LocalDate borrowDate;   // 빌린 기간
    private LocalDate returnDate;   // 반납일
//    private String formattedRegisterDate;
//    private String formattedReturnDate;


    public Book() {

    }

    public Book(int no, String name, String author, String category, boolean isBorrowed,
        boolean isReserved, LocalDate registerDate, LocalDate borrowDate, LocalDate returnDate) {
        this.no = no;
        this.name = name;
        this.author = author;
        this.category = category;
        this.isBorrowed = isBorrowed;
        this.isReserved = isReserved;
        this.registerDate = registerDate;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Book(int no) {
        this.no = no;
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
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }


    public void setRegisterDate() {
        this.registerDate = LocalDate.now();
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate() {
        this.returnDate = this.borrowDate.plusDays(7);
    }
}
