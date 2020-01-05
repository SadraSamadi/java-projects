package main.restaurant;

// TODO: Auto-generated Javadoc
/**
 * The Class Food.
 */
public class Food {

	/** The Constant VEGETARIAN. */
	public static final char VEGETARIAN = 'v';

	/** The Constant NON_VEGETARIAN. */
	public static final char NON_VEGETARIAN = 'n';

	/** The id. */
	private int id;

	/** The quality. */
	private int quality;

	/** The international quality. */
	private int internationalQuality;

	/** The nationality. */
	private char nationality;

	/** The food type. */
	private char foodType;

	/** The number. */
	private int number;

	/** The quality sum. */
	private int qualitySum;

	/** The quality counter. */
	private int qualityCounter = 1;

	/** The international quality sum. */
	private int internationalQualitySum;

	/** The international quality counter. */
	private int internationalQualityCounter = 1;

	/**
	 * Instantiates a new food.
	 *
	 * @param id
	 *            the id
	 * @param quality
	 *            the quality
	 * @param internationalQuality
	 *            the international quality
	 * @param nationality
	 *            the nationality
	 * @param foodType
	 *            the food type
	 */
	public Food(int id, int quality, int internationalQuality, char nationality, char foodType) {
		this.id = id;
		this.quality = quality;
		this.internationalQuality = internationalQuality;
		qualitySum = quality;
		internationalQualitySum = internationalQuality;
		this.nationality = nationality;
		this.foodType = foodType;
	}

	/**
	 * Gets the nationality.
	 *
	 * @return the nationality
	 */
	public char getNationality() {
		return nationality;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the quality.
	 *
	 * @return the quality
	 */
	public int getQuality() {
		return quality;
	}

	/**
	 * Gets the international quality.
	 *
	 * @return the international quality
	 */
	public int getInternationalQuality() {
		return internationalQuality;
	}

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Gets the food type.
	 *
	 * @return the food type
	 */
	public char getFoodType() {
		return foodType;
	}

	/**
	 * Sets the number.
	 *
	 * @param number
	 *            the new number
	 */
	public void setNumber(int number) {
		if (number >= 0)
			this.number = number;
	}

	/**
	 * Sets the quality.
	 *
	 * @param quality
	 *            the new quality
	 */
	public void setQuality(int quality) {
		this.quality = (qualitySum += quality) / ++qualityCounter;
	}

	/**
	 * Sets the international quality.
	 *
	 * @param internationalQuality
	 *            the new international quality
	 */
	public void setInternationalQuality(int internationalQuality) {
		this.internationalQuality = (internationalQualitySum += internationalQuality) / ++internationalQualityCounter;
	}

	/**
	 * Checks if is finished.
	 *
	 * @return true, if is finished
	 */
	public boolean isFinished() {
		return number == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Quality: " + quality + " | International Quality: " + internationalQuality + " | Nationality: "
				+ Nationality.getByChar(nationality) + " | Food Type: "
				+ (foodType == 'v' ? "Vegetarian" : "Non-Vegetarian");
	}

}
