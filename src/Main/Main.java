package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Some Future enhancement to implement would be another view would with company names and the parts
  associated with said company in a drop-down table.
 * Manufacture Date added to both add/modify products and add/modify parts views.
 * Javadoc files are located within C482 Project.zip inside Javadocs folder.
 */
public class Main extends Application {
    Stage mainWindow;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Views/MainForm.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 700, 575));
        primaryStage.show();
    }


}