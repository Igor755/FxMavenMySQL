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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    private TextArea textArea;


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    private TextField number_unit;
    @FXML
    private TextField last_name;
    @FXML
    private TextField first_name;
    @FXML
    private TextField middle_name;
    @FXML
    private DatePicker date_birth;
    @FXML
    private TextField personal_number;
    @FXML
    private Spinner<String> military_spiner = new Spinner<String>();
    @FXML
    private TextField military_post;




////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    private Spinner<String> nomr_spiner = new Spinner<String>();
    @FXML
    private DatePicker nomr_date;



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    private TableView<User> tableUsers;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, Integer> numberUnitColumn;
    @FXML
    private TableColumn<User, String> lastColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> middleColumn;
    @FXML
    private TableColumn<User, Date> DateColumn;
    @FXML
    private TableColumn<User, String> personalNumberColumn;
    @FXML
    private TableColumn<User, String> militaryRankColumn;
    @FXML
    private TableColumn<User, String> militaryPostColumn;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableColumn<User, String> nomrColumn;
    @FXML
    private TableColumn<User, String> prikazNomrColumn;
    @FXML
    private TableColumn<User, Date> dateNomrColumn;


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ObservableList<String> nomr = FXCollections.observableArrayList("",
            "Приказ МО РФ", "Пригаз НГУ", "Приказ КЧ 52", "Приказ КЧ 27");

    SpinnerValueFactory<String> valueFactory_nomr =
            new SpinnerValueFactory.ListSpinnerValueFactory<String>(nomr);


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        military_spiner.setValueFactory(valueFactory);
        nomr_spiner.setValueFactory(valueFactory_nomr);


        numberUnitColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("numberunit"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<User, String>("last"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        middleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("middle"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("birth"));
        personalNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("personalnumber"));
        militaryRankColumn.setCellValueFactory(new PropertyValueFactory<User, String>("militaryrank"));



        prikazNomrColumn.setCellValueFactory(new PropertyValueFactory<User, String>("nomrspiner"));
        dateNomrColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("nomrdate"));

        militaryPostColumn.setCellValueFactory(new PropertyValueFactory<User, String>("militarypost"));




        Tooltip tooltip_personal_number = new Tooltip("\n" +
                "This field includes:\n " +
                "The first letter character from А to Я, \n" +
                "the second character is a dash and \n" +
                "6 digits");

        Tooltip tooltip_number_unit = new Tooltip("\n" +
                "This field includes: \n" +
                "one, two, three, four or five digits");


        personal_number.setTooltip(tooltip_personal_number);
        number_unit.setTooltip(tooltip_number_unit);


        loaddate();
        textArea.setText("LOAD BASE SUCSESFULLY");


    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void loaddate() {

        try {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String date = "";
            String dateord = "";
            java.sql.Date sd;


            number_unit.clear();
            last_name.clear();
            first_name.clear();
            middle_name.clear();
            date_birth.setValue(null);
            personal_number.clear();
            military_spiner.getEditor().setText("");




            nomr_spiner.getEditor().setText("");
            nomr_date.setValue(null);

            military_post.clear();





            usersData.clear();


            Connection connection = (Connection) DBUtil.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM people.warrior");

            while (resultSet.next()) {


                usersData.add(new User(resultSet.getInt("numberunit"),
                        resultSet.getString("last"),
                        resultSet.getString("name"),
                        resultSet.getString("middle"),//java sql date
                        date = dateFormat.format(resultSet.getDate("birth")),//java sql date
                        resultSet.getString("personalnumber"),
                        resultSet.getString("militaryrank"),
                        resultSet.getString("nomrspiner"),
                        dateord = dateFormat.format(resultSet.getDate("nomrdate")),
                        resultSet.getString("militarypost")));//java sql date));

            }



            tableUsers.setItems(usersData);


        } catch (SQLException e) {
            System.out.println("DON'T LOAD DATA");
            System.out.println(e);
            textArea.setText("DON'T LOAD DATA");


        }

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    public void addUser() throws SQLException {


        String numberunit = number_unit.getText();
        String last = last_name.getText();
        String name = first_name.getText();
        String middle = middle_name.getText();
        String birth = String.valueOf(date_birth.getValue());
        String personalnumber = personal_number.getText();
        String militaryrank = military_spiner.getEditor().getText();



        String nomrspiner = nomr_spiner.getEditor().getText();
        String nomrdate = String.valueOf(nomr_date.getValue());

        String militarypost = military_post.getText();


        String sql = "INSERT into people.warrior (numberunit,last,name,middle,birth,personalnumber,militaryrank,nomrspiner,nomrdate,militarypost) Values (?,?,?,?,?,?,?,?,?,?)";

        preparedStatement = null;

        if ((numberunit.isEmpty()) |
                (numberunit.matches("^[0-9]{1,5}$") == false) |
                (last.isEmpty()) |
                (name.isEmpty()) |
                (middle.isEmpty()) |
                (birth.isEmpty()) |
                (personalnumber.isEmpty()) |
                (personalnumber.matches("^[А-Я]{1}[-]{1}[0-9]{6}$") == false) |
                (militaryrank.isEmpty()) |
                (nomrspiner.isEmpty()) |
                (nomrdate.isEmpty()) |
                (militarypost.isEmpty()))


        {


            textArea.setText("PLEASE FILL ALL FIELDS OR \n" +
                    "Rank must be [А-Я]{1}[-]{1}[0-9]{6} OR \n" +
                    "Numberunit must be [0-9]{1,5}");

        } else {

            try {


                Connection connection = (Connection) DBUtil.getConnection();
                preparedStatement = connection.prepareStatement(sql);






                preparedStatement.setString(1, numberunit);
                preparedStatement.setString(2, last);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, middle);
                preparedStatement.setString(5, birth);
                preparedStatement.setString(6, personalnumber);
                preparedStatement.setString(7, militaryrank);
                preparedStatement.setString(8, nomrspiner);
                preparedStatement.setString(9, nomrdate);
                preparedStatement.setString(10,militarypost);



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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

            number_unit.setText(String.valueOf(user.getNumberunit()));
            last_name.setText(user.getLast());
            first_name.setText(user.getName());
            middle_name.setText(user.getMiddle());
            date_birth.setValue(LocalDate.parse(user.getBirth(), formatter));
            personal_number.setText(user.getPersonalnumber());
            military_spiner.getEditor().setText(user.getMilitaryrank());


            nomr_spiner.getEditor().setText(user.getNomrspiner());
            nomr_date.setValue(LocalDate.parse(user.getNomrdate(), formatter));

            military_post.setText(user.getMilitarypost());






            textArea.setText("YOU SELECT" + " " + user.getName());

            preparedStatement.close();


        } catch (SQLException e) {
            System.out.println(e);
            textArea.setText(String.valueOf(e));
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    public void deleteUser() {

        try {
            User user = (User) tableUsers.getSelectionModel().getSelectedItem();

            String sql = "delete from people.warrior where numberunit=? and last=? and name=? and middle=? and birth=? and personalnumber=? and militaryrank=? and nomrspiner=? and nomrdate=? and militarypost=?";
            Connection connection = (Connection) DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);



            preparedStatement.setString(1, String.valueOf(user.getNumberunit()));
            preparedStatement.setString(2, user.getLast());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getMiddle());
            preparedStatement.setDate(5, java.sql.Date.valueOf(date_birth.getValue()));
            preparedStatement.setString(6, user.getPersonalnumber());
            preparedStatement.setString(7, user.getMilitaryrank());


            preparedStatement.setString(8, user.getNomrspiner());
            preparedStatement.setDate(9, java.sql.Date.valueOf(nomr_date.getValue()));

            preparedStatement.setString(10,user.getMilitarypost());



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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @FXML
    public void UpdateUser() {


        String numberunit = number_unit.getText();
        String last = last_name.getText();
        String name = first_name.getText();
        String middle = middle_name.getText();
        String birth = String.valueOf(date_birth.getValue());
        String personalnumber = personal_number.getText();
        String militaryrank = military_spiner.getEditor().getText();

        String nomrspiner = nomr_spiner.getEditor().getText();
        String nomrdate = String.valueOf(nomr_date.getValue());

        String militarypost = military_post.getText();



        String sql = "update people.warrior set numberunit=?, last=?, name=?, middle=?, birth=?, personalNumber=?, militaryrank=?, nomrspiner=?, nomrdate=?, militarypost=? where name='" + tempUsername + "'";


        if ((numberunit.isEmpty()) |
                (numberunit.matches("^[0-9]{1,5}$") == false) |
                (last.isEmpty()) |
                (name.isEmpty()) |
                (middle.isEmpty()) |
                (birth.isEmpty()) |
                (personalnumber.isEmpty()) |
                (personalnumber.matches("^[А-Я]{1}[-]{1}[0-9]{6}$") == false) |
                (militaryrank.isEmpty()) |
                (nomrspiner.isEmpty()) |
                (nomrdate.isEmpty()) |
                (militarypost.isEmpty()))


        {

            textArea.setText("PLEASE FILL ALL FIELDS OR \n" +
                    "Rank must be [А-Я]{1}[-]{1}[0-9]{6} OR \n" +
                    "Numberunit must be [0-9]{1,5}");

        } else {


            try {
                Connection connection = (Connection) DBUtil.getConnection();
                preparedStatement = connection.prepareStatement(sql);


                preparedStatement.setString(1, number_unit.getText());
                preparedStatement.setString(2, last_name.getText());
                preparedStatement.setString(3, first_name.getText());
                preparedStatement.setString(4, middle_name.getText());
                preparedStatement.setDate(5, java.sql.Date.valueOf(date_birth.getValue()));
                preparedStatement.setString(6, personal_number.getText());
                preparedStatement.setString(7, military_spiner.getEditor().getText());

                preparedStatement.setString(8, nomr_spiner.getEditor().getText());
                preparedStatement.setDate(9, java.sql.Date.valueOf(nomr_date.getValue()));

                preparedStatement.setString(10, military_post.getText());


                preparedStatement.execute();
                preparedStatement.close();
                loaddate();
                military_spiner.getEditor().setText("");
                nomr_spiner.getEditor().setText("");


                textArea.setText("USER" + " " + first_name.getText() + " " + "UPDATE");
            } catch (SQLException e) {
                System.out.println(e);
            }

        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    public void CreateUser() {


        number_unit.clear();
        last_name.clear();
        first_name.clear();
        middle_name.clear();
        date_birth.setValue(null);
        personal_number.clear();
        military_spiner.getEditor().setText("");

        nomr_spiner.getEditor().setText("");
        nomr_date.setValue(null);

        military_post.clear();



        usersData.clear();
        add.setDisable(false);
        loaddate();


    }


}


