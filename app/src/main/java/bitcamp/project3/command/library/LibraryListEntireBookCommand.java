package bitcamp.project3.command.library;

import bitcamp.project3.command.Command;
import bitcamp.project3.vo.Book;
import bitcamp.util.Prompt;
import java.util.List;

public class LibraryListEntireBookCommand implements Command {

    private List<Book> bookList;

    public LibraryListEntireBookCommand(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

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

        Prompt.input("(엔터 입력 시 종료)");
    }
}
