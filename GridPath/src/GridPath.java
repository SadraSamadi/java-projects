import java.util.Scanner;

/**
 * The Class GridPath.
 */
public class GridPath {

	/**
	 * Instantiates a new grid path.
	 */
	private GridPath() {
		Scanner scanner = new Scanner(System.in);
		int m, n;
		do {
			System.out.println("Please enter the coordinates :");
			System.out.print("m = ");
			m = scanner.nextInt();
			System.out.print("n = ");
			n = scanner.nextInt();
		} while (!isValidInput(m, n));
		scanner.close();
		System.out.println("=========================================================================================");
		System.out.println("The number of paths = " + allPaths(m, n, ""));
	}

	/**
	 * All paths.
	 *
	 * @param m
	 *            the m
	 * @param n
	 *            the n
	 * @param path
	 *            the path
	 * @return the int
	 */
	private int allPaths(int m, int n, String path) {
		path = String.format("(%d, %d) %s", m, n, path);
		if (m == 1 && n == 1) {
			System.out.println(path);
			return 1;
		}
		int cnt;
		if (m == 1)
			cnt = allPaths(m, n - 1, path);
		else if (n == 1)
			cnt = allPaths(m - 1, n, path);
		else
			cnt = allPaths(m - 1, n, path) + allPaths(m, n - 1, path) + allPaths(m - 1, n - 1, path);
		return cnt;
	}

	/**
	 * Checks if is valid input.
	 *
	 * @param m
	 *            the m
	 * @param n
	 *            the n
	 * @return true, if is valid input
	 */
	private boolean isValidInput(int m, int n) {
		if (m < 1 || n < 1) {
			System.out.println("Invalid input ! The values most be greater than zero ...");
			return false;
		}
		return true;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		new GridPath();
	}

}