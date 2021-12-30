package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.Database;
import sample.Model.Users;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    public String loggedUser;
    private String selectedUser = null;

    @FXML
    private VBox MessageHolder;

    @FXML
    private VBox MessageHolder2;

    @FXML
    private ScrollPane SCROLL_BAR;

    @FXML
    private TextField inputTxt;

    @FXML
    private ImageView sendMsg;

    @FXML
    private Label caraLBL;

    @FXML
    private VBox sideVbox;

    @FXML
    private ImageView allChatsSide;

    @FXML
    private ImageView channelChatsSide;

    @FXML
    private ImageView groupsChatsSide;

    @FXML
    private ImageView privateChatsSide;

    @FXML
    private ImageView profileSide;

    @FXML
    private ImageView attach;

    @FXML
    private ImageView emojy;

    @FXML
    void clickedSend(MouseEvent event) throws Exception {
        Connection connection = Database.getConnection();

        Label label = new Label();
        label.setAlignment(Pos.BOTTOM_RIGHT);
        label.setStyle("padding: 10px, 50px, 10px, 10px; -fx-background-radius: 10; -fx-font-size: 20px; -fx-border-color: #4022d6; -fx-border-radius: 30; -fx-font-family: 'Century Schoolbook'");
        label.setPadding(new Insets(10));
        label.setText(inputTxt.getText());
        label.wrapTextProperty();
        label.setMinWidth(550);
        label.setMaxWidth(550);

        if (inputTxt.getText().isBlank() == false){
            if (selectedUser != null) {
                String sql = "INSERT INTO private_message(message_text, message_from, message_to) VALUES(?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, label.getText());
                ps.setString(2, LoginPageController.loggedUser);
                ps.setString(3, selectedUser);
                ps.executeUpdate();
                MessageHolder.getChildren().add(label);
                inputTxt.setText("");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        File logFile = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\allChatsSide.png");
        Image logImage = new Image(logFile.toURI().toString());
        allChatsSide.setImage(logImage);

        File logFile1 = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\users1.png");
        Image logImage1 = new Image(logFile1.toURI().toString());
        privateChatsSide.setImage(logImage1);

        File logFile2 = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\group1.png");
        Image logImage2 = new Image(logFile2.toURI().toString());
        groupsChatsSide.setImage(logImage2);

        File logFile3 = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\channelChatsSide.png");
        Image logImage3 = new Image(logFile3.toURI().toString());
        channelChatsSide.setImage(logImage3);

        File logFile4 = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\profileSide.png");
        Image logImage4 = new Image(logFile4.toURI().toString());
        profileSide.setImage(logImage4);

        File logFile5 = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\emojy.png");
        Image logImage5 = new Image(logFile5.toURI().toString());
        emojy.setImage(logImage5);

        File logFile6 = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\attach.png");
        Image logImage6 = new Image(logFile6.toURI().toString());
        attach.setImage(logImage6);

        File logFile7 = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\sendMsg.png");
        Image logImage7 = new Image(logFile7.toURI().toString());
        sendMsg.setImage(logImage7);


        try {
            showUsersList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // show users in listview

    void showUsersList() throws Exception {
        File file = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\sendMsg.png");
        Image image = new Image(file.toURI().toString());

        ListView<String> listView = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList ();

        ArrayList<Users> users = getUsers();

        for (int i = 0; i < users.size(); i++) {
            items.add(users.get(i).getUsername());
        }

        listView.setItems(items);


        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView1 = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                imageView1.setImage(image);
                setText(name);
                setGraphic(imageView1);
                setFont(Font.font(20));
                setAlignment(Pos.CENTER_LEFT);

                setOnMouseClicked(mouseEvent -> {
                    ObservableList selected = listView.getSelectionModel().getSelectedItems();
                    for(Object o : selected){
                        try {
                            selectedUser = users.get(getIndex()).getUsername();
                            showChatHistory(LoginPageController.loggedUser, users.get(getIndex()).getUsername());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                setStyle("-fx-background-color:  linear-gradient(to top right,  #c9ebf5, #f5c1f3); -fx-background-insets: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, purple, 10, 0, 0, 0)");
            }
        });


        sideVbox.getChildren().add(listView);
        sideVbox.setAlignment(Pos.CENTER);

    }

    private ArrayList<Users> getUsers() throws Exception {
        ArrayList<Users> users = new ArrayList<>();
        Connection connection = Database.getConnection();
        String sql = "SELECT username FROM users";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            users.add(new Users(resultSet.getString("username")));
        }
        return users;
    }

    void showChatHistory(String from, String to) throws Exception {
        MessageHolder.getChildren().removeAll(MessageHolder.getChildren());
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM private_message WHERE message_from = '"+from+"' AND message_to = '"+to+"' OR message_from = '"+to+"' AND message_to = '"+from+"' ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            Label label = new Label();
            label.setMinWidth(550);
            label.setMaxWidth(550);
            label.setText(inputTxt.getText());
            label.setAlignment(Pos.BOTTOM_LEFT);
            label.setMinWidth(Region.USE_PREF_SIZE);
            label.wrapTextProperty();


            MessageHolder.setAlignment(Pos.BOTTOM_RIGHT);
            MessageHolder.setSpacing(20);

            if (resultSet.getString("message_from").equals(from) && resultSet.getString("message_to").equals(to)){
                String text = resultSet.getString("message_text");
                label.setText(text);
                label.setAlignment(Pos.BOTTOM_RIGHT);
                label.setStyle("padding: 10px, 50px, 10px, 10px; -fx-background-radius: 10; -fx-font-size: 20px; -fx-border-color: #4022d6; -fx-border-radius: 30; -fx-font-family: 'Century Schoolbook'");

                label.setPadding(new Insets(10));
                MessageHolder.getChildren().add(label);
            }
            else if (resultSet.getString("message_to").equals(from) && resultSet.getString("message_from").equals(to)){
                String text = resultSet.getString("message_text");
                label.setText(text);
                label.setAlignment(Pos.BOTTOM_LEFT);
                label.setStyle("padding: 10px, 50px, 10px, 10px; -fx-background-radius: 10; -fx-font-size: 20px; -fx-border-color: #4022d6; -fx-border-radius: 30; -fx-font-family: 'Century Schoolbook'");

                label.setPadding(new Insets(10));
                MessageHolder.getChildren().add(label);
            }

        }
    }


}
