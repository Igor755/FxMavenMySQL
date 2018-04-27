package com.metlin.mavenfxmysql.Controller;


import com.metlin.mavenfxmysql.People.User;
import com.metlin.mavenfxmysql.util.DBUtil;
import com.mysql.jdbc.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;


public class Controller implements Initializable {




    @FXML
    private Button add;

    @FXML
    private Button update;

    @FXML
    private Button delete;




    @FXML
    private TextArea textArea;

    @FXML
    private TextField first_name;

    @FXML
    private TextField last_name;

    @FXML
    private TextField middle_name;

    @FXML
    private DatePicker date_birth;






    @FXML
    private TableView<User> tableUsers;






    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> lastColumn;

    @FXML
    private TableColumn<User, String> middleColumn;

    @FXML
    private TableColumn<User, String> DateColumn;



    private ObservableList<User> usersData = FXCollections.observableArrayList();





    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        try {


            Connection connection = (Connection) DBUtil.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM people.warrior");

            while (resultSet.next()) {


                usersData.add(new User(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("last"),
                        resultSet.getString("middle"),
                        resultSet.getString("birth")));
            }



        } catch (SQLException e) {
            System.out.println("DON'T LOAD DATA");
            textArea.setText("DON'T LOAD DATA");
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User,String >("name"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<User,String>("last"));
        middleColumn.setCellValueFactory(new PropertyValueFactory<User,String>("middle"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<User,String>("birth"));

        tableUsers.setItems(usersData);

    }

    @FXML
    public  void addUser(ActionEvent event){

        String sql = "Inesrt into people.warrior (id,name,last,middle,birth) Values (?,?,?,?,?)";
        String name = first_name.getText();
        String last = last_name.getText();
        String middle = middle_name.getText();
        LocalDate birth = date_birth.getValue();


    }




}

