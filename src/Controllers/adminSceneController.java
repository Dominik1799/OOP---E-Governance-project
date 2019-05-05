package Controllers;

import Factories.Datasource;
import Factories.UserFactory;
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

/**
 * trieda starajuca sa o spravovanie pouzivatelov nejakym adminom/pracovnikom
 * tento typ admina moze schvalovat alebo zamietat pouzivatelske ziadosti
 * o specialny typ uctu.
 * Taktiez ma k dispozicii tabulku s prehladom o vsetkych pouzivateloch
 */
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
            //vytvorenie admina
            if (rs.getString("SPZ").equalsIgnoreCase("admin")){
                this.admin = new Admin(rs.getString("Name"),rs.getString("Surename"),
                                       rs.getString("FuelType"),rs.getString("SPZ"), (double) 0);
                continue;
            }
            //v tejto scene je jedno kolko maju pouzivatelia kreditu, preto nastavim nulu
            people.add(UserFactory.getInstance().makeUser(rs.getString("Type"),
                    rs.getString("Name"),
                    rs.getString("Surename"),
                    rs.getString("FuelType"),
                    rs.getString("SPZ"), (double) 0));
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

    /**
     * metoda na prijatie ziadosti o studentsky ucet
     */
    public void onAcceptStudentClick(){
        admin.acceptUser((String) box.getValue(),"student");
        box.getItems().remove(box.getValue());
    }

    /**
     * metoda na prijatie ziadosti o ZTP ucet
     */
    public void onAcceptDissabledClick(){
        admin.acceptUser((String) box1.getValue(),"Disabled");
        box1.getItems().remove(box1.getValue());
    }

    /**
     * odmietnutie studentskej ziadosti
     */
    public void onRejectStudentClick(){
        box.getItems().remove(box.getValue());
    }

    /**
     * odmietnutie ZTP ziadosti
     */
    public void onRejectDissabledClick(){
        box1.getItems().remove(box1.getValue());
    }


    /**
     * vnorena trieda admina. Tento admin sa stara o schvalovanie/zamietanie pouzivatelskych ziadosti
     */
    public class Admin extends User implements user_interface {
        public Admin(String fname, String surename, String typeOfCar, String Carid, Double credit) {
            super("admin", "admin", "admin", "admin", 5.00);
        }

        /**
         * Funckia na schvalenie pouzivatela
         * @param SPZ
         * @param Type
         */
        public void acceptUser(String SPZ, String Type) {
            Datasource.getInstance().updateUser(SPZ, Type);
        }


    }
}
