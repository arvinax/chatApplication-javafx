package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/LoginPage.fxml"));
        primaryStage.setTitle("Chatcore");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.getIcons().add(new Image("C:\\Users\\Justim\\Downloads\\promotional.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static String getMd5(String input)
    {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
