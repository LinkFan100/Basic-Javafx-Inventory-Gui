package Model;

import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedPart;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


/**
 * Getters and Setters
 */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getters
     * @return
     */
    public int getStock() {
        return stock;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }
    public int getId() {
        return id;
    }

    /**
     * Setters
     * @param min, max, stock, id
     */
    public void setMin(int min) {
        this.min = min;
    }
    public void setMax(int max) {
        this.max = max;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter For Associated Parts
     * @return
     */
    public ObservableList<Part> getAllAssociatedPart() {
        return associatedPart;
    }

    public void addAssociatedPart(Part part) {
        this.associatedPart.add(part);
    }
    public void deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedPart.remove(selectedAssociatedPart);
    }

    /**
     * Setter For AssociatedPart
     * @param associatedPart
     */
    public void setAssociatedPart(ObservableList<Part> associatedPart) {
        this.associatedPart = associatedPart;
    }
}
