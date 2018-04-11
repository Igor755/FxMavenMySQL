package com.metlin.mavenfxmysql.Controller;


import com.metlin.mavenfxmysql.util.DBUtil;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;


public class MainController {

    public  String p;

    @FXML
    private Button button_connect;

    @FXML
    private TextArea textArea;

    @FXML
    public void initialize(){
        button_connect.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent){

                textArea.setText(DBUtil.DBUtil(p));


            }
        });
    }

}
