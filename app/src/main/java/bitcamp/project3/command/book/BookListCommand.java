package bitcamp.project3.command.book;

import bitcamp.project3.command.Command;
import bitcamp.project3.vo.Book;
import bitcamp.util.Prompt;
import java.util.List;

public class BookListCommand implements Command {

    private List<Book> bookList;

    public BookListCommand(List<Book> list) {
        this.bookList = list;
    }
    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

        System.out.printf("번호%s제목%s저자%s카테고리\n",
            Prompt.getSpaces(8, "번호"),
            Prompt.getSpaces(20, "제목"),
            Prompt.getSpaces(16, "저자")
        );
        for (Book book : bookList) {
            System.out.printf("%d%s%s%s%s%s%s\n",
                book.getNo(), Prompt.getSpaces(8, String.valueOf(book.getNo())),
                book.getName(), Prompt.getSpaces(20, book.getName()),
                book.getAuthor(), Prompt.getSpaces(16, book.getAuthor()),
                book.getCategory()
            );
        }

        Prompt.input("(엔터 입력 시 종료)");
    }
}
