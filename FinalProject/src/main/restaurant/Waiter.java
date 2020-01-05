package main.restaurant;

// TODO: Auto-generated Javadoc
/**
 * The Class Waiter.
 */
public class Waiter {

	/** The id. */
	private int id;

	/** The nationality. */
	private char nationality;

	/** The salary. */
	private int salary;

	/** The customers. */
	private int customers;

	/**
	 * Instantiates a new waiter.
	 *
	 * @param id
	 *            the id
	 * @param nationality
	 *            the nationality
	 */
	public Waiter(int id, char nationality) {
		this.id = id;
		this.nationality = nationality;
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
	 * Gets the nationality.
	 *
	 * @return the nationality
	 */
	public char getNationality() {
		return nationality;
	}

	/**
	 * Gets the salary.
	 *
	 * @return the salary
	 */
	public int getSalary() {
		return salary + 10 * Math.floorDiv(customers, 10);
	}

	/**
	 * Gets the customers.
	 *
	 * @return the customers
	 */
	public int getCustomers() {
		return customers;
	}

	/**
	 * Increment customers.
	 *
	 * @param customers
	 *            the customers
	 */
	public void incrementCustomers(int customers) {
		if (customers > 0)
			this.customers += customers;
	}

	/**
	 * Sets the salary.
	 *
	 * @param salary
	 *            the new salary
	 */
	public void setSalary(int salary) {
		if (salary > 0)
			this.salary += salary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id + ", " + nationality;
	}

}
