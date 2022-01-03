package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Database;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AddGroupController implements Initializable {

    @FXML
    private TextField addGroupTextField;

    @FXML
    private Button createGroupButton;

    @FXML
    private Label errorLBL;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createGroupButton.setOnAction(event -> {
            if (createGroupButton.getText().isBlank() == false){
                try {
                    if (checkGroupName()){
                        addGoupToDatabase();
                        errorLBL.getScene().getWindow().hide();
                    }else {
                        errorLBL.setText("group name not available");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                errorLBL.setText("fill up the field");
            }
        });
    }

    public boolean checkGroupName() throws Exception {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM groups WHERE group_name = '"+addGroupTextField.getText()+"' ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet set = ps.executeQuery();
        if (set.next()) return false;
        return true;
    }

    public void addGoupToDatabase() throws Exception {
        Connection connection = Database.getConnection();
        String sql = "INSERT INTO groups(group_name) VALUES(?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, addGroupTextField.getText());
        ps.executeUpdate();
    }
}
