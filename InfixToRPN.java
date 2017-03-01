package uk.ac.cam.si310.supervision3;

import java.util.ArrayList;
import java.util.Stack;

public class InfixToRPN {

	private static String operators = "+-*/";
	private static String precedence = "0011";

	private static int index;
	private static String expr;

	private static String getNextNumber() {
		String num = "";

		while (index < expr.length() && Character.isDigit(expr.charAt(index))) {
			num += expr.charAt(index);
			index++;
		}
		return num;
	}

	private static void removeWhitespace() {
		while (index < expr.length() && expr.charAt(index) == ' ')
			index++;
	}

	private static String getNextToken() {
		String token = "";

		removeWhitespace();

		if (index < expr.length()) {
			if (Character.isDigit(expr.charAt(index)))
				token = getNextNumber();
			else {
				token += expr.charAt(index);
				index++;
			}

		}
		return token;
	}

	private static boolean isFinished() {
		return index >= expr.length();
	}

	public static String[] convert(String expression) {

		index = 0;
		expr = expression;

		ArrayList<String> output = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();

		// String[] tokens = expression.split(" ");

		while (!isFinished()) {
			String token = getNextToken();

			if (isOperator(token)) {
				while (!stack.empty() && isOperator(stack.peek())
						&& getPrecedence(token) <= getPrecedence(stack.peek())) {
					output.add(stack.pop());
				}

				stack.push(token);
			} else if (token.equals("(")) {
				stack.push(token);
			} else if (token.equals(")")) {
				while (!stack.empty() && !(stack.peek().equals("("))) {
					output.add(stack.pop());
				}
				stack.pop();
			} else
				output.add(token);

		}

		// for (String token : tokens) {
		//
		// if (isOperator(token)) {
		// while (!stack.empty() && isOperator(stack.peek())
		// && getPrecedence(token) <= getPrecedence(stack.peek())) {
		// output.add(stack.pop());
		// }
		//
		// stack.push(token);
		// } else if (token.equals("(")) {
		// stack.push(token);
		// } else if (token.equals(")")) {
		// while (!stack.empty() && !(stack.peek().equals("("))) {
		// output.add(stack.pop());
		// }
		// stack.pop();
		// } else
		// output.add(token);
		// }

		while (!stack.empty())
			output.add(stack.pop());

		return output.toArray(new String[output.size()]);
	}

	private static int getPrecedence(String str) {
		int index = operators.indexOf(str);
		if (index != -1) {
			return Integer.parseInt(precedence.substring(index, index + 1));
		}
		return -1;
	}

	private static boolean isOperator(String str) {
		return (operators.indexOf(str) != -1);
	}

}
