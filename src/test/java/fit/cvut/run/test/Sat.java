package fit.cvut.run.test;

import java.util.Stack;

public class Sat {
	final static int AND = 1;
	final static int OR = 2;
	final static int NOT = 3;

	/**
	 * Input is in postfix notation with a-z variables without gaps.
	 * @param args
	 * @throws Exception
	 */
	public static String main(String[] args) throws Exception {
		String sat = "abc!d&|&";
		Expression e = buildTree(sat);
		boolean[] solution = solve(e, variableCount(sat));
		String result = "";
		if (solution != null) {
			for (int i = 0; i < solution.length; i++) {
				result += (char) (97 + i);
			}
			result += "\n";
			for (int i = 0; i < solution.length; i++) {
				result += (solution[i] ? '1' : '0');
			}
		}
		
		return result;
	}

	public static boolean[] solve(Expression e, int variableCount) {
		for (int instance = 0; instance < variableCount * variableCount; instance++) {
			boolean[] bits = new boolean[variableCount];
			for (int j = variableCount - 1; j >= 0; j--) {
				bits[j] = (instance & (1 << j)) != 0;
			}
			if (evaluate(e, bits)) {
				return bits;
			}
		}
		return null;
	}

	public static int variableCount(String sat) {
		boolean[] a = new boolean[25];
		for (int i = 0; i < sat.length(); i++) {
			char letter = sat.charAt(i);
			if (Character.isLetter(letter)) {
				a[letter - 97] = true;
			}
		}
		int counter = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i]) counter++;
		}
		return counter;
	}

	public static Expression buildTree(String sat) {
		Stack<Expression> stack = new Stack<Expression>();

		for (int i = 0; i < sat.length(); i++) {
			char c = sat.charAt(i);
			if (c == ' ') {
				continue;
			}

			if (Character.isLetter(c)) {
				Expression e = new Expression();
				e.name = c;
				stack.push(e);
			} else {
				if(c == '&' || c == '|'){
						Expression e = new Expression();
						e.operator = c == '&' ? AND : OR;
						e.right = stack.pop();
						e.left = stack.pop();
						e.name = 0;
						stack.push(e);
				}else if (c == '!'){
						Expression e = new Expression();
						e.operator = NOT;
						e.left = stack.pop();
						e.name = 0;
						stack.push(e);
				}
			}
		}

		return stack.pop();
	}
	public static boolean evaluate(Expression e, boolean[] instance){
		if (e.name > 0) {
			return instance[e.name - 97];
		}
		if (e.operator == Sat.NOT) {
			return !evaluate(e.left, instance);
		}
		if (e.operator == Sat.AND) {
			return evaluate(e.left, instance) && evaluate(e.right, instance);
		}
		if (e.operator == Sat.OR) {
			return evaluate(e.left , instance) || evaluate(e.right, instance);
		}
		return false;		
	}
}

class Expression {
	char name ;
	Expression left;
	Expression right;
	int operator;
	@Override
	public String toString() {
		if (name > 0) {
			return Character.toString(name);
		}
		if (operator == Sat.NOT) {
			return "!" + left.toString();
		}
		return "(" + left.toString() + (operator == Sat.AND ? " & " : " | ") + right.toString() + ")";
	}
}




