package bitcamp.project3.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prompt {

  static Scanner keyboardScanner = new Scanner(System.in);
  static Queue<String> inputQueue = new LinkedList<>();

  public static String input(String format, Object... args) {
    String promptTitle = String.format(format + " ", args);
    System.out.print(promptTitle);

    String input = keyboardScanner.nextLine();
    if (format.endsWith(">")) {
      inputQueue.offer(promptTitle + input); // 최근 명령어를 큐의 맨 뒤에 넣는다.
      if (inputQueue.size() > 20) {
        inputQueue.poll(); // 가장 오래된 값을 큐에서 꺼낸다.
      }
    }
    return input;
  }

  public static int inputInt(String format, Object... args) {
    return Integer.parseInt(input(format, args));
  }

  public static void close() {
    keyboardScanner.close();
  }

  public static void loading(long time){
    long sz = time/1000;
    try {
      for(int i=0;i<sz;i++) {
        Thread.sleep(time/sz);
      }
      System.out.print("\n");
    } catch (InterruptedException e) {
      // e.printStackTrace();
    }
  }

  public static void printBuff() {
    for(int i = 0; i < 55; i++) {
      System.out.println();
    }
  }

  public static void printLogout() {
    System.out.println("로그아웃합니다.");
    loading(1000);
    printBuff();
  }

  public static void printLoginException() {
    System.out.println("로그인 정보를 확인하세요.");
    loading(1000);
    printBuff();
  }

  public static void printNumberException() {
    System.out.println("유효한 숫자를 입력해주세요.");
    loading(1000);
    printBuff();
  }

  public static void printProgramExit() {
    System.out.println("시스템을 종료합니다.");
    loading(1000);
    close();
  }

  public static void printAddComplete() {
    System.out.println("등록됐습니다.");
    loading(1000);
    printBuff();
  }

  public static void printUpdateComplete() {
    System.out.println("수정됐습니다.");
    loading(1000);
    printBuff();
  }

  public static void printDeleteComplete(String name, String type) {
    System.out.printf("'%s' %s을 삭제 했습니다.\n", name, type);
    loading(1000);
    printBuff();
  }

  public static String getSpaces(int length, String str) {
    int count = 0;
    count += str.length();

    Pattern pattern = Pattern.compile("[가-힣]");
    Matcher matcher = pattern.matcher(str);
    while (matcher.find()) {
      count++;
    }

    return " ".repeat(length - count);
  }
}
