package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Database;
import sample.Main;


import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class RegisterPageController implements Initializable {

    @FXML
    private ImageView lockImage;

    @FXML
    private ImageView phoneImage;

    @FXML
    private ImageView userIamge;

    @FXML
    private Button cancelButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label restoreLBL;

    private void closeStage(){
        ((Stage)cancelButton.getScene().getWindow()).close();
        LoginPageController.registerPage = null;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File phoneFile = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\phone (2).png");
        Image phoneeImage = new Image(phoneFile.toURI().toString());
        phoneImage.setImage(phoneeImage);

        File userFile = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\user.png");
        Image useImage = new Image(userFile.toURI().toString());
        userIamge.setImage(useImage);

        File lockFile = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\lock.png");
        Image logImage = new Image(lockFile.toURI().toString());
        lockImage.setImage(logImage);

        cancelButton.setOnAction(event -> {
            closeStage();
        });

    }

    @FXML
    void register(ActionEvent event) throws Exception {
        if (checkAllFields()){
            if (uniqueUsername()){
                Connection connection = Database.getConnection();
                String pass = Main.getMd5(passwordField.getText());
                String sql = "INSERT INTO users(name, lastname, username, password, phone) VALUES(?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, nameField.getText());
                ps.setString(2, lastnameField.getText());
                ps.setString(3, usernameField.getText());
                ps.setString(4, pass);
                ps.setString(5, phoneField.getText());
                ps.executeUpdate();
                this.getClass();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }else {
                errorLabel.setText("username or phone number already exist.");
            }
        }else {
                errorLabel.setText("please fill up all the fields");
        }
    }

    boolean checkAllFields(){
        if  (usernameField.getText().isBlank()
                || passwordField.getText().isBlank()
                || nameField.getText().isBlank()
                || lastnameField.getText().isBlank()
                || phoneField.getText().isBlank()){
            return false;
        }
        return true;
    }

    boolean uniqueUsername() throws Exception {
        Connection connection = Database.getConnection();
        String sql  = "SELECT * FROM users WHERE username = '"+usernameField.getText()+"' or phone = '"+phoneField.getText()+"'";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return false;
        }
        return true;
    }



}
