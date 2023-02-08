package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javax.naming.directory.SearchResult;


public class Inventory {
    private static ObservableList<Part> allPart = FXCollections.observableArrayList();
    private static ObservableList<Product> allProduct = FXCollections.observableArrayList();

    /**
    *add New Part to Part Table
     */
    public static void addPart(Part newPart){
    allPart.add(newPart);
    }

    /**
    * add New Product to Product Table
     */
    public static void addProduct(Product newProduct){
    allProduct.add(newProduct);
    }

    /**
     * Lookup PartId
     * @param partId
     * @return
     */
    public static Part lookupPart(int partId){
        Part partSearch = null;
        for (Part part : allPart){
            if (partId == part.getId()){
                partSearch = part;
            }
        }
        return partSearch;
    }

    /**
     * Lookup ProductId
     * @param productid
     * @return
     */
    public static Product lookupProduct(int productid){
        Product productSearch = null;
        for (Product product : allProduct){
            if (productid == product.getId()){
                productSearch = product;
            }
        }
        return productSearch;
    }

    /**
     * Lookup Part Name
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partNameSearch = FXCollections.observableArrayList();
        for (Part part : allPart){
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                partNameSearch.add(part);
            }
        }
        return partNameSearch;
    }

    /**
     * Lookup Product Name
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName){

        ObservableList<Product> productNameSearch = FXCollections.observableArrayList();
        for (Product product : allProduct){
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                productNameSearch.add(product);
            }
        }
        return productNameSearch;
    }

    /**
     * Update Selected Part
     * Needed to loop the items and set proper index
     * started index at 0 but when loop set index it started at 1 because of increment
     * Readjusted Index initial value at -1 to fix issue.
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart){
         index = -1;
         for(Part part:getAllPart()) {
             index++;
             if(part.getId()== selectedPart.getId()) {
                 allPart.set(index, selectedPart);
             }
         }
    }

    /**
     * Updates Product
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct){
        index = -1;
        for(Product product:getAllProduct()) {
            index++;
            if(product.getId()== newProduct.getId()) {
                allProduct.set(index, newProduct);
            }
        }
    }


    /**
     * Delete Selected Part
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart){
    return allPart.remove(selectedPart);
    }

    /**
     * Delete Selected Product
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct){
    return allProduct.remove(selectedProduct);
    }

    /**
     * Gets all Parts
     * @return
     */
    public static ObservableList<Part> getAllPart(){
        return allPart;
    }

    /**
     * Gets all Products
     * @return
     */
    public static ObservableList<Product> getAllProduct(){
    return allProduct;
    }

}
