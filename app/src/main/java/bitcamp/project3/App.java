package bitcamp.project3;

import bitcamp.login.LoginCommand;
import bitcamp.menu.MenuGroup;
import bitcamp.menu.MenuItem;
import bitcamp.project3.command.book.BookAddCommand;
import bitcamp.project3.command.book.BookDeleteCommand;
import bitcamp.project3.command.book.BookListCommand;
import bitcamp.project3.command.book.BookUpdateCommand;
import bitcamp.project3.command.library.LibraryHandler;
import bitcamp.project3.command.library.LibraryListEntireBookCommand;
import bitcamp.project3.command.library.LibraryListNewBookCommand;
import bitcamp.project3.command.library.LibraryReturnBookCommand;
import bitcamp.project3.command.library.LibrarySearchBookCommand;
import bitcamp.project3.command.library.LibraryShowGuideCommand;
import bitcamp.project3.command.user.UserAddCommand;
import bitcamp.project3.command.user.UserDeleteCommand;
import bitcamp.project3.command.user.UserListCommand;
import bitcamp.project3.command.user.UserUpdateCommand;
import bitcamp.project3.vo.Book;
import bitcamp.project3.vo.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
    private MenuGroup mainMenu;
    List<User> userList = new ArrayList<>();
    List<Book> bookList = new ArrayList<>();

    public App() {
        new DummyData().addDummy();
        mainMenu = new MenuGroup("");

        MenuGroup userMenu = new MenuGroup("사용자관리");
        userMenu.add(new MenuItem("목록", new UserListCommand(userList)));
        userMenu.add(new MenuItem("수정", new UserUpdateCommand(userList)));
        userMenu.add(new MenuItem("삭제", new UserDeleteCommand(userList)));

        MenuGroup bookMenu = new MenuGroup("도서관리");
        bookMenu.add(new MenuItem("등록", new BookAddCommand(bookList)));
        bookMenu.add(new MenuItem("목록", new BookListCommand(bookList)));
        bookMenu.add(new MenuItem("수정", new BookUpdateCommand(bookList)));
        bookMenu.add(new MenuItem("삭제", new BookDeleteCommand(bookList)));

        MenuGroup adminMainMenu = new MenuGroup("관리자", bookList, userList);
        adminMainMenu.add(userMenu);
        adminMainMenu.add(bookMenu);

        MenuGroup userMainMenu = new MenuGroup("메인", bookList, userList);
        LibraryHandler libraryHandler = new LibraryHandler(userList);
        userMainMenu.add(new MenuItem("도서대출", new LibrarySearchBookCommand(bookList, libraryHandler)));
        userMainMenu.add(new MenuItem("도서반납", new LibraryReturnBookCommand(bookList, userList, libraryHandler)));
        userMainMenu.add(new MenuItem("신간도서", new LibraryListNewBookCommand(bookList, libraryHandler)));
        userMainMenu.add(new MenuItem("전체도서목록", new LibraryListEntireBookCommand(bookList)));
        userMainMenu.add(new MenuItem("이용안내", new LibraryShowGuideCommand()));

        mainMenu.add(new MenuItem("로그인", new LoginCommand(userList, adminMainMenu, userMainMenu)));
        mainMenu.add(new MenuItem("회원가입", new UserAddCommand(userList)));
    }

    public static void main(String[] args) {
//        Menu m = Menu.getInstance();
//
//        m.menu();

        new App().mainMenu.execute();
    }

    private class DummyData {
        private void addDummy() {
            addDummyUser();
            addDummyBook();
            borrowDummy();
        }

        private void addDummyUser() {
            User user;
            user = new User("root", "0000", "엄진영", true, LocalDate.of(2024, 5, 24), new ArrayList<>());
            userList.add(user);
            user = new User("test", "0000", "백현기", false, LocalDate.of(2024, 6, 19), new ArrayList<>());
            userList.add(user);
            user = new User("test2", "0000", "강윤상", false, LocalDate.of(2024, 6, 24), new ArrayList<>());
            userList.add(user);
        }

        private void addDummyBook() {
            Book book;
            book = new Book(Book.getNextSeqNo(), "인터스텔라", "홍길동", "자연과학", null, null, LocalDate.of(2024, 7, 10), null);
            bookList.add(book);
            book = new Book(Book.getNextSeqNo(), "군주론", "마키아벨리", "문학", null, null, LocalDate.of(2024, 6, 25), null);
            bookList.add(book);
            book = new Book(Book.getNextSeqNo(), "자바의신", "엄진영", "기술과학", null, null, LocalDate.of(2024, 6, 20), null);
            bookList.add(book);
            book = new Book(Book.getNextSeqNo(), "이것이 자바다", "신용권", "기술과학", null, null, LocalDate.of(2024, 7, 2), null);
            bookList.add(book);
            book = new Book(Book.getNextSeqNo(), "Design Pattern", "에릭 프리먼", "기술과학", null, null, LocalDate.of(2024, 7, 8), null);
            bookList.add(book);
            book = new Book(Book.getNextSeqNo(), "개미", "베르나르", "문학", null, null, LocalDate.of(2024, 6, 13), null);
            bookList.add(book);
            book = new Book(Book.getNextSeqNo(), "맨큐의 경제학", "맨큐", "사회과학", null, null, LocalDate.of(2024, 7, 15), null);
            bookList.add(book);
            book = new Book(Book.getNextSeqNo(), "토익만점받기", "YBM", "언어", null, null, LocalDate.of(2024, 6, 17), null);
            bookList.add(book);
        }

        private void borrowDummy() {
            List<Book> dummyBookList;
            List<Book> myBookList;
            User user;
            Book book;

            dummyBookList = new ArrayList<>();
            user = userList.get(1);
            myBookList = user.getBorrowedBookList();
            book = bookList.get(1);
            dummyBookList.add(book);
            dummyBookList.getFirst().setBorrowedBy(user);
            dummyBookList.getFirst().setBorrowed(true);
            dummyBookList.getFirst().setBorrowedDate(LocalDate.of(2024, 6, 10));
            myBookList.add(book);
            user.setBorrowedBookList(myBookList);

            dummyBookList = new ArrayList<>();
            user = userList.get(1);
            myBookList = user.getBorrowedBookList();
            book = bookList.get(2);
            dummyBookList.add(book);
            dummyBookList.getFirst().setBorrowedBy(user);
            dummyBookList.getFirst().setBorrowed(true);
            dummyBookList.getFirst().setBorrowedDate(LocalDate.of(2024, 6, 15));
            myBookList.add(book);
            user.setBorrowedBookList(myBookList);
        }
    }
}
