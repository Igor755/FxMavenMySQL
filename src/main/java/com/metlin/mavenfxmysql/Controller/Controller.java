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
    @FXML
    private TextField nomr_number;


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    private Spinner<String> nomp_spiner = new Spinner<String>();
    @FXML
    private DatePicker nomp_date;
    @FXML
    private TextField nomp_number;



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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableColumn<User, String> nomrColumn;
    @FXML
    private TableColumn<User, String> prikazNomrColumn;
    @FXML
    private TableColumn<User, Date> dateNomrColumn;
    @FXML
    private TableColumn<User, String> numberNomrColumn;


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    private TableColumn<User, String> prikazNompColumn;
    @FXML
    private TableColumn<User, Date> dateNompColumn;
    @FXML
    private TableColumn<User, String> numberNompColumn;




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

    SpinnerValueFactory<String> valueFactory_nomp =
            new SpinnerValueFactory.ListSpinnerValueFactory<String>(nomr);


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        military_spiner.setValueFactory(valueFactory);
        nomr_spiner.setValueFactory(valueFactory_nomr);
        nomp_spiner.setValueFactory(valueFactory_nomp);


        numberUnitColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("numberunit"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<User, String>("last"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        middleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("middle"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("birth"));
        personalNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("personalnumber"));
        militaryRankColumn.setCellValueFactory(new PropertyValueFactory<User, String>("militaryrank"));


        prikazNomrColumn.setCellValueFactory(new PropertyValueFactory<User, String>("nomrspiner"));
        dateNomrColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("nomrdate"));
        numberNomrColumn.setCellValueFactory(new PropertyValueFactory<User, String>("nomrnumber"));

        militaryPostColumn.setCellValueFactory(new PropertyValueFactory<User, String>("militarypost"));

        prikazNompColumn.setCellValueFactory(new PropertyValueFactory<User, String>("nompspiner"));
        dateNompColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("nompdate"));
        numberNompColumn.setCellValueFactory(new PropertyValueFactory<User, String>("nompnumber"));


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
            String datenomp = "";
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
            nomr_number.clear();

            military_post.clear();


            nomp_spiner.getEditor().setText("");
            nomp_date.setValue(null);
            nomp_number.clear();




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
                        resultSet.getString("nomrnumber"),
                        resultSet.getString("militarypost"),
                        resultSet.getString("nompspiner"),
                        datenomp = dateFormat.format(resultSet.getDate("nompdate")),
                        resultSet.getString("nompnumber")));//java sql date));

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
        String nomrnumber = nomr_number.getText();

        String militarypost = military_post.getText();


        String nompspiner = nomp_spiner.getEditor().getText();
        String nompdate = String.valueOf(nomp_date.getValue());
        String nompnumber = nomp_number.getText();





        String sql = "INSERT into people.warrior (numberunit,last,name,middle,birth,personalnumber,militaryrank,nomrspiner,nomrdate,nomrnumber,militarypost,nompspiner,nompdate,nompnumber) Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
                (nomrnumber.isEmpty()) |
                (nomrnumber.matches("^[0-9]{1,4}$") == false) |
                (militarypost.isEmpty()) |
                (nompspiner.isEmpty()) |
                (nompdate.isEmpty()) |
                (nompnumber.isEmpty()) |
                (nompnumber.matches("^[0-9]{1,4}$") == false))


        {


            textArea.setText("PLEASE FILL ALL FIELDS OR \n" +
                    "Numberunit must be [0-9]{1,5} \n" +
                    "Rank must be [А-Я]{1}[-]{1}[0-9]{6} OR \n" +
                    "Nomrnumber must be [0-9]{1,4} OR \n" +
                    "Nompnumber must be [0-9]{1,4}");

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
                preparedStatement.setString(10, nomrnumber);
                preparedStatement.setString(11, militarypost);

                preparedStatement.setString(12, nompspiner);
                preparedStatement.setString(13, nompdate);
                preparedStatement.setString(14, nompnumber);


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
            nomr_number.setText(user.getNomrnumber());

            military_post.setText(user.getMilitarypost());

            nomp_spiner.getEditor().setText(user.getNompspiner());
            nomp_date.setValue(LocalDate.parse(user.getNompdate(), formatter));
            nomp_number.setText(user.getNompnumber());


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

            String sql = "delete from people.warrior where numberunit=? and last=? and name=? and middle=? and birth=? and personalnumber=? and militaryrank=? and nomrspiner=? and nomrdate=? and nomrnumber=? and militarypost=? and nompspiner=? and nompdate=? and nompnumber=?";
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
            preparedStatement.setString(10, user.getNomrnumber());

            preparedStatement.setString(11, user.getMilitarypost());

            preparedStatement.setString(12, user.getNompspiner());
            preparedStatement.setDate(13, java.sql.Date.valueOf(nomp_date.getValue()));
            preparedStatement.setString(14, user.getNompnumber());



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
        String nomrnumber = nomr_number.getText();

        String militarypost = military_post.getText();

        String nompspiner = nomp_spiner.getEditor().getText();
        String nompdate = String.valueOf(nomp_date.getValue());
        String nompnumber = nomp_number.getText();


        String sql = "update people.warrior set numberunit=?, last=?, name=?, middle=?, birth=?, personalNumber=?, militaryrank=?, nomrspiner=?, nomrdate=?, nomrnumber=?, militarypost=?, nompspiner=?, nompdate=?, nompnumber=? where name='" + tempUsername + "'";


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
                (nomrnumber.isEmpty()) |
                (nomrnumber.matches("^[0-9]{1,4}$") == false) |
                (militarypost.isEmpty()) |
                (nompspiner.isEmpty()) |
                (nompdate.isEmpty()) |
                (nompnumber.isEmpty()) |
                (nompnumber.matches("^[0-9]{1,4}$") == false))


        {

            textArea.setText("PLEASE FILL ALL FIELDS OR \n" +
                    "Numberunit must be [0-9]{1,5} \n" +
                    "Rank must be [А-Я]{1}[-]{1}[0-9]{6} OR \n" +
                    "Nomrnumber must be [0-9]{1,4} OR \n" +
                    "Nompnumber must be [0-9]{1,4}");

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
                preparedStatement.setString(10, nomr_number.getText());

                preparedStatement.setString(11, military_post.getText());

                preparedStatement.setString(12, nomp_spiner.getEditor().getText());
                preparedStatement.setDate(13, java.sql.Date.valueOf(nomp_date.getValue()));
                preparedStatement.setString(14, nomp_number.getText());





                preparedStatement.execute();
                preparedStatement.close();
                loaddate();
                military_spiner.getEditor().setText("");
                nomr_spiner.getEditor().setText("");
                nomp_spiner.getEditor().setText("");


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
        nomr_number.clear();

        military_post.clear();


        nomp_spiner.getEditor().setText("");
        nomp_date.setValue(null);
        nomp_number.clear();


        usersData.clear();
        add.setDisable(false);
        loaddate();


    }


}


