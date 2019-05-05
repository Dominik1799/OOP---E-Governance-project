
package Controllers;

import Factories.Datasource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * scena kde sa moze registrovat novy pouzivatel
 */
public class RegisterController implements Initializable {
    ObservableList<String> FuelTypesList = FXCollections.observableArrayList("Diesel",
            "Gasoline","LPG","CNG","Electric","Hybrid");
    @FXML
    private TextField Name;
    @FXML
    private TextField Surename;
    @FXML
    private PasswordField Password;
    @FXML
    private TextField Carid;
    @FXML
    private Button RegisterButton;

    @FXML
    private ChoiceBox FuelType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FuelType.setItems(FuelTypesList);
    }

    /**
     * spustenie registracie
     * @param event
     * @throws IOException
     * @throws LoginException ak pouzivatel nevyplni vsetky kolonky
     */
    public void onRegisterClick(ActionEvent event) throws IOException,LoginException {
        boolean safeCheck = Name.getText().isEmpty() || Surename.getText().isEmpty() || (FuelType.getValue() == null) ||
                            Carid.getText().isEmpty() || Password.getText().isEmpty();
        if (safeCheck){
            throw new LoginException(event);
        }
        Datasource.getInstance().createUser(Carid.getText(),Password.getText(),Name.getText(),Surename.getText(),(String)FuelType.getValue());
        System.out.println(FuelType.getValue());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Registration succesfull! Please login to your account.");
        alert.showAndWait();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/login.fxml"));
        Parent loginScreen = loader.load();
        Scene loginScene = new Scene(loginScreen);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }

    public void onLoginClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/login.fxml"));
        Parent loginScreen = loader.load();
        Scene loginScene = new Scene(loginScreen);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }

//    @FXML
//    public void handleKeyReleased(){
//        String text = Name.getText();
//        String text1 = Password.getText();
//        String text2 = Carid.getText();
//        String text3 = Surename.getText();
//        boolean disableButtons = text.isEmpty() || text.trim().isEmpty() || text1.isEmpty() || text1.trim().isEmpty()
//                || text2.trim().isEmpty() || text2.isEmpty() || text3.trim().isEmpty() || text3.isEmpty() ||
//                (FuelType.getValue() == null);
//        RegisterButton.setDisable(disableButtons);
//    }


    public void onRegClck(ActionEvent event){
        try {
            onRegisterClick(event);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}