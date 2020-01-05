package main.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.restaurant.Food;
import main.restaurant.Nationality;
import main.restaurant.Restaurant;

// TODO: Auto-generated Javadoc
/**
 * The Class FoodsController.
 */
public class FoodsController {

	/** The table. */
	public TableView<FoodRow> table;

	/** The id col. */
	public TableColumn<FoodRow, Integer> idCol;

	/** The quality col. */
	public TableColumn<FoodRow, Integer> qualityCol;

	/** The international quality col. */
	public TableColumn<FoodRow, Integer> internationalQualityCol;

	/** The nationality col. */
	public TableColumn<FoodRow, String> nationalityCol;

	/** The food type col. */
	public TableColumn<FoodRow, String> foodTypeCol;

	/** The number col. */
	public TableColumn<FoodRow, Integer> numberCol;

	/**
	 * Inits the.
	 *
	 * @param restaurant
	 *            the restaurant
	 */
	public void init(Restaurant restaurant) {
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		qualityCol.setCellValueFactory(new PropertyValueFactory<>("quality"));
		internationalQualityCol.setCellValueFactory(new PropertyValueFactory<>("internationalQuality"));
		nationalityCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
		foodTypeCol.setCellValueFactory(new PropertyValueFactory<>("foodType"));
		numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
		ObservableList<FoodRow> values = FXCollections.observableArrayList();
		for (Food food : restaurant.getFoods()) {
			values.add(new FoodRow(food));
		}
		table.setItems(values);
	}

	/**
	 * The Class FoodRow.
	 */
	public class FoodRow {

		/** The id. */
		private SimpleIntegerProperty id;

		/** The quality. */
		private SimpleIntegerProperty quality;

		/** The international quality. */
		private SimpleIntegerProperty internationalQuality;

		/** The nationality. */
		private SimpleStringProperty nationality;

		/** The food type. */
		private SimpleStringProperty foodType;

		/** The number. */
		private SimpleIntegerProperty number;

		/**
		 * Instantiates a new food row.
		 *
		 * @param food
		 *            the food
		 */
		public FoodRow(Food food) {
			this.id = new SimpleIntegerProperty(food.getId());
			this.quality = new SimpleIntegerProperty(food.getQuality());
			this.internationalQuality = new SimpleIntegerProperty(food.getInternationalQuality());
			this.nationality = new SimpleStringProperty(Nationality.getByChar(food.getNationality()) + "");
			this.foodType = new SimpleStringProperty((food.getFoodType() == 'v' ? "Vegetarian" : "Non-Vegetarian"));
			this.number = new SimpleIntegerProperty(food.getNumber());
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
		 * Gets the quality.
		 *
		 * @return the quality
		 */
		public int getQuality() {
			return quality.get();
		}

		/**
		 * Quality property.
		 *
		 * @return the simple integer property
		 */
		public SimpleIntegerProperty qualityProperty() {
			return quality;
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
		 * International quality property.
		 *
		 * @return the simple integer property
		 */
		public SimpleIntegerProperty internationalQualityProperty() {
			return internationalQuality;
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
		 * Gets the food type.
		 *
		 * @return the food type
		 */
		public String getFoodType() {
			return foodType.get();
		}

		/**
		 * Food type property.
		 *
		 * @return the simple string property
		 */
		public SimpleStringProperty foodTypeProperty() {
			return foodType;
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
		 * Gets the number.
		 *
		 * @return the number
		 */
		public int getNumber() {
			return number.get();
		}

		/**
		 * Number property.
		 *
		 * @return the simple integer property
		 */
		public SimpleIntegerProperty numberProperty() {
			return number;
		}

		/**
		 * Sets the number.
		 *
		 * @param number
		 *            the new number
		 */
		public void setNumber(int number) {
			this.number.set(number);
		}
	}
}
