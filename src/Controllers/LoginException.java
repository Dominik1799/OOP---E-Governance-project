package Controllers;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Vlastna vynimka, je vyvolana ak pri registracii pouzivatel nevyplnil jedno z poli. Ak sa tak stane, vynimka
 * vyhodi informacne okno a presmeruje pouzivatela na prihlasovaciu obrazovku
 */
public class LoginException extends Exception {
    public LoginException(Event event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Please fill all fields.");
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
}
