/**
 * Author: Ravi Tiwari
 * Filename: Items.java
 * Specifications: Items Java Class
 * For: CSE 205 - Final Project
 */



import java.util.ArrayList;
import java.util.Arrays;

public class Items {
	private String name;
	private double price;
	private int quantity = 0;
	private int availableQuant;

	//The hard coded items that go in the arraylist of items
	public static Items item1 = new Items("Frappe Coffee", 5.89, 100);
	public static Items item2 = new Items("Hot Chocolate", 5.89, 100);
	public static Items item3 = new Items("Cappuccino", 5.79, 100);
	public static Items item4 = new Items("Caffe Mocha", 3.78, 100);
	public static Items item5 = new Items("Vanilla Creme", 4.99, 100);
	public static Items item6 = new Items("Pistachio", 3.5, 100);
	public static Items item7 = new Items("Espresso", 4.0, 100);
	public static Items item8 = new Items("Caffe Americano",2.99 , 100);
	public static Items item9 = new Items("Blonde Roast", 3.0, 100);
	public static Items item10 = new Items("Caffe Misto", 4.75, 100);

	//The arraylist is public static so it is accessible anywhere in the project
	public static ArrayList<Items> itemsArray = new ArrayList<Items>();

	/*public static ArrayList<Items> itemsArray = new ArrayList<Items>(Arrays.asList(
	new Items("Frappe Coffee", 5.89, 100),
	new Items("Hot Chocolate", 5.89, 100),
	new Items("Cappuccino", 5.79, 100),
	new Items("Caffe Mocha", 3.78, 100),
	new Items("Vanilla Creme", 4.99, 100),
	new Items("Pistachio", 3.49, 100),
	new Items("Espresso", 3.99, 100),
	new Items("Caffe Americano", 2.99, 100),
	new Items("Blonde Roast", 3.69, 100),
	new Items("Caffe Misto", 4.75, 100) ));*/

	//Items constructor
	public Items(String n, double p, int q) {
		this.name = n;
		this.price = p;
		this.availableQuant = q;
	}

	//add and Item to the arraylist
	public void addItem(Items i){
		itemsArray.add(i);
	}

	//getters and setters
	public String getName() {
		return name;
	}

	public void setName(String s) {
		name = s;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double d) {
		price = d;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int n) {
		quantity = n;
	}

	public int getAvailableQuant(){
		return this.availableQuant;
	}

	public void setAvailableQuant(int n) {
		availableQuant = availableQuant -n;
	}

	public String toString() {
		return (this.name +" | $" + price);
	}

	public static Items findItem(String name){

		for (Items i : Items.itemsArray){
			if (name.equals(i.getName())){
				return i;
			}
		}
		return null;
	}
}
