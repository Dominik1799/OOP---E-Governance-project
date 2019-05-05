package Controllers;

import Factories.Datasource;
import Users.*;
import Users.user_interface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * V tejto scene sa tento typ pracovnika/admina stara o pridavanie noveho parkoviska
 */

public class adminMainController implements Initializable {
    public AdminPark admin = new AdminPark();
    @FXML
    TextField town,hourprice,dpp;
    @FXML
    ComboBox type;
    ObservableList<String> typeList = FXCollections.observableArrayList("under","reg","multi");
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.setItems(typeList);
    }

    public void onAddClick(){
        this.admin.addParking(Integer.valueOf(hourprice.getText()),town.getText(),Integer.valueOf(dpp.getText()), (String) type.getValue());
    }

    public void onLogoutClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/login.fxml"));
        Parent loginScreen = loader.load();
        Scene loginScene = new Scene(loginScreen);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }

    /**
     * vnorena trieda adminPark, tento admin pridava nove parkovisko do databazy
     */
    public class AdminPark {

        public void addParking(Integer price,String town,Integer DPP,String type) {
            Datasource.getInstance().createParking(town,price,DPP,type);
        }


    }







}

