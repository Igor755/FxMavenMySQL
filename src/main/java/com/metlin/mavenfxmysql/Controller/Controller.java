package com.metlin.mavenfxmysql.Controller;


import com.metlin.mavenfxmysql.People.User;
import com.metlin.mavenfxmysql.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;


public class Controller implements Initializable {


    public String p = "";

    @FXML
    private Button add;

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
    private TableColumn<User, Date> DateColumn;

    private ObservableList<User> data;
    private DBUtil dbUtil;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // textArea.setText(DBUtil.DBUtil(p));

        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));
        middleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("middlename"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("date"));


        add.setOnAction(event -> {
            //usersData.add(new User(2,"tyty","ytyty","tytyty"));

            //tableUsers.setItems(usersData);


        });
    }

}