# 미니프로젝트
- 주제 : 도서대출 시스템
  팀원 : 백현기, 강윤상
  발표 : 7/17 수 16시



# 클래스 구조
<Package util>
  <Class Prompt>


  <Class Login>
  User loginUser;
  login();
  logout();

  <Class Dummy>
  Dummydata


<Package vo>

  <Class User>
  String id;
  String pw;
  String name;
  boolean isAdmin;
  Date joinDate;
  List<Book> borrowedBookList;

  <Class Book>
  String name;
  String author;
  String category;
  boolean isBorrowed;
  boolean isReserved;
  String 예약한사람
  Date registerDate;
  Date borrowDate;
  Date returnDate;


<Package Command>

  <Class UserCommand>
  List<User> userList;
  ---------------------
  addUser()
  deleteUser()

  <Class BookCommand>
  List<Book> bookList;
  ---------------------
  CRUD

  <Class LibraryCommand>
  searchBook(String title)  //검색
  searchBook(Date date)  //검색
  borrowBook(User loginUser, Book book)  //대출
  returnBook(User loginUser, Book book)  //반납
  showStatus(User user)  //대출현황
  showGuide()  //이용안내


# 화면예시

-----------------------------------------------------

## 로그인
1. 로그인  //Login -> login()
2. 회원가입  //UserCommand -> addUser()
0. 종료

-----------------------------------------------------

## 메인
[연체 중인 도서가 있을 시 알림 표출]
1. 도서대출  //LibraryCommand -> searchBook(title)
2. 도서반납  //LibraryCommand -> returnBook()
2. 신간도서  //LibraryCommand -> searchBook(date)
3. 대출현황  //LibraryCommand -> showStatus()
4. 이용안내  //LibraryCommand -> showGuide()
0. 로그아웃  //Login -> logout()

## 관리자 화면
1. 사용자관리  //UserCommand
2. 도서관리  //BookCommand
<!-- 3. 대출기록   -->
0. 로그아웃  //Login -> logout()

-----------------------------------------------------

## 메인 > 도서검색
도서 제목? <<제목>>

번호    분류    도서명          대출여부
1       과학    인터스텔라      대출가능
2       인문    군주론          대출중
3       인문    군주론          예약중

도서 번호(0 이전)? <<번호>>
대출되었습니다. / 대출 중인 도서입니다. 예약하시겠습니까? / 대출이 불가한 도서입니다.


## 메인 > 도서반납
번호    분류    도서명        대출일      반납예정일      상태
1       과학    인터스텔라    2024-07-11  2024-07-25      정상
2       인문    군주론        2024-06-11  2024-06-25      연체

도서 번호(0 이전)? <<번호>>
'인터스텔라'를 연장하시겠습니까? y/n
y : 연장되었습니다. / n : 반납하였습니다.


## 메인 > 대출현황
분류    도서명        대출일      반납예정일      상태
과학    인터스텔라    2024-07-11  2024-07-25      정상
인문    군주론        2024-06-11  2024-06-25      연체


## 메인 > 신간도서
번호    등록일      분류    제목            대출여부
1       2024-06-11  인문    군주론          대출중
2       2024-07-10  과학    인터스텔라      대출가능

대출하시겠습니까? <<y/n>>
도서 번호? <<번호>>
대출되었습니다. / 대출이 불가한 도서입니다.


## 메인 > 이용안내
~~~~
~~~~

-----------------------------------------------------

## 관리자 > 사용자관리
1. 목록
2. 수정
3. 삭제
0. 이전

## 관리자 > 도서관리
1. 등록
2. 목록
3. 수정
4. 삭제
0. 이전

## 관리자 > 대출기록
번호    사용자    도서명       대출일      반납일      상태
1       윤상      인터스텔라1  2024-07-11  2024-07-25  정상
2       윤상      인터스텔라2  2024-07-11  2024-07-25  연체
3       윤상      인터스텔라3  2024-07-11  2024-07-11  반납