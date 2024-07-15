package bitcamp.project3.command.book;

import bitcamp.project3.command.Command;
import bitcamp.project3.vo.Book;
import bitcamp.util.Prompt;
import java.util.List;

public class BookUpdateCommand implements Command {

    private List<Book> bookList;

    public BookUpdateCommand(List<Book> list) {
        this.bookList = list;
    }
    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

        int bookNo = Prompt.inputInt("책 번호?");
        int index = bookList.indexOf(new Book(bookNo));
        if (index == -1) {
            System.out.println("없는 책입니다.");
            Prompt.loading(1000);
            return;
        }

        Book book = bookList.get(index);
        String[] updateMenu = {"제목", "저자", "카테고리"};
        for(int i = 0; i < updateMenu.length; i++) {
            System.out.printf("%d. %s\n", (i + 1), updateMenu[i]);
        }
        switch (Prompt.input("번호?")) {
            case "1":
                book.setName(Prompt.input("제목?(%s)", book.getName()));
                break;
            case "2":
                book.setAuthor(Prompt.input("저자?(%s)", book.getAuthor()));
                break;
            case "3":
                book.setCategory(Prompt.input("카테고리?(%s)", book.getCategory()));
                break;
            default:
                System.out.println("없는 항목입니다.");
                Prompt.loading(1000);
                return;
        }

        Prompt.printUpdateComplete();
    }
}
