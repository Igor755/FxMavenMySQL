package com.metlin.mavenfxmysql.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {



    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/fxsql.fxml"));
        stage.setTitle("PEOPLE APPLICATION");
        stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
        stage.show();
    }
    public static void main(String[] args)  {
        launch(args);
    }
}
