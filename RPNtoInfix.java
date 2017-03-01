package yourpackage;

import java.util.Stack;

public class RPNtoInfix {

	private static class Expression {
		private static final String operators = "+-*/";
		private static final String precedence = "0011";

		String expression;
		int prec;

		Expression(String ex) {
			expression = ex;
			prec = Integer.MAX_VALUE;
		}

		Expression(String ex1, String ex2, String op) {
			expression = ex1 + " " + op + " " + ex2;
			prec = getPrecedence(op);
		}

		@Override
		public String toString() {
			return expression;
		}
	}

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

	public static String convert(String expression) {

		index = 0;
		expr = expression;
		Stack<Expression> stack = new Stack<Expression>();

		while (!isFinished()) {
			String token = getNextToken();

			if (isOperator(token)) {
				Expression right = stack.pop();
				Expression left = stack.pop();

				if (left.prec < getPrecedence(token))
					left.expression = "(" + left.expression + ")";

				if (right.prec < getPrecedence(token))
					right.expression = "(" + right.expression + ")";

				stack.push(new Expression(left.expression, right.expression, token));
			} else {
				stack.push(new Expression(token));
			}

		}

		return stack.pop().expression;
	}

	private static int getPrecedence(String str) {
		int index = Expression.operators.indexOf(str);
		if (index != -1) {
			return Integer.parseInt(Expression.precedence.substring(index, index + 1));
		}
		return -1;
	}

	private static boolean isOperator(String str) {
		return (Expression.operators.indexOf(str) != -1);
	}

}
