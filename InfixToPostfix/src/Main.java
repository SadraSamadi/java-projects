import java.util.Scanner;
import java.util.Stack;

/**
 * The Class Main.
 */
public class Main {

	/**
	 * Instantiates a new main.
	 */
	private Main() {
		String infix;
		String postfix;
		double result;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Please enter infix expression :");
			infix = scanner.nextLine();
			try {
				postfix = infixToPostfix(infix.replaceAll("\\s+", ""));
				result = evaluate(postfix);
				break;
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		scanner.close();
		System.out.println("=========================================================================================");
		System.out.println("Infix expression : " + infix);
		System.out.println("Postfix expression : " + postfix);
		System.out.println("Result = " + result);
	}

	/**
	 * Infix to postfix.
	 *
	 * @param infix
	 *            the infix
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	private String infixToPostfix(String infix) throws Exception {
		String postfix = "";
		Stack<Character> stack = new Stack<>();
		for (char c : infix.toCharArray()) {
			if (Character.isDigit(c)) {
				postfix += c;
			} else if (c == '(') {
				stack.push(c);
			} else if (c == ')') {
				while (!stack.isEmpty() && (stack.peek() != '('))
					postfix += stack.pop();
				if (!stack.isEmpty() && (stack.peek() != '('))
					throw new Exception("Invalid expression !");
				else
					stack.pop();
			} else if (isOperator(c)) {
				while (!stack.isEmpty() && (stack.peek() != '(') && (priorityOf(c) <= priorityOf(stack.peek())))
					postfix += stack.pop();
				stack.push(c);
			} else {
				throw new Exception("Invalid expression !");
			}
		}
		while (!stack.isEmpty())
			postfix += stack.pop();
		return postfix;
	}

	/**
	 * Evaluate.
	 *
	 * @param postfix
	 *            the postfix
	 * @return the double
	 * @throws Exception
	 *             the exception
	 */
	private double evaluate(String postfix) throws Exception {
		Stack<Double> stack = new Stack<>();
		for (char c : postfix.toCharArray()) {
			if (Character.isDigit(c)) {
				stack.push(Double.parseDouble(String.valueOf(c)));
			} else if (isOperator(c)) {
				if (stack.size() < 2)
					throw new Exception("Error !");
				double a = stack.pop();
				double b = stack.pop();
				stack.push(calculate(b, c, a));
			}
		}
		if (stack.size() != 1)
			throw new Exception("Error !");
		return stack.pop();
	}

	/**
	 * Calculate.
	 *
	 * @param a
	 *            the a
	 * @param operator
	 *            the operator
	 * @param b
	 *            the b
	 * @return the double
	 * @throws Exception
	 *             the exception
	 */
	private Double calculate(double a, char operator, double b) throws Exception {
		switch (operator) {
		case '-':
			return a - b;
		case '+':
			return a + b;
		case '%':
			return a % b;
		case '/':
			return a / b;
		case '*':
			return a * b;
		case '^':
			return Math.pow(a, b);
		default:
			throw new Exception("Invalid operator !");
		}
	}

	/**
	 * Priority of.
	 *
	 * @param operator
	 *            the operator
	 * @return the int
	 * @throws Exception
	 *             the exception
	 */
	private int priorityOf(char operator) throws Exception {
		switch (operator) {
		case '-':
		case '+':
			return 1;
		case '%':
		case '/':
		case '*':
			return 2;
		case '^':
			return 2;
		default:
			throw new Exception("Invalid operator !");
		}
	}

	/**
	 * Checks if is operator.
	 *
	 * @param c
	 *            the c
	 * @return true, if is operator
	 */
	private boolean isOperator(char c) {
		switch (c) {
		case '-':
		case '+':
		case '%':
		case '/':
		case '*':
		case '^':
			return true;
		default:
			return false;
		}
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		new Main();
	}

}
