package main.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.restaurant.Nationality;
import main.restaurant.Restaurant;
import main.restaurant.Waiter;

// TODO: Auto-generated Javadoc
/**
 * The Class WaiterController.
 */
public class WaiterController {

	/** The id col. */
	public TableColumn<Waiter, Integer> idCol;

	/** The nationality col. */
	public TableColumn<Waiter, String> nationalityCol;

	/** The salary col. */
	public TableColumn<Waiter, Integer> salaryCol;

	/** The customers col. */
	public TableColumn<Waiter, Integer> customersCol;

	/** The table. */
	public TableView<WaiterRow> table;

	/**
	 * Inits the.
	 *
	 * @param restaurant
	 *            the restaurant
	 */
	public void init(Restaurant restaurant) {
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		nationalityCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
		salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
		customersCol.setCellValueFactory(new PropertyValueFactory<>("customers"));
		ObservableList<WaiterRow> values = FXCollections.observableArrayList();
		for (Waiter waiter : restaurant.getWaiters()) {
			values.add(new WaiterRow(waiter));
		}
		table.setItems(values);
	}

	/**
	 * The Class WaiterRow.
	 */
	public class WaiterRow {

		/** The id. */
		private SimpleIntegerProperty id;

		/** The nationality. */
		private SimpleStringProperty nationality;

		/** The salary. */
		private SimpleIntegerProperty salary;

		/** The customers. */
		private SimpleIntegerProperty customers;

		/**
		 * Instantiates a new waiter row.
		 *
		 * @param waiter
		 *            the waiter
		 */
		public WaiterRow(Waiter waiter) {
			this.id = new SimpleIntegerProperty(waiter.getId());
			this.nationality = new SimpleStringProperty(Nationality.getByChar(waiter.getNationality()) + "");
			this.salary = new SimpleIntegerProperty(waiter.getSalary());
			this.customers = new SimpleIntegerProperty(waiter.getCustomers());
		}

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public int getId() {
			return id.get();
		}

		/**
		 * Id property.
		 *
		 * @return the simple integer property
		 */
		public SimpleIntegerProperty idProperty() {
			return id;
		}

		/**
		 * Sets the id.
		 *
		 * @param id
		 *            the new id
		 */
		public void setId(int id) {
			this.id.set(id);
		}

		/**
		 * Gets the nationality.
		 *
		 * @return the nationality
		 */
		public String getNationality() {
			return nationality.get();
		}

		/**
		 * Nationality property.
		 *
		 * @return the simple string property
		 */
		public SimpleStringProperty nationalityProperty() {
			return nationality;
		}

		/**
		 * Sets the nationality.
		 *
		 * @param nationality
		 *            the new nationality
		 */
		public void setNationality(String nationality) {
			this.nationality.set(nationality);
		}

		/**
		 * Gets the salary.
		 *
		 * @return the salary
		 */
		public int getSalary() {
			return salary.get();
		}

		/**
		 * Salary property.
		 *
		 * @return the simple integer property
		 */
		public SimpleIntegerProperty salaryProperty() {
			return salary;
		}

		/**
		 * Sets the salary.
		 *
		 * @param salary
		 *            the new salary
		 */
		public void setSalary(int salary) {
			this.salary.set(salary);
		}

		/**
		 * Gets the customers.
		 *
		 * @return the customers
		 */
		public int getCustomers() {
			return customers.get();
		}

		/**
		 * Customers property.
		 *
		 * @return the simple integer property
		 */
		public SimpleIntegerProperty customersProperty() {
			return customers;
		}

		/**
		 * Sets the customers.
		 *
		 * @param customers
		 *            the new customers
		 */
		public void setCustomers(int customers) {
			this.customers.set(customers);
		}
	}
}
