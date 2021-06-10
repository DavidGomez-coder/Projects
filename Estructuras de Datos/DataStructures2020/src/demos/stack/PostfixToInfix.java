/**
 * @author Pablo López, Data Structures, Grado en Informática. UMA.
 *
 * Translates from postfix notation to infix notation using a stack.
 * The resulting infix expression is fully parenthesized except
 * for atomic expressions (i.e. numbers).
 */
package demos.stack;

import dataStructures.stack.*;

public class PostfixToInfix {

	public static void main(String[] args) {

		String[] exp1 = { "563", "13", "6", "+", "83", "*", "-" }; // OK
		String[] exp2 = { "2", "3", "+" }; // OK
		String[] exp3 = { "312" }; // OK
		String[] exp4 = { "34", "81", "+", "*" }; // missing operand
		String[] exp5 = { "73", "10", "-", "81" }; // missing operator

		// choose expression to test
		String[] exp = exp1;

		Stack<String> s = new LinkedStack<>();
		boolean missingOperand = false;

		for (int i= 0; i < exp.length; i++) {
			if (Character.isDigit(exp[i].charAt(0))) {
				s.push(exp[i]);
			} else {
				// it is an operator
				try {
					String op2 = s.top();
					s.pop();
					String op1 = s.top();
					s.pop();
					s.push(parenthezise(op1) + exp[i] + parenthezise(op2));
				} catch (EmptyStackException e) {
					missingOperand = true;
					System.out.println("wrong postfix expression: missing operand");
				}
			}
		}

		if (!missingOperand) {
			String result = s.top(); // cannot be empty; the loop pushes on s
			s.pop();
			if (s.isEmpty()) // must be empty
				System.out.println(result);
			else
				System.out.println("wrong postfix expression: missing operator");
		}
	}

	private static boolean hasOperators(String s) {
		// what I really want:
		
		// not $ null $ filter (`elem` "+-*") s

		// what I get is the inefficient:

		// return s.indexOf('+') != -1 ||
		//        s.indexOf('-') != -1 ||
		//        s.indexOf('*') != -1;

		// or the ugly, overspecified:
		
		boolean hasOps = false;
		for (int i = 0; i < s.length() && !hasOps; i++) {
			char c = s.charAt(i);
			hasOps = c == '+' || c == '-' || c == '*';
		}
		return hasOps;
	}

	private static String parenthezise(String s) {
		if (hasOperators(s))
			return "(" + s + ")";
		else
			return s;
	}
}
