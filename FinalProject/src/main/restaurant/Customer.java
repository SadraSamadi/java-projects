package main.restaurant;

// TODO: Auto-generated Javadoc
/**
 * The Class Customer.
 */
public class Customer {

	/** The id. */
	private int id;

	/** The nationality. */
	private char nationality;

	/** The food type. */
	private char foodType;

	/** The menu type. */
	private char menuType;

	/** The money. */
	private int money;

	/** The vote. */
	private int vote;

	/** The menu. */
	private Menu menu;

	/**
	 * Instantiates a new customer.
	 *
	 * @param nationality
	 *            the nationality
	 * @param id
	 *            the id
	 * @param foodType
	 *            the food type
	 * @param menuType
	 *            the menu type
	 * @param money
	 *            the money
	 * @param vote
	 *            the vote
	 */
	public Customer(char nationality, int id, char foodType, char menuType, int money, int vote) {
		this.nationality = nationality;
		this.id = id;
		this.foodType = foodType;
		this.menuType = menuType;
		this.money = money;
		this.vote = vote;
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
	 * Gets the food type.
	 *
	 * @return the food type
	 */
	public char getFoodType() {
		return foodType;
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
	 * Gets the money.
	 *
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Gets the vote.
	 *
	 * @return the vote
	 */
	public int getVote() {
		return vote;
	}

	/**
	 * Gets the menu.
	 *
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * Sets the menu.
	 *
	 * @param menu
	 *            the new menu
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/**
	 * Sets the vote.
	 *
	 * @param vote
	 *            the new vote
	 */
	public void setVote(int vote) {
		this.vote = vote;
	}

	/**
	 * Sad.
	 *
	 * @return true, if successful
	 */
	public boolean sad() {
		return menu.getFoods().length == 0;
	}

	/**
	 * Leaving.
	 *
	 * @return the int
	 */
	public int leaving() {
		if (getMenuType() == getNationality()) {
			getFood().setQuality(getVote());
		} else {
			getFood().setInternationalQuality(getVote());
		}
		int off = Restaurant.customersId.contains(id) ? 1 : 0;
		return money - 3 - off;
	}

	/**
	 * Gets the food.
	 *
	 * @return the food
	 */
	public Food getFood() {
		if (menu.getFoods().length != 0)
			return menu.getFoods()[0];
		else
			return null;
	}

	/**
	 * Gets the menu type.
	 *
	 * @return the menu type
	 */
	public char getMenuType() {
		return menuType;
	}

}
