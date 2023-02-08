package Controllers;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class modifyProductPartsController implements Initializable {
    Stage stage;
    Parent scene;

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private Product newProduct;



    @FXML
    private TextField modifyProductId;
    @FXML
    private TextField modifyProductName;
    @FXML
    private TextField modifyProductInv;
    @FXML
    private TextField modifyProductPrice;
    @FXML
    private TextField modifyProductMax;
    @FXML
    private TextField modifyProductMin;
    @FXML
    private TableView<Part> modifypartsOfProductTable;
    @FXML
    private TableColumn<Part, Integer> partID;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partsInventoryLevel;
    @FXML
    private TableColumn<Part, Double> partsPrice;
    @FXML
    private TextField productPartSearchBar;

    @FXML
    private TableView<Part> modifyAssociatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> partID1;
    @FXML
    private TableColumn<Part, String> partName1;
    @FXML
    private TableColumn<Part, Integer> partsInventoryLevel1;
    @FXML
    private TableColumn<Part, Double> partsPrice1;


    /**
     * Loads Data into modify form associatedParts Table and make a product object newProduct.
     * @param product
     */
    public void modifyProductSelectedData(Product product) {

        newProduct = product;
        modifyProductId.setText(String.valueOf(product.getId()));
        modifyProductName.setText(product.getName());
        modifyProductInv.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMax.setText(String.valueOf(product.getMax()));
        modifyProductMin.setText(String.valueOf(product.getMin()));
        modifyAssociatedPartsTable.setItems(product.getAllAssociatedPart());


    }

    /**
     * Adds new associated part in modify view.
     * @param actionEvent
     */
    @FXML void modifyProductPartClick(ActionEvent actionEvent) {
        Part partSelected = modifypartsOfProductTable.getSelectionModel().getSelectedItem();
        if(modifypartsOfProductTable.getSelectionModel().getSelectedItem() == null){
            Alert alertNull = new Alert(Alert.AlertType.WARNING);
            alertNull.setTitle("Error");
            alertNull.setContentText("Nothing is Selected.");
            alertNull.showAndWait();
        }
        else
            newProduct.addAssociatedPart(partSelected);
            newProduct.setAssociatedPart(newProduct.getAllAssociatedPart());
    }

    /**
     * Remove Associated Part in Modify View
     * @param actionEvent
     */
    @FXML void modifyRemoveAssociatedPartClick(ActionEvent actionEvent) {

        Part partA = modifyAssociatedPartsTable.getSelectionModel().getSelectedItem();
        if (modifyAssociatedPartsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alertNull = new Alert(Alert.AlertType.WARNING);
            alertNull.setTitle("Error");
            alertNull.setContentText("Nothing is Selected.");
            alertNull.showAndWait();
        } else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setContentText("Are you sure you want to Delete this part?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                newProduct.deleteAssociatedPart(partA);
            }
        }
    }

    /**
     * Save Changes Done to Object in Modify Form.
     * @param actionEvent
     * @throws IOException
     */
    @FXML void modifyProductSaveChangesClick(ActionEvent actionEvent) throws IOException {

        try {
            Boolean flag=false;


            int productId = Integer.parseInt(modifyProductId.getText());
            String productName = modifyProductName.getText();
            double productPrice = Double.parseDouble(modifyProductPrice.getText());
            int productInv = Integer.parseInt(modifyProductInv.getText());
            int productMin = Integer.parseInt(modifyProductMin.getText());
            int productMax = Integer.parseInt(modifyProductMax.getText());

            for (int a = 0; a < productName.length(); a++) {
                if (a == 0 && productName.charAt(a) == '-')
                    continue;
                if (!Character.isDigit(productName.charAt(a)))
                    flag = false;


                if (Character.isDigit(productName.charAt(a))) {
                    flag = true;
                }
            }
            if (flag == true) {
                Alert nameNumAlert = new Alert(Alert.AlertType.WARNING);
                nameNumAlert.setTitle("Critical Name Error");
                nameNumAlert.setContentText("Name field contains numbers");
                nameNumAlert.showAndWait();
            }


            else if (productMin > productMax) {
                Alert minMaxAlert = new Alert(Alert.AlertType.WARNING);
                minMaxAlert.setTitle("Critical Min/Max Error");
                minMaxAlert.setContentText("Error: The Min is Greater then the Max.");
                minMaxAlert.showAndWait();
            } else if (productInv > productMax || productMin > productInv) {
                Alert inventoryAlert = new Alert(Alert.AlertType.WARNING);
                inventoryAlert.setTitle("Critical Inventory Error");
                inventoryAlert.setContentText("Inventory input is not within the Min/Max Parameters");
                inventoryAlert.showAndWait();

            } else if (productName == null) {
                Alert nameAlert = new Alert(Alert.AlertType.WARNING);
                nameAlert.setTitle("Critical Name Error");
                nameAlert.setContentText("Name field is empty please input a name");
                nameAlert.showAndWait();

            }

            else {
                newProduct.setId(Integer.parseInt(modifyProductId.getText()));
                newProduct.setName(modifyProductName.getText());
                newProduct.setPrice(Double.parseDouble(modifyProductPrice.getText()));
                newProduct.setStock(Integer.parseInt(modifyProductInv.getText()));
                newProduct.setMin(Integer.parseInt(modifyProductMin.getText()));
                newProduct.setMax(Integer.parseInt(modifyProductMax.getText()));
                newProduct.setAssociatedPart(newProduct.getAllAssociatedPart());
                Inventory.updateProduct(newProduct.getId(), newProduct);
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/Views/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }





        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please input valid values for all text fields");
            alert.showAndWait();
        }
    }

    /**
     * Cancel Modify and return to Main View.
     * @param actionEvent
     * @throws IOException
     */
    @FXML void modifyProductCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Alert Alertcancel = new Alert(Alert.AlertType.CONFIRMATION);
        Alertcancel.setTitle("Confirm");
        Alertcancel.setContentText("Are you sure? All data Inputted will be lost.");
        Optional<ButtonType> result = Alertcancel.showAndWait();
        if(result.get()==ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Views/MainForm.fxml"));
            stage.setTitle("Inventory Management");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Search Associated Part In Modify View.
     * @param actionEvent
     */
    @FXML void modifyProductPartSearch(ActionEvent actionEvent) {
        String searchinput = productPartSearchBar.getText();
        try{ObservableList<Part> partSearch = Inventory.lookupPart(searchinput);
            if(partSearch.isEmpty()){
                int idSearch = Integer.parseInt(searchinput);
                Part pSearch = Inventory.lookupPart(idSearch);
                partSearch.add(pSearch);

                if (pSearch == null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Search Error");
                    alert.setContentText("No Matching Id");
                    alert.showAndWait();
                }
            }

            modifypartsOfProductTable.setItems(partSearch);

        } catch (Exception e) {
            modifypartsOfProductTable.setItems(null);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Search Error");
            alert.setContentText("No Matching Results found.");
            alert.showAndWait();
        }
    }

    /**
     * Set VAlues of Modify View Associated parts tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        modifypartsOfProductTable.setItems(Inventory.getAllPart());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        partID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevel1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPrice1.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


}
