package sample.Controller;


import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Database;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    private Button signUpButton;

    @FXML
    private ImageView loginImage;

    @FXML
    private PasswordField enterPasswordField;

    @FXML
    private TextField enterUsernameTF;

    @FXML
    private Button loginButton;

    @FXML
    private Label validationMessageLB;


    static Stage registerPage = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        File logFile = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\loginImage.jpg");
        Image logImage = new Image(logFile.toURI().toString());
        loginImage.setImage(logImage);


        RotateTransition transition = new RotateTransition();
        transition.setNode(loginImage);
        transition.setDuration(Duration.millis(3000));
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.setByAngle(360);
        transition.play();

        signUpButton.setOnMouseClicked(mouseEvent -> {
            try {
                goToSignUpPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }


    public void goToSignUpPage() throws IOException {
        if (registerPage == null){
            Parent root = FXMLLoader.load(this.getClass().getResource("../View/RegisterPage.fxml"));
            registerPage = new Stage();
            registerPage.setTitle("Sign Up");
            registerPage.setScene(new Scene(root));
            registerPage.show();
        }

    }



    @FXML
    void back(MouseEvent event) {

    }

    @FXML
    void closeclick(MouseEvent event) {
        ((Stage)((Circle)event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void login(ActionEvent event) throws Exception {

        if (checkLoginFields()){
            if (confirmLogin(enterUsernameTF.getText(), enterPasswordField.getText())){
                validationMessageLB.setText("welcome");
                validationMessageLB.setTextFill(Color.GREEN);
            }else {
                validationMessageLB.setText("incorrect info");
                validationMessageLB.setTextFill(Color.RED);
            }
        }else{
            validationMessageLB.setText("Eat it!!");
            validationMessageLB.setTextFill(Color.RED);
        }
        validationMessageLB.setAlignment(Pos.CENTER);
    }


    static boolean confirmLogin(String username, String password) throws Exception {
        Connection connection = Database.getConnection();
        String sql  = "SELECT * FROM users WHERE username = '"+username+"' and password = '"+password+"'";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        return resultSet.next();
    }

    boolean checkLoginFields(){
        if (enterUsernameTF.getText().isBlank() || enterUsernameTF.getText().isBlank()){
            return false;
        }
        return true;
    }



    @FXML
    void minclick(MouseEvent event) {
        ((Stage)((Circle)event.getSource()).getScene().getWindow()).setIconified(true);
    }



}
