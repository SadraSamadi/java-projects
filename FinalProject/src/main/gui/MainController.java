package main.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.restaurant.Customer;
import main.restaurant.Nationality;
import main.restaurant.Restaurant;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

// TODO: Auto-generated Javadoc
/**
 * The Class MainController.
 */
public class MainController implements Initializable {

	/** The restaurant. */
	public Restaurant restaurant;

	/** The table. */
	public TableView<CustomerRow> table;

	/** The id col. */
	public TableColumn<CustomerRow, Integer> idCol;

	/** The nationality col. */
	public TableColumn<CustomerRow, String> nationalityCol;

	/** The food type col. */
	public TableColumn<CustomerRow, String> foodTypeCol;

	/** The menu type col. */
	public TableColumn<CustomerRow, String> menuTypeCol;

	/** The money col. */
	public TableColumn<CustomerRow, Integer> moneyCol;

	/** The vote col. */
	public TableColumn<CustomerRow, Integer> voteCol;

	/** The fund label. */
	public Label fundLabel;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		restaurant = new Restaurant("Restaurant");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		nationalityCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
		foodTypeCol.setCellValueFactory(new PropertyValueFactory<>("foodType"));
		menuTypeCol.setCellValueFactory(new PropertyValueFactory<>("menuType"));
		moneyCol.setCellValueFactory(new PropertyValueFactory<>("money"));
		voteCol.setCellValueFactory(new PropertyValueFactory<>("vote"));
	}

	/**
	 * Adds the customer.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void addCustomer() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/customer_layout.fxml"));
		Parent root = loader.load();
		CustomerController customerController = loader.<CustomerController> getController();
		customerController.restaurant = this.restaurant;
		customerController.controller = this;
		Stage stage = new Stage();
		customerController.stage = stage;
		stage.setScene(new Scene(root));
		stage.setTitle("New Customer");
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * Update.
	 */
	public void update() {
		ObservableList<CustomerRow> values = FXCollections.observableArrayList();
		for (Customer customer : restaurant.getCustomers()) {
			values.add(new CustomerRow(customer));
		}
		table.setItems(values);
		fundLabel.setText("Fund : " + restaurant.getFund() + " $");
	}

	/**
	 * On settlement click.
	 */
	public void onSettlementClick() {
		CustomerRow customerRow = table.getSelectionModel().getSelectedItem();
		if (customerRow != null) {
			Customer customer = restaurant.getCustomerBy(customerRow.getId());
			if (customer != null) {
				int bill = customer.leaving();
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Settlement");
				alert.setContentText("Customer's bill : " + bill + " $");
				alert.showAndWait();
				restaurant.incrementFund(bill);
				restaurant.settlement(customer);
				update();
			}
		}
	}

	/**
	 * On cooking.
	 */
	public void onCooking() {
		List<Nationality> choices = new ArrayList<>();
		choices.add(Nationality.CHINA);
		choices.add(Nationality.ITALIAN);
		choices.add(Nationality.FRENCH);
		choices.add(Nationality.INDIAN);
		choices.add(Nationality.IRANIAN);
		ChoiceDialog<Nationality> dialog = new ChoiceDialog<>(Nationality.CHINA, choices);
		dialog.setTitle("Cooking");
		dialog.setHeaderText("Nationality");
		dialog.setContentText("Choose nationality : ");
		Optional<Nationality> result = dialog.showAndWait();
		if (result.isPresent()) {
			restaurant.cooking(result.get().getChar());
		}
	}

	/**
	 * Open waiters.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void openWaiters() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/waiters_layout.fxml"));
		Parent root = loader.load();
		WaiterController waiterController = loader.<WaiterController> getController();
		waiterController.init(restaurant);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Waiters");
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * Open foods.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void openFoods() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/foods_layout.fxml"));
		Parent root = loader.load();
		FoodsController foodsController = loader.<FoodsController> getController();
		foodsController.init(restaurant);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Foods");
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * The Class CustomerRow.
	 */
	public class CustomerRow {

		/** The nationality. */
		private SimpleStringProperty nationality;

		/** The id. */
		private SimpleIntegerProperty id;

		/** The food type. */
		private SimpleStringProperty foodType;

		/** The menu type. */
		private SimpleStringProperty menuType;

		/** The money. */
		private SimpleIntegerProperty money;

		/** The vote. */
		private SimpleIntegerProperty vote;

		/**
		 * Instantiates a new customer row.
		 *
		 * @param customer
		 *            the customer
		 */
		public CustomerRow(Customer customer) {
			this.nationality = new SimpleStringProperty(Nationality.getByChar(customer.getNationality()) + "");
			this.id = new SimpleIntegerProperty(customer.getId());
			this.foodType = new SimpleStringProperty((customer.getFoodType() == 'v' ? "Vegetarian" : "Non-Vegetarian"));
			this.menuType = new SimpleStringProperty(Nationality.getByChar(customer.getMenuType()) + "");
			this.money = new SimpleIntegerProperty(customer.getMoney());
			this.vote = new SimpleIntegerProperty(customer.getVote());
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
		 * Sets the nationality.
		 *
		 * @param nationality
		 *            the new nationality
		 */
		public void setNationality(String nationality) {
			this.nationality.set(nationality);
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
		 * Sets the id.
		 *
		 * @param id
		 *            the new id
		 */
		public void setId(int id) {
			this.id.set(id);
		}

		/**
		 * Gets the food type.
		 *
		 * @return the food type
		 */
		public String getFoodType() {
			return foodType.get();
		}

		/**
		 * Sets the food type.
		 *
		 * @param foodType
		 *            the new food type
		 */
		public void setFoodType(String foodType) {
			this.foodType.set(foodType);
		}

		/**
		 * Gets the menu type.
		 *
		 * @return the menu type
		 */
		public String getMenuType() {
			return menuType.get();
		}

		/**
		 * Sets the menu type.
		 *
		 * @param menuType
		 *            the new menu type
		 */
		public void setMenuType(String menuType) {
			this.menuType.set(menuType);
		}

		/**
		 * Gets the money.
		 *
		 * @return the money
		 */
		public int getMoney() {
			return money.get();
		}

		/**
		 * Sets the money.
		 *
		 * @param money
		 *            the new money
		 */
		public void setMoney(int money) {
			this.money.set(money);
		}

		/**
		 * Gets the vote.
		 *
		 * @return the vote
		 */
		public int getVote() {
			return vote.get();
		}

		/**
		 * Sets the vote.
		 *
		 * @param vote
		 *            the new vote
		 */
		public void setVote(int vote) {
			this.vote.set(vote);
		}

	}

}
