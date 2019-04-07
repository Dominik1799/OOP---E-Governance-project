package Controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class LoginException extends Exception {
    public LoginException(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Please fill all fields.");
        alert.showAndWait();
        Platform.exit();
    }
}
