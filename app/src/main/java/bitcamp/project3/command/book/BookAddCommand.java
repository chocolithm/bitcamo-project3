package bitcamp.project3.command.book;

import bitcamp.project3.command.Command;
import bitcamp.project3.vo.Book;
import bitcamp.util.Prompt;
import java.time.LocalDate;
import java.util.List;

public class BookAddCommand implements Command {

    private List<Book> bookList;

    public BookAddCommand(List<Book> list) {
        this.bookList = list;
    }
    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

        Book book = new Book();
        book.setName(Prompt.input("제목?"));
        book.setAuthor(Prompt.input("저자?"));
        book.setCategory(Prompt.input("카테고리?"));
        book.setRegisteredDate(LocalDate.now());
        book.setNo(Book.getNextSeqNo());
        bookList.add(book);
        Prompt.printAddComplete();
    }
}
