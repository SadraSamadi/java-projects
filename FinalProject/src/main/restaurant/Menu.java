package main.restaurant;

// TODO: Auto-generated Javadoc
/**
 * The Class Menu.
 */
public class Menu {

	/** The foods. */
	private Food[] foods;

	/** The customer. */
	private Customer customer;

	/**
	 * Instantiates a new menu.
	 *
	 * @param foods
	 *            the foods
	 * @param customer
	 *            the customer
	 */
	public Menu(Food[] foods, Customer customer) {
		this.foods = foods;
		this.customer = customer;
	}

	/**
	 * Gets the foods.
	 *
	 * @return the foods
	 */
	public Food[] getFoods() {
		return foods;
	}

	/**
	 * Gets the customer.
	 *
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Sets the customer.
	 *
	 * @param customer
	 *            the new customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
