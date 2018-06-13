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
    private TextField personal_number;

    @FXML
    private Spinner<String> military_spiner = new Spinner<String>();

    @FXML
    private DatePicker date_order;


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
    private TableColumn<User, String> personalNumberColumn;

    @FXML
    private TableColumn<User, String> militaryRankColumn;

    @FXML
    private TableColumn<User, Date> dateOrderColumn;


    private ObservableList<User> usersData = FXCollections.observableArrayList();
    PreparedStatement preparedStatement = null;

    ObservableList<String> rank = FXCollections.observableArrayList("",
            "Младший сержант", "Сержант", "Старший сержант", "Мичман", "Прапорщик",
            "Старший прапорщик", "Младший лейтенант", "Лейтенант", "Старший лейтенант",
            "Капитан", "Майор", "Подполковник", "Полковник", "Генерал-майор",
            "Генерал-лейтенант", "Генерал-полковник", "Генерал армии", "Маршал Российской Федерации");

    SpinnerValueFactory<String> valueFactory =
            new SpinnerValueFactory.ListSpinnerValueFactory<String>(rank);

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        military_spiner.setValueFactory(valueFactory);


        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<User, String>("last"));
        middleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("middle"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("birth"));
        personalNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("personalnumber"));
        militaryRankColumn.setCellValueFactory(new PropertyValueFactory<User, String>("militaryrank"));
        dateOrderColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("orderdate"));

        loaddate();
        textArea.setText("LOAD BASE SUCSESFULLY");


    }

    public void loaddate() {

        try {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String date = "";
            String dateord = "";
            java.sql.Date sd;

            first_name.clear();
            last_name.clear();
            middle_name.clear();
            date_birth.setValue(null);
            personal_number.clear();
            military_spiner.getEditor().setText("");
            date_order.setValue(null);


            usersData.clear();


            Connection connection = (Connection) DBUtil.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM people.warrior");

            while (resultSet.next()) {


                usersData.add(new User(resultSet.getString("name"),
                        resultSet.getString("last"),
                        resultSet.getString("middle"),
                        date = dateFormat.format(resultSet.getDate("birth")),//java sql date
                        resultSet.getString("personalnumber"),
                        resultSet.getString("militaryrank"),
                        dateord = dateFormat.format(resultSet.getDate("orderdate"))));

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
        String personalnumber = personal_number.getText();
        String militaryrank = military_spiner.getEditor().getText();
        String orderdate = String.valueOf(date_order.getValue());


        String sql = "INSERT into people.warrior (name,last,middle,birth,personalnumber,militaryrank,orderdate) Values (?,?,?,?,?,?,?)";

        preparedStatement = null;

        if ((name.isEmpty()) |
                (last.isEmpty()) |
                (middle.isEmpty()) |
                (birth.isEmpty()) |
                (personalnumber.isEmpty()) |
                (personalnumber.matches("^[А-Я]{1}[-]{1}[0-9]{6}$") == false) |
                (militaryrank.isEmpty()) | (orderdate.isEmpty())) {

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
                preparedStatement.setString(5, personalnumber);
                preparedStatement.setString(6, militaryrank);
                preparedStatement.setString(7, orderdate);


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
            personal_number.setText(user.getPersonalnumber());
            military_spiner.getEditor().setText(user.getMilitaryrank());
            date_order.setValue(LocalDate.parse(user.getOrderdate(), formatter));


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

            String sql = "delete from people.warrior where name=? and last=? and middle=? and birth=? and personalnumber=?";
            Connection connection = (Connection) DBUtil.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLast());
            preparedStatement.setString(3, user.getMiddle());
            preparedStatement.setDate(4, java.sql.Date.valueOf(date_birth.getValue()));
            preparedStatement.setString(5, user.getPersonalnumber());


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


        String name = first_name.getText();
        String last = last_name.getText();
        String middle = middle_name.getText();
        String birth = String.valueOf(date_birth.getValue());
        String personalnumber = personal_number.getText();
        String militaryrank = military_spiner.getEditor().getText();
        String orderdate = String.valueOf(date_order.getValue());


        String sql = "update people.warrior set name=?, last=?, middle=?, birth=?, personalNumber=?, militaryrank=?, orderdate=? where name='" + tempUsername + "'";


        if ((name.isEmpty()) | (last.isEmpty()) | (middle.isEmpty()) | (personalnumber.isEmpty()) | (personalnumber.matches("^[А-Я]{1}[-]{1}[0-9]{6}$") == false)) {

            textArea.setText("PLEASE FILL ALL FIELDS OR " +
                    "Rank must be [А-Я]{1}[-]{1}[0-9]{6}");

        } else {


            try {
                Connection connection = (Connection) DBUtil.getConnection();
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, first_name.getText());
                preparedStatement.setString(2, last_name.getText());
                preparedStatement.setString(3, middle_name.getText());
                preparedStatement.setDate(4, java.sql.Date.valueOf(date_birth.getValue()));
                preparedStatement.setString(5, personal_number.getText());
                preparedStatement.setString(6, military_spiner.getEditor().getText());
                preparedStatement.setDate(7, java.sql.Date.valueOf(date_order.getValue()));


                preparedStatement.execute();
                preparedStatement.close();
                loaddate();
                military_spiner.getEditor().setText("");

                textArea.setText("USER" + " " + first_name.getText() + " " + "UPDATE");
            } catch (SQLException e) {
                System.out.println(e);
            }

        }
    }

    @FXML
    public void CreateUser() {

        first_name.clear();
        last_name.clear();
        middle_name.clear();
        date_birth.setValue(null);
        personal_number.clear();
        military_spiner.getEditor().setText("");
        date_order.setValue(null);

        usersData.clear();
        add.setDisable(false);
        loaddate();


    }


}


