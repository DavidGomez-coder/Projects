package demos.stack;

import dataStructures.stack.*;

public class Reverser {

	public static void main(String[] args) {

		String message = "hello world";
		Stack<Character> s = new ArrayStack<>();

		for (int i = 0; i < message.length(); i++) {
			s.push(message.charAt(i));
		}

		System.out.println(s);

		while (!s.isEmpty()) {
			System.out.print(s.top());
			s.pop();
		}
	}
}
