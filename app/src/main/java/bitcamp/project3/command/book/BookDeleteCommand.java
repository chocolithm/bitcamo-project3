package bitcamp.project3.command.book;

import bitcamp.project3.command.Command;
import bitcamp.project3.vo.Book;
import bitcamp.util.Prompt;
import java.util.List;

public class BookDeleteCommand implements Command {

    private List<Book> bookList;

    public BookDeleteCommand(List<Book> list) {
        this.bookList = list;
    }
    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

        int bookNo = Prompt.inputInt("책 번호?");
        int index = bookList.indexOf(new Book(bookNo));
        if (index == -1) {
            System.out.println("없는 책 입니다.");
            Prompt.loading(1000);
            return;
        }

        Book deletedBook = bookList.get(index);

        if(deletedBook.isBorrowed()) {
            System.out.println("대출 중인 도서는 삭제할 수 없습니다.");
            Prompt.loading(1000);
            return;
        }

        bookList.remove(index);
        Prompt.printDeleteComplete(deletedBook.getName(), "책");
    }
}
