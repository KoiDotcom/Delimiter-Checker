import java.util.Scanner;
import java.util.Stack;

public class DelimiterChecker {

    private static char getOpening(char closing) {
        switch (closing) {
            case ')': return '(';
            case ']': return '[';
            case '}': return '{';
            default:  return '\0';
        }
    }

    private static boolean isOpening(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    private static boolean isClosing(char c) {
        return c == ')' || c == ']' || c == '}';
    }

    // Builds a string like "{ [ (" from the stack, bottom to top
    private static String stackToString(Stack<Character> stack) {
        if (stack.isEmpty()) {
            return "(empty)";
        }
        StringBuilder sb = new StringBuilder();
        for (char c : stack) {
            sb.append(c).append(" ");
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a sequence of delimiters: ");
        String sequence = input.nextLine();
        System.out.println();
        System.out.println("Input: " + sequence);
        System.out.println();

        Stack<Character> stack = new Stack<>();
        boolean valid = true;
        String reason = "";

        for (int i = 0; i < sequence.length(); i++) {
            char ch = sequence.charAt(i);

            if (isOpening(ch)) {
                System.out.println("Read: " + ch);
                stack.push(ch);
                System.out.println("Operation: PUSH → " + ch);
                System.out.println("Stack: " + stackToString(stack));
                System.out.println();

            } else if (isClosing(ch)) {
                System.out.println("Read: " + ch);

                // Check whether the stack is empty
                if (stack.isEmpty()) {
                    valid = false;
                    reason = "Closing delimiter '" + ch + "' at position " + (i + 1)
                            + " has no matching opening delimiter.";
                    System.out.println("Operation: stack is empty, nothing to PEEK");
                    System.out.println("Stack: " + stackToString(stack));
                    System.out.println("Problem: " + reason);
                    System.out.println();
                    break;
                }

                // Peek at the top
                char top = stack.peek();
                System.out.println("Operation: PEEK → " + top);

                // Check for a match
                if (top != getOpening(ch)) {
                    valid = false;
                    reason = "Mismatched delimiters: '" + top + "' cannot be closed by '"
                            + ch + "' at position " + (i + 1) + ".";
                    System.out.println("Stack: " + stackToString(stack));
                    System.out.println("Problem: " + reason);
                    System.out.println();
                    break;
                }

                // Pop the matching opener
                stack.pop();
                System.out.println("Operation: POP → " + top);
                System.out.println("Stack: " + stackToString(stack));
                System.out.println();
            }
            // Other characters (like spaces) are ignored
        }

        // Check for leftover openers
        if (valid && !stack.isEmpty()) {
            valid = false;
            reason = "Unmatched opening delimiter remains: '" + stack.peek() + "'.";
            System.out.println("End of input. Stack: " + stackToString(stack));
            System.out.println("Problem: " + reason);
            System.out.println();
        }

        // Final result
        if (valid) {
            System.out.println("Result: Valid");
        } else {
            System.out.println("Result: Invalid");
            System.out.println("Reason: " + reason);
        }

        input.close();
    }
}

/* Description: The program will accept specifically the openers and the closers, [{()}], once inputted, it will check if that opener in the input
has a matching closer, if it doesnt, then it will return invalid, otherwise valid.
Programmed by: Sheire Nyle Cuevas, BSIT, 48079, Data Structure And Algorithms
Last Modified: October 28, 2026
Version: 1
[Acknowledgements: I used Claude.ai to help me make a plan on where to start, as well as some debugging.*/