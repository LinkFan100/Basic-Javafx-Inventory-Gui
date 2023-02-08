package Controllers;

import Model.*;
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

public class partsAddFormController implements Initializable {
    private static int partId = 1001;

    @FXML
    private Text machineIDLabel;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartId;
    @FXML
    private TextField addPartInv;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartMin;
    @FXML
    private TextField addPartMachineId;
    @FXML
    private RadioButton iHRadioButton;
    @FXML
    private RadioButton OutRadioButton;

    Stage stage;
    Parent scene;


    /**
     *Adds Input into part Table
     *Runtime error caused by "NumberFormatException: For input string: """
     *Fixed error by adjusting addPartId to be set to the value of partId
     *No Data being inserted into the mainform fxml parts table
     *Fixed by change getAll method in inventory Model
     *@author Lafyette Russell
     * @version 1.0
     *@param actionEvent addPartSaveClick
     */
    @FXML void addPartSaveClick(ActionEvent actionEvent) throws IOException {

        try {

            int partId = Integer.parseInt(addPartId.getText());
            String partName = addPartName.getText();
            Boolean flag = false;

            int partInv = Integer.parseInt(addPartInv.getText());
            double partPrice = Double.parseDouble(addPartPrice.getText());
            int partMin = Integer.parseInt(addPartMin.getText());
            int partMax = Integer.parseInt(addPartMax.getText());

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
                if (iHRadioButton.isSelected()) {
                    try {
                        int machineID = Integer.parseInt(addPartMachineId.getText());
                        Inventory.addPart(new InHouse(partId, partName, partPrice, partInv, partMin, partMax, machineID));

                    } catch (NumberFormatException e) {
                        Alert MachineIdAlert = new Alert(Alert.AlertType.WARNING);
                        MachineIdAlert.setTitle("Critical Machine ID Error");
                        MachineIdAlert.setContentText("The Machine Id format is Incorrect please input a number.");
                        MachineIdAlert.showAndWait();
                    }
                } else if (OutRadioButton.isSelected()) {
                    String companyName = addPartMachineId.getText();
                    Inventory.addPart(new OutSourced(partId, partName, partPrice, partInv, partMin, partMax, companyName));

                }
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/Views/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

            }

        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please input valid values for all text fields");
            alert.showAndWait();
        }
    }

    /**
     *On iHRadioButtonToggle selection set text of machineIDLabel to Machine ID
     *OutRadioButton is set to false
     * @param actionEvent
      */
    @FXML void iHRadioButtonToggle(ActionEvent actionEvent) {
        machineIDLabel.setText("Machine ID");
        OutRadioButton.setSelected(false);
    }

    /**
     *On OutRadioClick selection set text of machineIDLabel to Company Name
     *iHRadioButton is set to false
     * @param actionEvent
     */
    @FXML void OutRadioClick(ActionEvent actionEvent) {
        machineIDLabel.setText("Company Name");
        iHRadioButton.setSelected(false);
    }

    /**
     * Set on ActionEvent cancelAddPartClick to return to previous scene
     * @param actionEvent
     * @throws IOException
     */
    @FXML void cancelAddPartClick(ActionEvent actionEvent) throws IOException {
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
     * Set increment for id input to increase with each new object
     * @return returns partId
     */
    public static int incrementId(){
        partId++;
        return partId;
    }

    /**
     *Default selects iHRadioButton
     * assign incrementId() return to partId
     * add the value of partId to Id coloum in table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iHRadioButton.setSelected(true);
        partId = incrementId();
        addPartId.setText(String.valueOf(partId));
    }
}
