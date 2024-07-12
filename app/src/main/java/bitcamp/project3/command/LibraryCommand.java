package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.Book;
import bitcamp.project3.vo.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryCommand extends AbstractCommand {

    private List<Book> bookList;
    private List<User> userList;
    private User currentUser;
    private String[] menus = {"도서대출", "도서반납", "신간도서", "전체도서목록", "이용안내"};

    public LibraryCommand(String menuTitle, List<Book> bookList, List<User> userList, User currentUser) {
        super(menuTitle);
        this.bookList = bookList;
        this.userList = userList;
        this.currentUser = currentUser;
    }

    @Override
    protected String[] getMenus() {
        return menus;
    }

    @Override
    protected void processMenu(String menuName) {
        switch (menuName) {
            case "도서대출":
                this.searchBook();
                Prompt.loading(1000);
                break;
            case "도서반납":
                this.returnBook();
                Prompt.loading(1000);
                break;
            case "신간도서":
                this.listNewBooks();
                Prompt.loading(1000);
                break;
            case "전체도서목록":
                this.listEntireBook();
                Prompt.loading(1000);
                break;
            case "이용안내":
                this.showGuide();
                Prompt.loading(1000);
                break;
        }
    }

    private void searchBook() {
        String title = Prompt.input("도서 제목?");
        System.out.printf("번호%s분류%s도서명%s대출여부\n",
            Prompt.getSpaces(8, "번호"),
            Prompt.getSpaces(12, "분류"),
            Prompt.getSpaces(20, "도서명")
        );

        List<Book> searchList = getSearchListByTitle(title);

        if (searchList.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        int bookNo = Prompt.inputInt("도서 번호(0 이전)?");
        if (bookNo == 0) return;

        Book selectedBook = getSelectedBook(searchList, bookNo);

        if (selectedBook == null) {
            System.out.println("잘못된 도서 번호입니다.");
            return;
        }

        borrowBook(selectedBook);
    }

    private void returnBook() {
        List<Book> myBookList = currentUser.getBorrowedBookList();

        if(myBookList.isEmpty()) {
            System.out.println("대출한 도서가 없습니다.");
            return;
        }

        listMyBook(myBookList);

        int bookNo = Prompt.inputInt("반납할 도서 번호(0 이전)?");
        if(bookNo == 0) {
            return;
        }

        Book selectedBook = getSelectedBook(bookList, bookNo);

        if (selectedBook == null || !myBookList.contains(selectedBook)) {
            System.out.println("대출한 도서가 아닙니다.");
            return;
        }

        myBookList.remove(selectedBook);
        currentUser.setBorrowedBookList(myBookList);

        if (selectedBook.hasReserbation()) {
            processBorrowForReserver(selectedBook);
        } else {
            selectedBook.returnBook();
            System.out.println("도서를 반납했습니다.");
        }
    }

    private void processBorrowForReserver(Book selectedBook) {
        User reserver = selectedBook.getReservedBy();
        selectedBook.lendToReservation();
        List<Book> reserverBookList = reserver.getBorrowedBookList();
        reserverBookList.add(selectedBook);
        System.out.println("도서를 반납했습니다. 예약자에게 자동으로 대출 됩니다.");
    }

    private void listNewBooks() {
        int month = Prompt.inputInt("월?");
        System.out.printf("[%d월 신간도서]\n", month);
        System.out.printf("번호%s분류%s도서명%s대출여부\n",
            Prompt.getSpaces(8, "번호"),
            Prompt.getSpaces(12, "분류"),
            Prompt.getSpaces(20, "도서명")
        );

        List<Book> searchList = getSearchListByDate(month);

        if (searchList.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        int bookNo = Prompt.inputInt("도서 번호(0 이전)?");
        if (bookNo == 0) return;

        Book selectedBook = getSelectedBook(searchList, bookNo);

        if (selectedBook == null) {
            System.out.println("잘못된 도서 번호입니다.");
            return;
        }

        borrowBook(selectedBook);
    }

    private List<Book> getSearchListByTitle(String title) {
        List<Book> searchList = new ArrayList<>();

        for (Book book : bookList) {
            if (book.getName().toLowerCase().contains(title)) {
                searchList.add(book);
                System.out.printf("%d%s%s%s%s%s%s\n",
                    book.getNo(), Prompt.getSpaces(8, String.valueOf(book.getNo())),
                    book.getCategory(), Prompt.getSpaces(12, book.getCategory()),
                    book.getName(), Prompt.getSpaces(20, book.getName()),
                    book.isBorrowed() ? (book.isReserved() ? "예약중" : "대출중") : "대출가능");
            }
        }

        return searchList;
    }

    private List<Book> getSearchListByDate(int month) {
        List<Book> searchList = new ArrayList<>();

        for (Book book : bookList) {
            if (book.getRegisteredDate().getMonthValue() == month) {
                searchList.add(book);
                System.out.printf("%d%s%s%s%s%s%s\n",
                    book.getNo(), Prompt.getSpaces(8, String.valueOf(book.getNo())),
                    book.getCategory(), Prompt.getSpaces(12, book.getCategory()),
                    book.getName(), Prompt.getSpaces(20, book.getName()),
                    book.isBorrowed() ? (book.isReserved() ? "예약중" : "대출중") : "대출가능");
            }
        }

        return searchList;
    }

    private Book getSelectedBook(List<Book> searchList, int bookNo) {
        Book selectedBook = null;

        for (Book book : searchList) {
            if (book.getNo() == bookNo) {
                selectedBook = book;
                break;
            }
        }

        return selectedBook;
    }

    private void borrowBook(Book selectedBook) {
        List<Book> myBookList = currentUser.getBorrowedBookList();

        if (!selectedBook.isBorrowed()) {
            selectedBook.setBorrowed(true);
            selectedBook.setBorrowedBy(currentUser);
            selectedBook.setBorrowedDate(LocalDate.now());

            myBookList.add(selectedBook);
            currentUser.setBorrowedBookList(myBookList);

            System.out.println("대출되었습니다.");
        } else if(myBookList.contains(selectedBook)) {
            System.out.println("이미 대출한 도서입니다.");
        } else if (selectedBook.isReserved()) {
            System.out.println("예약중인 도서입니다. 대출이 불가합니다.");
        } else {
            System.out.println("대출 중인 도서입니다. 예약하시겠습니까? (y/n)");
            String answer = Prompt.input("선택 (y/n)");
            if (answer.equalsIgnoreCase("y")) {
                selectedBook.setReserved(true);
                selectedBook.setReservedBy(currentUser);
                System.out.println("예약되었습니다.");
            }
        }
    }

    private void listMyBook(List<Book> myBookList) {
        String borrowDate = "";
        String returnDate = "";

        System.out.printf("번호%s분류%s도서명%s대출일%s반납예정일%s상태\n",
            Prompt.getSpaces(8, "번호"),
            Prompt.getSpaces(12, "분류"),
            Prompt.getSpaces(20, "도서명"),
            Prompt.getSpaces(12, "대출일"),
            Prompt.getSpaces(12, "반납예정일")
        );
        for(Book book : myBookList) {
            borrowDate = String.valueOf(book.getBorrowedDate());
            returnDate = String.valueOf(book.getBorrowedDate().plusDays(14));
            System.out.printf("%d%s%s%s%s%s%s%s%s%s%s\n",
                book.getNo(), Prompt.getSpaces(8, String.valueOf(book.getNo())),
                book.getCategory(), Prompt.getSpaces(12, book.getCategory()),
                book.getName(), Prompt.getSpaces(20, book.getName()),
                borrowDate, Prompt.getSpaces(12, borrowDate),
                returnDate, Prompt.getSpaces(12, returnDate),
                book.isOverdue() ? "연체" : "정상");
        }
    }

    private void listEntireBook() {
        System.out.printf("번호%s분류%s도서명%s저자%s대출상태%s대출일%s반납예정일\n",
            Prompt.getSpaces(8, "번호"),
            Prompt.getSpaces(12, "분류"),
            Prompt.getSpaces(20, "도서명"),
            Prompt.getSpaces(16, "저자"),
            Prompt.getSpaces(12, "대출상태"),
            Prompt.getSpaces(12, "대출일")
        );
        for (Book book : bookList) {
            String borrowStatus = book.isBorrowed() ? "대출중" : (book.isReserved() ? "예약중" : "대출가능");
            String borrowedDate = book.getBorrowedDate() != null ? book.getBorrowedDate().toString() : "-";
            String returnDate = book.getBorrowedDate() != null ? book.getBorrowedDate().plusDays(14).toString() : "-";

            System.out.printf("%d%s%s%s%s%s%s%s%s%s%s%s%s\n",
                book.getNo(), Prompt.getSpaces(8, String.valueOf(book.getNo())),
                book.getCategory(), Prompt.getSpaces(12, book.getCategory()),
                book.getName(), Prompt.getSpaces(20, book.getName()),
                book.getAuthor(), Prompt.getSpaces(16, book.getAuthor()),
                borrowStatus, Prompt.getSpaces(12, borrowStatus),
                borrowedDate, Prompt.getSpaces(12, borrowedDate),
                returnDate
            );
        }
    }

    private void showGuide() {
        String str =
                        "[이용 안내]\n" +
                                "안녕하세요! \uD83D\uDE0A 대출관리시스템에 오신 것을 환영합니다!\n" +
                                "신청 방법: 로그인 후 ‘도서 대출’ 메뉴에서 쉽게 대여 하세요!\n" +
                                "반납 기한: 대출일로부터 14일 이내에 반납해주세요.\n" + "" +
                                "Tip: 보고 싶은 도서는 미리 예약해두면 더욱 편리해요!\n"+
                                "언제나 여러분의 독서를 응원합니다!\n" +
                                "궁금한 점이 있으면 언제든지 고객센터(1234-5678)로 연락해 주세요";

        System.out.println(str);
    }
}
