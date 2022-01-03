package sample.Controller;

import com.mysql.cj.log.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Database;
import sample.Model.Channels;
import sample.Model.Groups;
import sample.Model.Users;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    public  String loggedUser;
    private String selectedUser = null;
    private String selectedGroup = null;
    private String selectedChannel = null;
    private String selectedSection = null;


    @FXML
    private Button addGroupMemberButton;

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
    private Label topOfChatUsername;

    @FXML
    private ImageView newChannelButton;

    @FXML
    private ImageView newGroupButton;



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
            if (selectedSection.equals("groups")){
                if (selectedGroup != null) {
                    String sql = "INSERT INTO group_message(message_sender, message_text, group_name) VALUES(?,?,?)";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, LoginPageController.loggedUser);
                    ps.setString(2, label.getText());
                    ps.setString(3, selectedGroup);
                    ps.executeUpdate();
                    MessageHolder.getChildren().add(label);
                    inputTxt.setText("");
                }
            } else if (selectedSection.equals("users")){
                if (selectedUser != null) {
                    String sql = "INSERT INTO private_message(message_text, message_from, message_to) VALUES(?,?,?)";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, label.getText());
                    ps.setString(2, LoginPageController.loggedUser);
                    ps.setString(3, selectedGroup);
                    ps.executeUpdate();
                    MessageHolder.getChildren().add(label);
                    inputTxt.setText("");
                }
            }
            else if (selectedSection.equals("channels")){
                if (selectedChannel != null) {
                    if (checkIfChannelAdmin(selectedChannel)){
                        System.out.println("pass");
                        System.out.println(LoginPageController.loggedUser);
                        String sql = "INSERT INTO channel_message(channel_name, message_text) VALUES(?,?)";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, selectedChannel);
                        ps.setString(2, label.getText());
                        ps.executeUpdate();
                        MessageHolder.getChildren().add(label);
                        inputTxt.setText("");
                    }

                }
            }
        }


    }

    private boolean checkIfChannelAdmin(String sChannel) throws Exception {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM channels WHERE channel_name = '"+sChannel+"' AND channel_owner = '"+LoginPageController.loggedUser+"' ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet set = ps.executeQuery();
        System.out.println(LoginPageController.loggedUser + " in checher");
        if (set.next()) return true;
        return false;
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

        File logFile8 = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\addGroup.png");
        Image logImage8 = new Image(logFile8.toURI().toString());
        newGroupButton.setImage(logImage8);

        File logFile9 = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\addChannel.png");
        Image logImage9 = new Image(logFile9.toURI().toString());
        newChannelButton.setImage(logImage9);

        channelChatsSide.setOnMouseClicked(mouseEvent -> {
            selectedSection = "channels";
            try {
                showChannelsList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        allChatsSide.setOnMouseClicked(mouseEvent -> {
            selectedSection = "users";
            try {
                showUsersList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            showUsersList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        groupsChatsSide.setOnMouseClicked(mouseEvent -> {
            selectedSection = "groups";
            try {
                showGroupsList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        newGroupButton.setOnMouseClicked(mouseEvent -> {
            try {
                goToAddGroup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        newChannelButton.setOnMouseClicked(mouseEvent -> {
            try {
                goToAddChannel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    public void goToAddGroup() throws IOException {
        Stage stage = new Stage();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(35);
        vBox.setStyle("-fx-background-color:  #e88ee5;");

        Label mainLBL = new Label("Enter in a name for your group");
        mainLBL.setFont(Font.font("Century Schoolbook"));
        mainLBL.setTextFill(Color.rgb(12, 32, 105));

        TextField textField = new TextField();
        textField.setFont(Font.font("Century Schoolbook"));
        textField.setMinSize(25, 30);
        textField.setMaxWidth(251);
        textField.setStyle("-fx-background-radius: 50;");

        Button button = new Button("create");
        button.setFont(Font.font("Century Schoolbook"));
        button.setStyle("-fx-background-radius: 50; -fx-background-color: #ffffff");
        button.setPrefSize(116, 34);

        Label errorLBL = new Label("");
        errorLBL.setFont(Font.font("Century Schoolbook"));

        vBox.getChildren().addAll(errorLBL, mainLBL, textField, button);
        stage.setScene(new Scene(vBox));
        stage.setHeight(300);
        stage.setWidth(400);
        stage.show();

        button.setOnAction(event -> {
            if (textField.getText().isBlank() == false){
                try {
                    if (checkGroupName(textField.getText())){
                        addGroupToDatabase(textField.getText());
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




    public void goToAddChannel() throws IOException {
        Stage stage = new Stage();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(35);
        vBox.setStyle("-fx-background-color:  #e88ee5;");

        Label mainLBL = new Label("Enter in a name for your channel");
        mainLBL.setFont(Font.font("Century Schoolbook"));
        mainLBL.setTextFill(Color.rgb(12, 32, 105));

        TextField textField = new TextField();
        textField.setFont(Font.font("Century Schoolbook"));
        textField.setMinSize(25, 30);
        textField.setMaxWidth(251);
        textField.setStyle("-fx-background-radius: 50;");

        Button button = new Button("create");
        button.setFont(Font.font("Century Schoolbook"));
        button.setStyle("-fx-background-radius: 50; -fx-background-color: #ffffff");
        button.setPrefSize(116, 34);

        Label errorLBL = new Label("");
        errorLBL.setFont(Font.font("Century Schoolbook"));

        vBox.getChildren().addAll(errorLBL, mainLBL, textField, button);
        stage.setScene(new Scene(vBox));
        stage.setHeight(300);
        stage.setWidth(400);
        stage.show();

        button.setOnAction(event -> {
            if (textField.getText().isBlank() == false){
                try {
                    if (checkChannelName(textField.getText())){
                        addChannelToDatabase(textField.getText());
                        errorLBL.getScene().getWindow().hide();
                    }else {
                        errorLBL.setText("cahnnel name not available");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                errorLBL.setText("fill up the field");
            }
        });
    }


    // show users in listview

    void showUsersList() throws Exception {
        sideVbox.getChildren().removeAll(sideVbox.getChildren());
        File file = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\profilePlaceHolder.png");
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
                imageView1.setFitHeight(50);
                imageView1.setFitWidth(60);
                super.updateItem(name, empty);
                imageView1.setImage(image);
                setText(name);
                setGraphic(imageView1);
                setFont(Font.font(20));
                setAlignment(Pos.CENTER_LEFT);
                setCursor(Cursor.HAND);

                setOnMouseClicked(mouseEvent -> {
                    ObservableList selected = listView.getSelectionModel().getSelectedItems();
                    for(Object o : selected){
                        try {
                            if (getIndex() < users.size()){
                                selectedUser = users.get(getIndex()).getUsername();
                                topOfChatUsername.setText(users.get(getIndex()).getUsername());
                                showChatHistory(LoginPageController.loggedUser, users.get(getIndex()).getUsername());
                            }

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

    void showGroupChatHistory(String userOn, String groupName) throws Exception {
        MessageHolder.getChildren().removeAll(MessageHolder.getChildren());
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM group_message WHERE group_name = '"+groupName+"' ";
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

            if (resultSet.getString("message_sender").equals(userOn)){
                String text = resultSet.getString("message_text");
                label.setText(text);
                label.setAlignment(Pos.BOTTOM_RIGHT);
                label.setStyle("padding: 10px, 50px, 10px, 10px; -fx-background-radius: 10; -fx-font-size: 20px; -fx-border-color: #4022d6; -fx-border-radius: 30; -fx-font-family: 'Century Schoolbook'");

                label.setPadding(new Insets(10));
                MessageHolder.getChildren().add(label);
            }
            else {
                String text = resultSet.getString("message_text");
                label.setText(text);
                label.setAlignment(Pos.BOTTOM_LEFT);
                label.setStyle("padding: 10px, 50px, 10px, 10px; -fx-background-radius: 10; -fx-font-size: 20px; -fx-border-color: #4022d6; -fx-border-radius: 30; -fx-font-family: 'Century Schoolbook'");

                label.setPadding(new Insets(10));
                MessageHolder.getChildren().add(label);
            }

        }
    }


    void showChannelChatHistory(String channelName) throws Exception {
        MessageHolder.getChildren().removeAll(MessageHolder.getChildren());
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM channel_message WHERE channel_name = '"+channelName+"' ";
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

            String text = resultSet.getString("message_text");
            label.setText(text);
            label.setAlignment(Pos.BOTTOM_RIGHT);
            label.setStyle("padding: 10px, 50px, 10px, 10px; -fx-background-radius: 10; -fx-font-size: 20px; -fx-border-color: #4022d6; -fx-border-radius: 30; -fx-font-family: 'Century Schoolbook'");

            label.setPadding(new Insets(10));
            MessageHolder.getChildren().add(label);

        }
    }




    public boolean checkGroupName(String groupName) throws Exception {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM groups WHERE group_name = '"+groupName+"' ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet set = ps.executeQuery();
        if (set.next()) return false;
        return true;
    }

    public void addGroupToDatabase(String groupName) throws Exception {
        Connection connection = Database.getConnection();
        String sql = "INSERT INTO groups(group_name, group_owner) VALUES(?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, groupName);
        ps.setString(2, LoginPageController.loggedUser);
        ps.executeUpdate();
    }

    public boolean checkChannelName(String channelName) throws Exception {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM channels WHERE channel_name = '"+channelName+"' ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet set = ps.executeQuery();
        if (set.next()) return false;
        return true;
    }

    public void addChannelToDatabase(String channelName) throws Exception {
        Connection connection = Database.getConnection();
        String sql = "INSERT INTO channels(channel_name, channel_owner) VALUES(?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, channelName);
        ps.setString(2, LoginPageController.loggedUser);
        ps.executeUpdate();
    }




    void showGroupsList() throws Exception {
        sideVbox.getChildren().removeAll(sideVbox.getChildren());
        File file = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\groupProfile.png");
        Image image = new Image(file.toURI().toString());
        ListView<String> listView = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList ();

        ArrayList<Groups> groups = getGroups();
        for (int i = 0; i < groups.size(); i++) {
            items.add(groups.get(i).getGroupName());
        }
        listView.setItems(items);
        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView1 = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                imageView1.setFitHeight(50);
                imageView1.setFitWidth(60);
                super.updateItem(name, empty);
                imageView1.setImage(image);
                setText(name);
                setGraphic(imageView1);
                setFont(Font.font(20));
                setAlignment(Pos.CENTER_LEFT);
                setCursor(Cursor.HAND);
                setOnMouseClicked(mouseEvent -> {
                    ObservableList selected = listView.getSelectionModel().getSelectedItems();
                    for(Object o : selected){
                        try {
                            if (getIndex() < groups.size()){
                                selectedGroup = groups.get(getIndex()).getGroupName();
                                topOfChatUsername.setText(groups.get(getIndex()).getGroupName());
                                showGroupChatHistory(LoginPageController.loggedUser, groups.get(getIndex()).getGroupName());
                            }

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

    private ArrayList<Groups> getGroups() throws Exception {
        ArrayList<Groups> groups = new ArrayList<>();
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM groups";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            groups.add(new Groups(resultSet.getString("group_name"), resultSet.getString("group_owner")));
        }
        return groups;
    }

    private ArrayList<Channels> getChannels() throws Exception {
        ArrayList<Channels> channels = new ArrayList<>();
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM channels";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            channels.add(new Channels(resultSet.getString("channel_name"), resultSet.getString("channel_owner")));
        }
        return channels;
    }

    void showChannelsList() throws Exception {
        sideVbox.getChildren().removeAll(sideVbox.getChildren());
        File file = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\channelProfile.png");
        Image image = new Image(file.toURI().toString());
        ListView<String> listView = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList ();

        ArrayList<Channels> channels = getChannels();
        for (int i = 0; i < channels.size(); i++) {
            items.add(channels.get(i).getChannelName());
        }
        listView.setItems(items);
        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView1 = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                imageView1.setFitHeight(50);
                imageView1.setFitWidth(60);
                super.updateItem(name, empty);
                imageView1.setImage(image);
                setText(name);
                setGraphic(imageView1);
                setFont(Font.font(20));
                setAlignment(Pos.CENTER_LEFT);
                setCursor(Cursor.HAND);
                setOnMouseClicked(mouseEvent -> {
                    ObservableList selected = listView.getSelectionModel().getSelectedItems();
                    for(Object o : selected){
                        try {
                            if (getIndex() < channels.size()){
                                selectedChannel = channels.get(getIndex()).getChannelName();
                                topOfChatUsername.setText(channels.get(getIndex()).getChannelName());
                                showChannelChatHistory(channels.get(getIndex()).getChannelName());
                            }

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


}
