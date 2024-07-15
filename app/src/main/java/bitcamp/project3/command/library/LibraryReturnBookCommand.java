package bitcamp.project3.command.library;

import bitcamp.login.Login;
import bitcamp.project3.command.Command;
import bitcamp.project3.vo.Book;
import bitcamp.project3.vo.User;
import bitcamp.util.Prompt;
import java.util.List;

public class LibraryReturnBookCommand implements Command {

    private List<Book> bookList;
    private List<User> userList;
    private User currentUser;
    private LibraryHandler libraryHandler;

    public LibraryReturnBookCommand(List<Book> bookList, List<User> userList, LibraryHandler libraryHandler) {
        this.bookList = bookList;
        this.userList = userList;
        this.libraryHandler = libraryHandler;
    }

    @Override
    public void execute(String menuName) {
        currentUser = userList.get(userList.indexOf(new User(Login.getInstance().getId())));

        System.out.printf("[%s]\n", menuName);

        List<Book> myBookList = currentUser.getBorrowedBookList();

        if(myBookList.isEmpty()) {
            System.out.println("대출한 도서가 없습니다.");
            Prompt.loading(1000);
            return;
        }

        listMyBook(myBookList);

        int bookNo = Prompt.inputInt("반납할 도서 번호(0 이전)?");
        if(bookNo == 0) {
            return;
        }

        Book selectedBook = libraryHandler.getSelectedBook(bookList, bookNo);

        if (selectedBook == null || !myBookList.contains(selectedBook)) {
            System.out.println("대출한 도서가 아닙니다.");
            Prompt.loading(1000);
            return;
        }

        myBookList.remove(selectedBook);
        currentUser.setBorrowedBookList(myBookList);

        if (selectedBook.hasReserbation()) {
            processBorrowForReserver(selectedBook);
        } else {
            selectedBook.returnBook();
            System.out.println("도서를 반납했습니다.");
            Prompt.loading(1000);
        }
    }

    private void processBorrowForReserver(Book selectedBook) {
        User reserver = selectedBook.getReservedBy();
        selectedBook.lendToReservation();
        List<Book> reserverBookList = reserver.getBorrowedBookList();
        reserverBookList.add(selectedBook);
        System.out.println("도서를 반납했습니다. 예약자에게 자동으로 대출 됩니다.");
        Prompt.loading(1000);
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
}
