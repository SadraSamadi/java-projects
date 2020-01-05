package main.restaurant;

// TODO: Auto-generated Javadoc
/**
 * The Enum Nationality.
 */
public enum Nationality {

	/** The china. */
	CHINA(0, 'c'), /** The italian. */
	ITALIAN(1, 'i'), /** The french. */
	FRENCH(2, 'f'), /** The indian. */
	INDIAN(3, 'h'), /** The iranian. */
	IRANIAN(4, 'p'), /** The english. */
	ENGLISH(5, 'e');

	/** The index. */
	private int index;

	/** The ch. */
	private char ch;

	/**
	 * Instantiates a new nationality.
	 *
	 * @param index
	 *            the index
	 * @param ch
	 *            the ch
	 */
	Nationality(int index, char ch) {
		this.index = index;
		this.ch = ch;
	}

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the char.
	 *
	 * @return the char
	 */
	public char getChar() {
		return ch;
	}

	/**
	 * Gets the by char.
	 *
	 * @param ch
	 *            the ch
	 * @return the by char
	 */
	public static Nationality getByChar(char ch) {
		for (Nationality n : values())
			if (n.getChar() == ch)
				return n;
		return null;
	}

	/**
	 * Gets the by index.
	 *
	 * @param index
	 *            the index
	 * @return the by index
	 */
	public static Nationality getByIndex(int index) {
		for (Nationality n : values())
			if (n.getIndex() == index)
				return n;
		return null;
	}

}
