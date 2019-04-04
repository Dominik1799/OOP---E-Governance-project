package Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import Users.*;

public class LoginController {
    private String type;
    private String userName;
    private String userSurename;
    private String userSPZ;
    private String userFuelType;
    private boolean isLoginSuccesful = false;

    @FXML
    private TextField spz;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;

    @FXML
    public void initialize(){
        loginButton.setDisable(true);
    }

    public void adminLogIn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/adminScene.fxml"));
        Parent newsceneparent = loader.load();
        Scene newscene = new Scene(newsceneparent);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newscene);
        window.show();
    }

    @FXML
    public void onLoginClick(ActionEvent event) throws IOException, SQLException {
        User user;
        try {
            ResultSet results = Datasource.getInstance().openUsers();
            //iterating through the table
            while (results.next ()){
                //We look into the database if there is a match with loogin and password
                if (results.getString("SPZ").equals(spz.getText()) && results.getString("Password").equals(password.getText())){
                    type = results.getString("Type");
                    userName = results.getString("Name");
                    userSurename = results.getString("Surename");
                    userSPZ = results.getString("SPZ");
                    userFuelType = results.getString("FuelType");
                    isLoginSuccesful = true;
                    Datasource.getInstance().closeConnection();
                    break;
                } else continue;

            }
        } catch (SQLException e) {
            System.out.println("FATAL ERROR: couldn't connect to the USERS database");
        }
        //login is succesfull,change screen
        if (isLoginSuccesful == true) {
            if (userSPZ.equalsIgnoreCase("admin")){
                adminLogIn(event);
                return;
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Scenes/homescreen.fxml"));
            Parent newsceneparent = loader.load();
            Scene newscene = new Scene(newsceneparent);
            user = UserFactory.getInstance().makeUser(type,userName,userSurename,userFuelType,userSPZ);
            HomeScreenController controller = loader.getController();
            controller.setAllLabels(user);
            Datasource.getInstance().closeConnection();
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newscene);
            window.show();
        }

    }

    @FXML
    public void handleKeyReleased(){
        String text = spz.getText();
        String text1 = password.getText();
        boolean disableButtons = text.isEmpty() || text.trim().isEmpty() || text1.isEmpty() || text1.trim().isEmpty() ;
        loginButton.setDisable(disableButtons);
    }

    public void onRegisterClick(ActionEvent event) throws IOException,SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/register.fxml"));
        Parent newsceneparent = loader.load();
        Scene newscene = new Scene(newsceneparent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(newscene);
        window.show();
    }

}
