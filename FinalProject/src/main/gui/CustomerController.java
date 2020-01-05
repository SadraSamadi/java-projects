package main.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.restaurant.Food;
import main.restaurant.Nationality;
import main.restaurant.Restaurant;

import java.net.URL;
import java.util.ResourceBundle;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerController.
 */
public class CustomerController implements Initializable {

	/** The restaurant. */
	public Restaurant restaurant;

	/** The stage. */
	public Stage stage;

	/** The controller. */
	public MainController controller;

	/** The nationality. */
	public ChoiceBox<Nationality> nationality;

	/** The food type. */
	public ChoiceBox<String> foodType;

	/** The menu type. */
	public ChoiceBox<Nationality> menuType;

	/** The money. */
	public Spinner<Integer> money;

	/** The vote. */
	public Slider vote;

	/** The state label. */
	public Label stateLabel;

	/** The table. */
	public TableView<FoodRow> table;

	/** The quality col. */
	public TableColumn<FoodRow, Integer> qualityCol;

	/** The international quality col. */
	public TableColumn<FoodRow, Integer> internationalQualityCol;

	/** The food type col. */
	public TableColumn<FoodRow, String> foodTypeCol;

	/** The id col. */
	public TableColumn<FoodRow, Integer> idCol;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nationality.setItems(FXCollections.observableArrayList(Nationality.CHINA, Nationality.ITALIAN,
				Nationality.FRENCH, Nationality.INDIAN, Nationality.IRANIAN, Nationality.ENGLISH));
		foodType.setItems(FXCollections.observableArrayList("Vegetarian", "Non-Vegetarian"));
		menuType.setItems(FXCollections.observableArrayList(Nationality.CHINA, Nationality.ITALIAN, Nationality.FRENCH,
				Nationality.INDIAN, Nationality.IRANIAN));
		money.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));

		qualityCol.setCellValueFactory(new PropertyValueFactory<>("quality"));
		internationalQualityCol.setCellValueFactory(new PropertyValueFactory<>("internationalQuality"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		foodTypeCol.setCellValueFactory(new PropertyValueFactory<>("foodType"));
	}

	/**
	 * On ok.
	 */
	public void onOK() {
		if (check()) {
			char nationality = this.nationality.getValue().getChar();
			char foodType = this.foodType.getValue().equals("Vegetarian") ? Food.VEGETARIAN : Food.NON_VEGETARIAN;
			char menuType = this.menuType.getValue().getChar();
			restaurant.customerEntry(nationality, foodType, menuType, money.getValue(), (int) vote.getValue());
			controller.update();
			stage.close();
		}
	}

	/**
	 * On cancel.
	 */
	public void onCancel() {
		stage.close();
	}

	/**
	 * On show.
	 */
	public void onShow() {
		if (check()) {
			char foodType = this.foodType.getValue().equals("Vegetarian") ? Food.VEGETARIAN : Food.NON_VEGETARIAN;
			char menuType = this.menuType.getValue().getChar();
			Food[] foods = restaurant.getFoodsFor(menuType, foodType);
			ObservableList<FoodRow> value = FXCollections.observableArrayList();
			for (Food food : foods) {
				value.add(new FoodRow(food));
			}
			table.setItems(value);
			if (foods.length == 0) {
				stateLabel.setText("Sorry, foods are finished !");
			}
		}
	}

	/**
	 * Check.
	 *
	 * @return true, if successful
	 */
	private boolean check() {
		boolean flag = false;
		if (nationality.getValue() == null) {
			stateLabel.setText("Please enter nationality ...");
		} else if (foodType.getValue() == null) {
			stateLabel.setText("Please enter food type ...");
		} else if (menuType.getValue() == null) {
			stateLabel.setText("Please enter menu type ...");
		} else if (money.getValue() < 4) {
			stateLabel.setText("Your money is not enough ...");
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * The Class FoodRow.
	 */
	public class FoodRow {

		/** The quality. */
		private SimpleIntegerProperty quality;

		/** The international quality. */
		private SimpleIntegerProperty internationalQuality;

		/** The id. */
		private SimpleIntegerProperty id;

		/** The food type. */
		private SimpleStringProperty foodType;

		/**
		 * Instantiates a new food row.
		 *
		 * @param food
		 *            the food
		 */
		public FoodRow(Food food) {
			quality = new SimpleIntegerProperty(food.getQuality());
			internationalQuality = new SimpleIntegerProperty(food.getInternationalQuality());
			id = new SimpleIntegerProperty(food.getId());
			foodType = new SimpleStringProperty((food.getFoodType() == 'v' ? "Vegetarian" : "Non-Vegetarian"));
		}

		/**
		 * Gets the quality.
		 *
		 * @return the quality
		 */
		public int getQuality() {
			return quality.get();
		}

		/**
		 * Sets the quality.
		 *
		 * @param quality
		 *            the new quality
		 */
		public void setQuality(int quality) {
			this.quality.set(quality);
		}

		/**
		 * Gets the international quality.
		 *
		 * @return the international quality
		 */
		public int getInternationalQuality() {
			return internationalQuality.get();
		}

		/**
		 * Sets the international quality.
		 *
		 * @param internationalQuality
		 *            the new international quality
		 */
		public void setInternationalQuality(int internationalQuality) {
			this.internationalQuality.set(internationalQuality);
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
	}

}
