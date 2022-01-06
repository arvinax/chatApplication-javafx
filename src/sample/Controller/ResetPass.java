package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import sample.Database;
import sample.Main;

import java.lang.ref.PhantomReference;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ResetPass implements Initializable {



    @FXML
    private PasswordField passTF;

    @FXML
    private Button resetButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetButton.setOnAction(event -> {
            resetButton.setOnAction(event1 -> {
                if (passTF.getText().isBlank() == false){
                    try {
                        updatePass();
                        resetButton.getScene().getWindow().hide();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    private void updatePass() throws Exception {
        Connection connection = Database.getConnection();
        String pass = Main.getMd5(passTF.getText());
        String sql = "UPDATE users SET password = '"+pass+"' WHERE username = '"+Restore.username+"'";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();
    }
}
