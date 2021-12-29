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

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    public String loggedUser;

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
            System.out.println(loggedUser + " inside the method");
            String sql = "INSERT INTO private_message(message_text, message_from, message_to) VALUES(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, label.getText());
            ps.setString(2, "caraw");
            ps.setString(3, "justim");
            ps.executeUpdate();
            MessageHolder.getChildren().add(label);
            inputTxt.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File logFile = new File("C:\\Users\\Justim\\IdeaProjects\\SickFX\\images\\allChatsSide.png");
        Image logImage = new Image(logFile.toURI().toString());
        allChatsSide.setImage(logImage);


        try {
            showChatHistory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        showUsersList();
    }


    private final Image IMAGE_APPLE  = new Image("http://findicons.com/files/icons/832/social_and_web/64/apple.png");

    void showUsersList(){
        ListView<String> listView = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList ();

        for (int i = 0; i < 50; i++) {
            items.add("Jasem\nJasem");
        }

        listView.setItems(items);

        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView1 = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                imageView1.setImage(IMAGE_APPLE);
                //   int index = getIndex();
                setText(name);
                setGraphic(imageView1);
                setFont(Font.font(20));
                setAlignment(Pos.CENTER);
                setOnMouseClicked(mouseEvent -> {
                    ObservableList selected = listView.getSelectionModel().getSelectedItems();
                    for(Object o : selected){
                        System.out.println(o);
                    }
                });
                setStyle("-fx-background-color: palegreen; -fx-background-insets: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, purple, 10, 0, 0, 0)");
            }
        });


        sideVbox.getChildren().add(listView);
        sideVbox.setAlignment(Pos.CENTER);

    }

    void showChatHistory() throws Exception {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM private_message WHERE message_from = '"+"justim"+"' OR message_from = '"+"caraw"+"' AND message_from = '"+"caraw"+"' OR message_from = '"+"caraw"+"' ";
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
           // label.setMaxWidth(Region.USE_COMPUTED_SIZE);
           // label.setMinWidth(Region.USE_PREF_SIZE);



            MessageHolder.setAlignment(Pos.BOTTOM_RIGHT);
            MessageHolder.setSpacing(20);

            if (resultSet.getString("message_from").equals("justim")){
                String text = resultSet.getString("message_text");
                label.setText(text);
                label.setAlignment(Pos.BOTTOM_RIGHT);
                label.setStyle("padding: 10px, 50px, 10px, 10px; -fx-background-radius: 10; -fx-font-size: 20px; -fx-border-color: #4022d6; -fx-border-radius: 30; -fx-font-family: 'Century Schoolbook'");

                label.setPadding(new Insets(10));
                MessageHolder.getChildren().add(label);
            }
            else if (resultSet.getString("message_from").equals("caraw")){
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
