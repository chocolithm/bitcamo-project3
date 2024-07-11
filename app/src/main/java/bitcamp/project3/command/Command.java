package bitcamp.project3.command;

import java.util.Stack;

public interface Command {

  void execute(Stack<String> menuPath);
}
