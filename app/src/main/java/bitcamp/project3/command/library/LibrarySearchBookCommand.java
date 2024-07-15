package bitcamp.project3.command.library;

import bitcamp.project3.command.Command;
import bitcamp.project3.vo.Book;
import bitcamp.util.Prompt;
import java.util.ArrayList;
import java.util.List;

public class LibrarySearchBookCommand implements Command {

    private List<Book> bookList;
    private LibraryHandler libraryHandler;

    public LibrarySearchBookCommand(List<Book> bookList, LibraryHandler libraryHandler) {
        this.bookList = bookList;
        this.libraryHandler = libraryHandler;
    }

    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

        String title = Prompt.input("도서 제목?");
        System.out.printf("번호%s분류%s도서명%s대출여부\n",
            Prompt.getSpaces(8, "번호"),
            Prompt.getSpaces(12, "분류"),
            Prompt.getSpaces(20, "도서명")
        );

        List<Book> searchList = getSearchListByTitle(title);

        if (searchList.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            Prompt.loading(1000);
            return;
        }

        int bookNo = Prompt.inputInt("도서 번호(0 이전)?");
        if (bookNo == 0) return;

        Book selectedBook = libraryHandler.getSelectedBook(searchList, bookNo);

        if (selectedBook == null) {
            System.out.println("잘못된 도서 번호입니다.");
            Prompt.loading(1000);
            return;
        }

        libraryHandler.borrowBook(selectedBook);
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
}
