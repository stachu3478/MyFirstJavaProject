package mainLayout;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

/**
 *
 * @author stachu
 */
public class FXContactView extends Application {
    private FXPersonView personView;
    
    private ListView<String> contactList;
    private Label searchText;
    private TextField searchInput;
    private Button searchButton;
    
    private int selectedContact;
    private boolean doubleClick;
    
    private GridPane root;
    private Scene scene;
    
    @Override
    public void start(Stage primaryStage) {
        
        personView = new FXPersonView();
        personView.start(new Stage());
        
        // TODO Add search contacts text
        searchText = new Label("Search contacts: ");
        searchText.getStyleClass().add("search-label");
        
        // add search input
        searchInput = new TextField();
        searchInput.getStyleClass().add("search-input");
        searchInput.setTooltip(new Tooltip("Enter any text to search contacts by names"));
        searchInput.setOnKeyTyped(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                System.out.println("Searching...");
            }
        });
        
        // Add contacts button
        searchButton = new Button();
        searchButton.getStyleClass().add("search-button");
        searchButton.setText("Add new contact");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Opening new window to add a new contact");
            }
        });
        
        // TODO add found contacts list
        // add person list member
        selectedContact = -1;
        doubleClick = false;
        contactList = new ListView<String>();
        ObservableList<String> contacts = FXCollections.observableArrayList("COntact1", "Contact2", "COntact3");
        contactList.setItems(contacts);
        contactList.setTooltip(new Tooltip("Click a contact to show it"));
        contactList.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                int newContact = contactList.getSelectionModel().getSelectedIndex();
                if (selectedContact != newContact || doubleClick) {
                    selectedContact = newContact;
                    doubleClick = false;
                    System.out.println("Showing contact details...");
                    personView.showPerson(new Person());
                } else if (selectedContact != -1) {
                    doubleClick = true;
                }
            }
        });
        
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(15, 15, 15, 15));
        
        root.add(searchText, 0, 0);
        root.add(searchInput, 1, 0);
        root.add(contactList, 0, 1, 2, 1);
        root.add(searchButton, 0, 2);
        
        scene = new Scene(root, 480, 360);
        scene.getStylesheets().add(FXContactView.class.getResource("FXContactView.css").toExternalForm());
        
        primaryStage.setTitle("Książka adresowa");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Stage is closing");
                personView.stop();
                primaryStage.close();
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
