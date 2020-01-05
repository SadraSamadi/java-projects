package main.restaurant;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Restaurant.
 */
public class Restaurant {

	/** The name. */
	private String name;

	/** The capacity. */
	private int capacity;

	/** The fund. */
	private int fund;

	/** The foods. */
	private final Food[] FOODS = new Food[100];

	/** The waiters. */
	private final Waiter[] WAITERS = new Waiter[5];

	/** The customers. */
	private Vector<Customer> customers;

	/** The customers id. */
	public static Set<Integer> customersId = new HashSet<>();

	/** The customer id. */
	private int customerId;

	/**
	 * Instantiates a new restaurant.
	 *
	 * @param name
	 *            the name
	 * @param capacity
	 *            the capacity
	 */
	public Restaurant(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
		customers = new Vector<>(capacity);
		initFoods();
		initWaiters();
	}

	/**
	 * Instantiates a new restaurant.
	 *
	 * @param name
	 *            the name
	 */
	public Restaurant(String name) {
		this(name, 50);
	}

	/**
	 * Inits the foods.
	 */
	private void initFoods() {
		for (int i = 0; i < 20; i++) {
			int quality = i < 5 ? 10 : i < 10 ? 7 : i < 15 ? 5 : 8;
			char foodType = i < 15 ? Food.NON_VEGETARIAN : Food.VEGETARIAN;
			FOODS[i] = new Food(i + 1, quality, quality, Nationality.CHINA.getChar(), foodType);
			FOODS[i + 20] = new Food(i + 21, quality, quality, Nationality.ITALIAN.getChar(), foodType);
			FOODS[i + 40] = new Food(i + 41, quality, quality, Nationality.FRENCH.getChar(), foodType);
			FOODS[i + 60] = new Food(i + 61, quality, quality, Nationality.INDIAN.getChar(), foodType);
			FOODS[i + 80] = new Food(i + 81, quality, quality, Nationality.IRANIAN.getChar(), foodType);
		}
	}

	/**
	 * Inits the waiters.
	 */
	private void initWaiters() {
		WAITERS[0] = new Waiter(1, Nationality.CHINA.getChar());
		WAITERS[1] = new Waiter(2, Nationality.ITALIAN.getChar());
		WAITERS[2] = new Waiter(3, Nationality.FRENCH.getChar());
		WAITERS[3] = new Waiter(4, Nationality.INDIAN.getChar());
		WAITERS[4] = new Waiter(5, Nationality.IRANIAN.getChar());
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the fund.
	 *
	 * @return the fund
	 */
	public int getFund() {
		return fund;
	}

	/**
	 * Increment fund.
	 *
	 * @param fund
	 *            the fund
	 */
	public void incrementFund(int fund) {
		this.fund += fund;
	}

	/**
	 * Gets the capacity.
	 *
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Gets the foods.
	 *
	 * @return the foods
	 */
	public Food[] getFoods() {
		return FOODS;
	}

	/**
	 * Gets the customers.
	 *
	 * @return the customers
	 */
	public Customer[] getCustomers() {
		return customers.toArray(new Customer[customers.size()]);
	}

	/**
	 * Gets the waiters.
	 *
	 * @return the waiters
	 */
	public Waiter[] getWaiters() {
		return WAITERS;
	}

	/**
	 * Sets the capacity.
	 *
	 * @param capacity
	 *            the new capacity
	 */
	public void setCapacity(int capacity) {
		if (capacity > 0) {
			this.capacity = capacity;
			customers.setSize(capacity);
		}
	}

	/**
	 * Cooking.
	 *
	 * @param nationality
	 *            the nationality
	 */
	public void cooking(char nationality) {
		ArrayList<Food> foods = new ArrayList<>();
		for (Food food : FOODS) {
			if (food.getNationality() == nationality && food.getNumber() == 0)
				foods.add(food);
		}
		if (foods.size() == 20) {
			foods.forEach(food -> food.setNumber(10)); // Regular Expression
		}
	}

	/**
	 * Settlement.
	 *
	 * @param customer
	 *            the customer
	 */
	public void settlement(Customer customer) {
		if (customers.contains(customer)) {
			customersId.add(customer.getId());
			customers.remove(customer);
		}
	}

	/**
	 * Customer entry.
	 *
	 * @param nationality
	 *            the nationality
	 * @param foodType
	 *            the food type
	 * @param menuType
	 *            the menu type
	 * @param money
	 *            the money
	 * @param vote
	 *            the vote
	 */
	public void customerEntry(char nationality, char foodType, char menuType, int money, int vote) {
		if (customers.size() < capacity) {
			Customer customer = new Customer(nationality, ++customerId, foodType, menuType, money, vote);
			customer.setMenu(new Menu(getFoodsFor(customer.getMenuType(), customer.getFoodType()), customer));
			if (!customer.sad()) {
				customers.add(customer);
				customer.getFood().setNumber(customer.getFood().getNumber() - 1);
				Nationality nation = Nationality.getByChar(menuType);
				if (nation != null) {
					Waiter waiter = WAITERS[nation.getIndex()];
					waiter.incrementCustomers(1);
					waiter.setSalary(3);
				}
			}
		} else {
			int oneFifth = customers.size() / 5;
			for (int i = 0; i < oneFifth; i++) {
				Customer customer = customers.elementAt(i);
				incrementFund(customer.leaving());
				settlement(customer);
			}
			customerEntry(nationality, foodType, menuType, money, vote);
		}
	}

	/**
	 * Gets the foods for.
	 *
	 * @param menuType
	 *            the menu type
	 * @param foodType
	 *            the food type
	 * @return the foods for
	 */
	public Food[] getFoodsFor(char menuType, char foodType) {
		ArrayList<Food> foods = new ArrayList<>();
		for (Food food : FOODS) {
			if (food.getNationality() == menuType && !food.isFinished()
					&& (foodType == Food.NON_VEGETARIAN || food.getFoodType() == Food.VEGETARIAN)) {
				foods.add(food);
			}
		}
		// Regular Expression
		foods.sort((f1, f2) -> Integer.compare(f2.getQuality(), f1.getQuality()));
		return foods.toArray(new Food[foods.size()]);
	}

	/**
	 * Gets the customer by.
	 *
	 * @param id
	 *            the id
	 * @return the customer by
	 */
	public Customer getCustomerBy(int id) {
		for (Customer customer : customers) {
			if (customer.getId() == id)
				return customer;
		}
		return null;
	}

}
