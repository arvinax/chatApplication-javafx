package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Database;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Profile implements Initializable {
    @FXML
    private TextField desTF;

    @FXML
    private TextField phoneTF;

    @FXML
    private TextField usernameTF;

    @FXML
    private Button updateBTN;

    @FXML
    private ImageView mainPic;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File logFile = new File("C:\\Users\\Justim\\Desktop\\user.png");
        Image logImage = new Image(logFile.toURI().toString());
        mainPic.setImage(logImage);


        try {
            loadInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateBTN.setOnAction(event -> {

                try {
                    if (usernameTF.getText().isBlank() == false) {
                        if (uniqueUsername()){
                            editUsername();
                            loadInfo();
                        }
                    }
                    if (phoneTF.getText().isBlank() == false){
                        if (uniquePhone()){
                            editPhone();
                            loadInfo();
                        }
                    }
                    if (desTF.getText().isBlank() == false){
                        editDes();
                        loadInfo();
                    }

                    desTF.getScene().getWindow().hide();

                } catch (Exception e) {
                    e.printStackTrace();
                }

        });
    }

    private void loadInfo() throws Exception {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM users WHERE username = '"+LoginPageController.loggedUser+"'";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet set = ps.executeQuery();
        if (set.next()){
            desTF.setPromptText(set.getString("description"));
            usernameTF.setPromptText(set.getString("username"));
            phoneTF.setPromptText(set.getString("phone"));
        }
    }

    private void editUsername() throws Exception {
        Connection connection = Database.getConnection();
        String sql = "UPDATE users SET username = '"+usernameTF.getText()+"' WHERE username = '"+LoginPageController.loggedUser+"' ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();
        connection.close();
    }

    private void editPhone() throws Exception {
        Connection connection = Database.getConnection();
        String sql = "UPDATE users SET phone = '"+phoneTF.getText()+"' WHERE username = '"+LoginPageController.loggedUser+"' ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();
        connection.close();
    }
    private void editDes() throws Exception {
        Connection connection = Database.getConnection();
        String sql = "UPDATE users SET description = '"+desTF.getText()+"' WHERE username = '"+LoginPageController.loggedUser+"' ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();
        connection.close();
    }




    private boolean uniqueUsername() throws Exception {
        Connection connection = Database.getConnection();
        String sql  = "SELECT * FROM users WHERE username = '"+usernameTF.getText()+"' ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            connection.close();
            return false;
        }
        return true;
    }

    private boolean uniquePhone() throws Exception {
        Connection connection = Database.getConnection();
        String sql  = "SELECT * FROM users WHERE username = '"+phoneTF.getText()+"' ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            connection.close();
            return false;
        }
        return true;
    }


}
