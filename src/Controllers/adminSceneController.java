package Controllers;

import Users.*;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class adminSceneController implements Initializable {
    Admin admin;

    ObservableList<String> StudentreqID = FXCollections.observableArrayList();
    ObservableList<String> DisabledreqID = FXCollections.observableArrayList();

    @FXML
    private ComboBox box,box1;

    @FXML private TableView<User> tableView;
    @FXML private TableColumn<User, String> carID;
    @FXML private TableColumn<User, String> fname;
    @FXML private TableColumn<User, String> Surname;
    @FXML private TableColumn<User, String> typecar;
    @FXML private TableColumn<User, String> typeuser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fname.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        carID.setCellValueFactory(new PropertyValueFactory<User, String>("carID"));
        Surname.setCellValueFactory(new PropertyValueFactory<User, String>("surename"));
        typecar.setCellValueFactory(new PropertyValueFactory<User, String>("typeOfCar"));
        typeuser.setCellValueFactory(new PropertyValueFactory<User, String>("type"));
        try {
            tableView.setItems(getPeople());
            box.setItems(StudentreqID);
            box1.setItems(DisabledreqID);
            Datasource.getInstance().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public ObservableList<User>  getPeople() throws SQLException {
        ObservableList<User> people = FXCollections.observableArrayList();
        ResultSet rs = Datasource.getInstance().openUsers();
        while (rs.next()) {

            if (rs.getString("SPZ").equalsIgnoreCase("admin")){
                this.admin = new Admin(rs.getString("Name"),rs.getString("Surename"),
                                       rs.getString("FuelType"),rs.getString("SPZ"));
                continue;
            }
            people.add(UserFactory.getInstance().makeUser(rs.getString("Type"),
                    rs.getString("Name"),
                    rs.getString("Surename"),
                    rs.getString("FuelType"),
                    rs.getString("SPZ")));
            if (rs.getInt("req") == 1) this.StudentreqID.add(rs.getString("SPZ"));
            if (rs.getInt("req") == 2) this.DisabledreqID.add(rs.getString("SPZ"));
                    }

        return people;
        }

    public void onLogOutClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/login.fxml"));
        Parent loginScreen = loader.load();
        Scene loginScene = new Scene(loginScreen);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }

    public void onAcceptStudentClick(){
        admin.acceptUser((String) box.getValue(),"student");
        box.getItems().remove(box.getValue());
    }

    public void onAcceptDissabledClick(){
        admin.acceptUser((String) box1.getValue(),"Disabled");
        box1.getItems().remove(box1.getValue());
    }

    //kedze je trieda admin pouzita len tu, pouzijem ju ako vhniezdenu triedu
    public class Admin extends User implements user_interface {
        public Admin(String fname, String surename, String typeOfCar, String Carid) {
            super(fname, surename, typeOfCar, Carid);
        }

        public void acceptUser(String SPZ, String Type) {
            Datasource.getInstance().updateUser(SPZ, Type);
        }
    }
}
