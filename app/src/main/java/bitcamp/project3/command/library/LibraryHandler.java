package bitcamp.project3.command.library;

import bitcamp.login.Login;
import bitcamp.project3.vo.Book;
import bitcamp.project3.vo.User;
import bitcamp.util.Prompt;
import java.time.LocalDate;
import java.util.List;

public class LibraryHandler {

    private List<User> userList;
    private User currentUser;

    public LibraryHandler(List<User> userList) {
        this.userList = userList;
    }

    public void borrowBook(Book selectedBook) {
        currentUser = userList.get(userList.indexOf(new User(Login.getInstance().getId())));

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
            System.out.println("대출 중인 도서입니다. 예약하시겠습니까?");
            String answer = Prompt.input("선택 (y/n)");
            if (answer.equalsIgnoreCase("y")) {
                selectedBook.setReserved(true);
                selectedBook.setReservedBy(currentUser);
                System.out.println("예약되었습니다.");
            }
        }

        Prompt.loading(1000);
    }

    public Book getSelectedBook(List<Book> searchList, int bookNo) {
        Book selectedBook = null;

        for (Book book : searchList) {
            if (book.getNo() == bookNo) {
                selectedBook = book;
                break;
            }
        }

        return selectedBook;
    }
}
