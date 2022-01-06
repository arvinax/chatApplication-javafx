package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sample.Database;
import sample.Model.Channels;
import sample.Model.Users;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddMembersChannel implements Initializable {

    @FXML
    private VBox sideVbox;

    @FXML
    private Label notifLBL;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showUsersList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
                                if (HomePageController.selectedChannel != null){
                                    String member = users.get(getIndex()).getUsername();
                                    if(checkIfMember(member, HomePageController.selectedChannel)){
                                        addChannelMemberToDatabse(member, HomePageController.selectedChannel);
                                        notifLBL.setText(member + " added to your channel!");
                                    }else{
                                        notifLBL.setText(member + " already exist in the channel!");
                                    }
                                }

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


    private void addChannelMemberToDatabse(String member, String channelName) throws Exception {
        Connection connection = Database.getConnection();
        String sql = "INSERT INTO channel_members(channel_name, member_name) VALUES(?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, channelName);
        ps.setString(2, member);
        ps.executeUpdate();
    }

    private boolean checkIfMember(String member, String channelName) throws Exception {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM channel_members WHERE member_name = '"+member+"' AND channel_name = '"+channelName+"' ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet set = ps.executeQuery();
        if(set.next()) return false;
        return true;
    }



}
