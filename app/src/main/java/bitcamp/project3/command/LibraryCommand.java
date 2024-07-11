package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.Book;
import bitcamp.project3.vo.User;

import java.util.List;

public class LibraryCommand extends AbstractCommand {

    private List<Book> bookList;
    private List<User> userList;
    private User currentUser;
    private String[] menus = {"도서대출", "도서반납", "신간도서", "대출현황", "이용안내"};

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
                break;
            case "도서반납":
                this.returnBook();
                break;
            case "신간도서":
                this.newBooks();
                break;
            case "대출현황":
                this.showStatus();
                break;
            case "이용안내":
                this.showGuide();
                break;
        }
    }

    private void searchBook() {
        String title = Prompt.input("도서 제목?");
        System.out.println("번호    분류    도서명          대출여부");
        int count = 0;
        for (Book book : bookList) {
            if (book.getName().contains(title)) {
                System.out.printf("%d       %s    %s      %s\n",
                        book.getNo(), book.getCategory(), book.getName(),
                        book.isBorrowed() ? (book.isReserved() ? "예약중" : "대출중") : "대출가능");
                count++;
            }
        }

        if (count == 0) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        int bookNo = Prompt.inputInt("도서 번호(0 이전)?");
        if (bookNo == 0) return;

        Book selectedBook = null;
        for (Book book : bookList) {
            if (book.getNo() == bookNo) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook == null) {
            System.out.println("잘못된 도서 번호입니다.");
            return;
        }

        if (!selectedBook.isBorrowed()) {
            selectedBook.setBorrowedBy(currentUser);
            currentUser.getBorrowedBookList().add(selectedBook);
            System.out.println("대출되었습니다.");
        } else if (selectedBook.isReserved()) {
            System.out.println("예약중인 도서입니다. 대출이 불가합니다.");
        } else {
            System.out.println("대출 중인 도서입니다. 예약하시겠습니까? (y/n)");
            String answer = Prompt.input("선택 (y/n)");
            if (answer.equalsIgnoreCase("y")) {
                selectedBook.setReservedBy(currentUser);
                System.out.println("예약되었습니다.");
            }
        }
    }

    private void returnBook() {
        
    }

    private void newBooks() {
    }

    private void showStatus() {
    }

    private void showGuide() {
    }
}
