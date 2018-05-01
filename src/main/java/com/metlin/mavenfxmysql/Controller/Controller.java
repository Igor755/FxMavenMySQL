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
    PreparedStatement preparedStatement = null;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {


        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<User, String>("last"));
        middleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("middle"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<User, String>("birth"));
        loaddate();


    }

    public void loaddate() {

        try {

            first_name.clear();
            last_name.clear();
            middle_name.clear();
            date_birth.getEditor().clear();


            usersData.clear();


            Connection connection = (Connection) DBUtil.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM people.warrior");

            while (resultSet.next()) {


                usersData.add(new User(resultSet.getString("name"),
                        resultSet.getString("last"),
                        resultSet.getString("middle"),
                        resultSet.getString("birth")));
            }


        } catch (SQLException e) {
            System.out.println("DON'T LOAD DATA");
            textArea.setText("DON'T LOAD DATA");
        }
        textArea.setText("LOAD BASE Succsesfully");

        tableUsers.setItems(usersData);


    }

    @FXML
    public void addUser(ActionEvent event) throws SQLException {

        String sql = "INSERT into people.warrior (name,last,middle,birth) Values (?,?,?,?)";

        String name = first_name.getText();
        String last = last_name.getText();
        String middle = middle_name.getText();
        LocalDate birth = date_birth.getValue();
        preparedStatement = null;

        try {

            Connection connection = (Connection) DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, last);
            preparedStatement.setString(3, middle);
            preparedStatement.setString(4, String.valueOf(birth));

            textArea.setText("User Added");

        } catch (SQLException e) {
            textArea.setText(String.valueOf(e));
            System.out.println(e);

        } finally {
            preparedStatement.execute();
            preparedStatement.close();
        }

        loaddate();


    }

    @FXML
    public void showOnClick(){

        try{
            User user = (User)tableUsers.getSelectionModel().getSelectedItem();
            String sql = "select * from people.warrior";
            Connection connection = (Connection) DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            first_name.setText(user.getName());
            last_name.setText(user.getLast());
            middle_name.setText(user.getMiddle());
            date_birth.setValue(LocalDate.parse(user.getBirth()));

            preparedStatement.close();


        }
        catch (SQLException e)
        {
            System.out.println(e);
            textArea.setText(String.valueOf(e));
        }
    }
    @FXML
    public void deleteUser(){

        try{
            User user = (User)tableUsers.getSelectionModel().getSelectedItem();
            String sql = "delete * from people.warrior where first_name=?";
            Connection connection = (Connection) DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getName());
            preparedStatement.executeUpdate();



            preparedStatement.close();


        }
        catch (SQLException e)
        {
            System.out.println(e);
            textArea.setText(String.valueOf(e));
        }
        loaddate();

    }


}

