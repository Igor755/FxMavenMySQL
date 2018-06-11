package com.metlin.mavenfxmysql.Controller;


import com.metlin.mavenfxmysql.People.User;

import com.metlin.mavenfxmysql.util.DBUtil;
import com.mysql.jdbc.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private TextField military_rank;


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

    @FXML
    private TableColumn<User, String> RankColumn;


    private ObservableList<User> usersData = FXCollections.observableArrayList();
    PreparedStatement preparedStatement = null;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {


        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<User, String>("last"));
        middleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("middle"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("birth"));
        RankColumn.setCellValueFactory(new PropertyValueFactory<User, String>("rank"));
        loaddate();
        textArea.setText("LOAD BASE SUCSESFULLY");


    }

    public void loaddate() {

        try {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String date = "";
            java.sql.Date sd;

            first_name.clear();
            last_name.clear();
            middle_name.clear();
            date_birth.setValue(null);
            military_rank.clear();


            usersData.clear();


            Connection connection = (Connection) DBUtil.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM people.warrior");

            while (resultSet.next()) {


                usersData.add(new User(resultSet.getString("name"),
                        resultSet.getString("last"),
                        resultSet.getString("middle"),
                        date = dateFormat.format(resultSet.getDate("birth")),//java sql date
                        resultSet.getString("rank")));

            }

            tableUsers.setItems(usersData);


        } catch (SQLException e) {
            System.out.println("DON'T LOAD DATA");
            System.out.println(e);
            textArea.setText("DON'T LOAD DATA");


        }

    }


    @FXML
    public void addUser() throws SQLException {


        String name = first_name.getText();
        String last = last_name.getText();
        String middle = middle_name.getText();
        String birth = String.valueOf(date_birth.getValue());
        String rank = military_rank.getText();

        String sql = "INSERT into people.warrior (name,last,middle,birth,rank) Values (?,?,?,?,?)";

        preparedStatement = null;

        if ((name.isEmpty()) | (last.isEmpty()) | (middle.isEmpty()) | (rank.isEmpty()) | (rank.matches("^[А-Я]{1}[-]{1}[0-9]{6}$") == false)) {

            textArea.setText("PLEASE FILL ALL FIELDS OR " +
                    "Rank must be [А-Я]{1}[-]{1}[0-9]{6}");

        } else {

            try {


                Connection connection = (Connection) DBUtil.getConnection();
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, last);
                preparedStatement.setString(3, middle);
                preparedStatement.setString(4, birth);
                preparedStatement.setString(5, rank);


                textArea.setText("USER ADDED");


            } catch (SQLException e) {
                textArea.setText(String.valueOf(e));
                System.out.println(e);

            } finally {
                preparedStatement.execute();
                preparedStatement.close();
            }

            loaddate();

        }
    }


    static String tempUsername;

    @FXML
    public void showOnClick() {


        DateFormat formatter2 = new SimpleDateFormat("dd.MM.yyyy");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            add.setDisable(true);
            User user = (User) tableUsers.getSelectionModel().getSelectedItem();
            String sql = "select * from people.warrior";
            Connection connection = (Connection) DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            tempUsername = user.getName();
            first_name.setText(user.getName());
            last_name.setText(user.getLast());
            middle_name.setText(user.getMiddle());
            date_birth.setValue(LocalDate.parse(user.getBirth(), formatter));
            military_rank.setText(user.getRank());

            textArea.setText("YOU SELECT" + " " + user.getName());

            preparedStatement.close();


        } catch (SQLException e) {
            System.out.println(e);
            textArea.setText(String.valueOf(e));
        }
    }

    @FXML
    public void deleteUser() {

        try {
            User user = (User) tableUsers.getSelectionModel().getSelectedItem();
            String sql = "delete from people.warrior where name=? and last=? and middle=?";
            Connection connection = (Connection) DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLast());
            preparedStatement.setString(3, user.getMiddle());
            preparedStatement.executeUpdate();
            textArea.setText("USER WAS DELETED");


            preparedStatement.close();


        } catch (SQLException e) {
            System.out.println(e);
            textArea.setText(String.valueOf(e));
        } catch (NullPointerException e) {
            textArea.setText("PLEASE SELECT USER FOR DELETE");
        }
        loaddate();

    }

    @FXML
    public void UpdateUser() {
        String sql = "update people.warrior set name=?, last=?, middle=?, birth=?, rank=? where name='" + tempUsername + "'";


        try {
            Connection connection = (Connection) DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, first_name.getText());
            preparedStatement.setString(2, last_name.getText());
            preparedStatement.setString(3, middle_name.getText());
            preparedStatement.setDate(4, java.sql.Date.valueOf(date_birth.getValue()));



            //if    military_rank.getText().matches("^[А-Я]{1}[-]{1}[0-9]{6}$") == false));

            preparedStatement.setString(5, military_rank.getText());

            preparedStatement.execute();
            preparedStatement.close();
            loaddate();
            textArea.setText("USER" + " " + first_name.getText() + " " + "UPDATE");
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    @FXML
    public void CreateUser() {

        first_name.clear();
        last_name.clear();
        middle_name.clear();
        date_birth.setValue(null);
        military_rank.clear();

        usersData.clear();
        add.setDisable(false);
        loaddate();


    }


}


