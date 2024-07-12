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
            return;
        }

        Book deletedBook = bookList.remove(index);
        Prompt.printDeleteComplete(deletedBook.getName(), "책");
    }

    private void updateBook() {
        int bookNo = Prompt.inputInt("책 번호?");
        int index = bookList.indexOf(new Book(bookNo));
        if (index == -1) {
            System.out.println("없는 책 입니다.");
            return;
        }

        Book book = bookList.get(index);

        book.setName(Prompt.input("제목(%s)?", book.getName()));
        book.setAuthor(Prompt.input("저자(%s)?", book.getAuthor()));
        book.setCategory(Prompt.input("카테고리(%s)?", book.getCategory()));
        Prompt.printUpdateComplete();
    }


    private void listBook() {
        System.out.println("번호 제목 저자 카테고리");
        for (Book book : bookList) {
            System.out.printf("%d %s %s %s\n",
                    book.getNo(), book.getName(), book.getAuthor(), book.getCategory());
        }
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
