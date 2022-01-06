package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Restore implements Initializable {

    public static String username;

    @FXML
    private TextField phoneTF;

    @FXML
    private Button resetTF;

    @FXML
    private TextField userTF;

    @FXML
    private Label errorLBL;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetTF.setOnAction(event -> {
            if (userTF.getText().isBlank() || phoneTF.getText().isBlank()) {
                errorLBL.setText("fill up all fields good boy/girl!");
            } else {
                try {
                    if (checkInformation()) {
                        username = userTF.getText();
                        goToResetPass();
                        errorLBL.getScene().getWindow().hide();
                    } else {
                        errorLBL.setText("can't find desired user.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean checkInformation() throws Exception {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM users WHERE username = '" + userTF.getText() + "' AND phone = '" + phoneTF.getText() + "'";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet set = ps.executeQuery();
        if (set.next()) return true;
        return false;
    }

    private void goToResetPass(){
        Parent root = null;
        Stage primaryStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("../View/ResetPass.fxml"));
            primaryStage.setTitle("restore");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
