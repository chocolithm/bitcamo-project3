package bitcamp.project3.command.library;

import bitcamp.project3.command.Command;
import bitcamp.util.Prompt;

public class LibraryShowGuideCommand implements Command {

    public LibraryShowGuideCommand() {
    }

    @Override
    public void execute(String menuName) {
        System.out.printf("[%s]\n", menuName);

        String str =
            "안녕하세요! \uD83D\uDE0A 대출관리시스템에 오신 것을 환영합니다!\n" +
            "신청 방법: 로그인 후 ‘도서 대출’ 메뉴에서 쉽게 대여 하세요!\n" +
            "반납 기한: 대출일로부터 14일 이내에 반납해주세요.\n" + "" +
            "Tip: 보고 싶은 도서는 미리 예약해두면 더욱 편리해요!\n"+
            "언제나 여러분의 독서를 응원합니다!\n" +
            "궁금한 점이 있으면 언제든지 고객센터(1234-5678)로 연락해 주세요";

        System.out.println(str);
        Prompt.input("(엔터 입력 시 종료)");
    }
}
