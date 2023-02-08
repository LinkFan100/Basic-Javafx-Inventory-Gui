package Controllers;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import com.sun.javafx.menu.RadioMenuItemBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class partsModifyFormController implements Initializable {

    @FXML
    private TextField modifyId;
    @FXML
    private TextField modifyName;
    @FXML
    private TextField modifyInv;
    @FXML
    private TextField modifyPrice;
    @FXML
    private TextField modifyMax;
    @FXML
    private TextField modifyMin;
    @FXML
    private TextField modifyMachineId;
    @FXML
    private Text ModifyMachineIdLabel;

    @FXML
    private RadioButton modifyiHRadioButton;
    @FXML
    private RadioButton modifyOutRadioButton;

    Parent scene;
    Stage stage;

    /**
     * Gets selected Part data to modify from part object into parts Modify View.
     * @param part
     */
    public void modifySelectedData(Part part) {
        modifyId.setText(String.valueOf(part.getId()));
        modifyName.setText(part.getName());
        modifyInv.setText(String.valueOf(part.getStock()));
        modifyPrice.setText(String.valueOf(part.getPrice()));
        modifyMax.setText(String.valueOf(part.getMax()));
        modifyMin.setText(String.valueOf(part.getMin()));

        //Get In-House or Outsourced radio button
        if (part instanceof InHouse) {
            modifyiHRadioButton.setSelected(true);
            ModifyMachineIdLabel.setText("Machine ID");
            modifyMachineId.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else {
            modifyOutRadioButton.setSelected(true);
            ModifyMachineIdLabel.setText("Company Name");
            modifyMachineId.setText(String.valueOf(((OutSourced) part).getCompanyName()));
        }
    }

    /**
     * Change Machine Id Label name to "Machine Id"
     * @param actionEvent
     */
    @FXML
    void modifyiHRadioButtonToggle(ActionEvent actionEvent) {
        ModifyMachineIdLabel.setText("Machine ID");

    }

    /**
     * Change Machine Id Label name to "Company Name"
     * @param actionEvent
     */
    @FXML
    void modifyOutRadioToggle(ActionEvent actionEvent) {
        ModifyMachineIdLabel.setText("Company Name");
    }

    /**
     * Exits Modify Parts View Returns to Main View
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    void modifycancelAddPartClick(ActionEvent actionEvent) throws IOException {
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
     * Save modification of part and updates parts Table with changes
     * Index out of bounds error
     * Fixed by changes values in update part
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    void modifyaddPartSaveClick(ActionEvent actionEvent) throws IOException {

        try {
            Boolean flag=false;

            int partId = Integer.parseInt(modifyId.getText());
            String partName = modifyName.getText();
            int partInv = Integer.parseInt(modifyInv.getText());
            double partPrice = Double.parseDouble(modifyPrice.getText());
            int partMin = Integer.parseInt(modifyMin.getText());
            int partMax = Integer.parseInt(modifyMax.getText());

            for (int a = 0; a < partName.length(); a++) {
                if (a == 0 && partName.charAt(a) == '-')
                    continue;
                if (!Character.isDigit(partName.charAt(a)))
                    flag = false;


                if (Character.isDigit(partName.charAt(a))) {
                    flag = true;
                }
            }
            if (flag == true) {
                Alert nameNumAlert = new Alert(Alert.AlertType.WARNING);
                nameNumAlert.setTitle("Critical Name Error");
                nameNumAlert.setContentText("Name field contains numbers");
                nameNumAlert.showAndWait();
            }


            else if (partMin > partMax) {
                Alert minMaxAlert = new Alert(Alert.AlertType.WARNING);
                minMaxAlert.setTitle("Critical Min/Max Error");
                minMaxAlert.setContentText("Error: The Min is Greater then the Max.");
                minMaxAlert.showAndWait();
            } else if (partInv > partMax || partMin > partInv) {
                Alert inventoryAlert = new Alert(Alert.AlertType.WARNING);
                inventoryAlert.setTitle("Critical Inventory Error");
                inventoryAlert.setContentText("Inventory input is not within the Min/Max Parameters");
                inventoryAlert.showAndWait();

            } else if (partName == null) {
                Alert nameAlert = new Alert(Alert.AlertType.WARNING);
                nameAlert.setTitle("Critical Name Error");
                nameAlert.setContentText("Name field is empty please input a name");
                nameAlert.showAndWait();

            }

            else {
                //In-House radio button is selected
                if (modifyiHRadioButton.isSelected()) {
                        int machineID = Integer.parseInt(modifyMachineId.getText());
                        Inventory.updatePart(partId, new InHouse(partId, partName, partPrice, partInv, partMin, partMax, machineID));
                    }
                else if (modifyOutRadioButton.isSelected()) {
                    String companyName = modifyMachineId.getText();
                    Inventory.updatePart(partId, new OutSourced(partId, partName, partPrice, partInv, partMin, partMax, companyName));
                }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}