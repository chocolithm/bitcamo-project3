package bitcamp.project3.command;

import bitcamp.project3.util.Prompt;
import bitcamp.project3.vo.Book;

import java.time.LocalDate;
import java.util.List;

public class BookCommand extends AbstractCommand {

    private List<Book> bookList;
    private String[] menus = {"등록", "목록", "수정", "삭제"};

    public BookCommand(String menuTitle, List<Book> list) {
        super(menuTitle);
        this.bookList = list;
    }

    @Override
    protected String[] getMenus() {
        return menus;
    }

    @Override
    protected void processMenu(String menuName) {
        System.out.printf("[%s]\n", menuName);
        switch (menuName) {
            case "등록":
                this.addBook();
                break;
            case "목록":
                this.listBook();
                break;
            case "수정":
                this.updateBook();
                break;
            case "삭제":
                this.deleteBook();
                break;
        }
    }

    private void deleteBook() {
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

    private void updateBook() {
        int bookNo = Prompt.inputInt("책 번호?");
        int index = bookList.indexOf(new Book(bookNo));
        if (index == -1) {
            System.out.println("없는 책 입니다.");
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


    private void listBook() {
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
        Prompt.loading(1000);
    }

    private void addBook() {
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
