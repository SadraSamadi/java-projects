import datastructer.BinarySearchTree;

import java.util.Scanner;

/**
 * The Class Main.
 */
public class Main {

	/**
	 * Instantiates a new main.
	 */
	private Main() {
		System.out.println(".:: Binary Search Tree ::.");
		System.out.println("=========================================================================================");
		Scanner scanner = new Scanner(System.in);
		int[] numbers;
		while (true) {
			System.out.println("Please enter your numbers (separate by \',\') : ");
			try {
				numbers = getNumbers(scanner.nextLine().replaceAll("\\s+", ""));
				break;
			} catch (Exception e) {
				System.out.println("Invalid input !!! Try again ...");
			}
		}
		scanner.close();
		System.out.println("=========================================================================================");
		BinarySearchTree bst = new BinarySearchTree();
		for (int number : numbers)
			bst.insert(number);
		System.out.println("Inorder :");
		System.out.println(bst.inorder());
	}

	/**
	 * Gets the numbers.
	 *
	 * @param input
	 *            the input
	 * @return the numbers
	 */
	private int[] getNumbers(String input) {
		String[] data = input.split(",");
		int[] numbers = new int[data.length];
		for (int i = 0; i < numbers.length; i++)
			numbers[i] = Integer.parseInt(data[i]);
		return numbers;
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
