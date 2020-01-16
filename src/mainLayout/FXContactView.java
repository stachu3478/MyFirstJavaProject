package mainLayout;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import database.PeopleRepository;
import models.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Tooltip;
import javafx.stage.WindowEvent;
import mainLayout.components.FXListSelector;
import mainLayout.components.ObservableListSelector;
import mainLayout.components.StandardGridPane;

/**
 *
 * @author stachu
 */
public class FXContactView extends Application {
    private FXPersonView personView;
    
    private PeopleRepository peopleDb;
    
    private FXListSelector<Person, FXPersonView> contactList;
    private ObservableListSelector<Person> filterer;
    private Label searchText;
    private TextField searchInput;
    private Button searchButton;
    
    private StandardGridPane root;
    private Scene scene;
    
    @Override
    public void start(Stage primaryStage) {
        
        personView = new FXPersonView();
        personView.start(new Stage());
        personView.setOnUpdate(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                if (personView.getAddingMode()) {
                    peopleDb.addRecord(personView.getPerson());
                    peopleDb.saveList();
                }
                contactList.refresh();
                // TODO disable adding mode for person
            }
        });
        
        contactList = new FXListSelector<Person, FXPersonView>();
        peopleDb = new PeopleRepository();
        contactList.setItems(peopleDb.getList());
        contactList.setTooltip("Click a contact to see details");
        contactList.setReceiver(personView);
        personView.setPhoneRepository(peopleDb.getPhoneDb());
        personView.setAddressRepository(peopleDb.getAddressDb());
        
        filterer = new ObservableListSelector();
        filterer.setListView(contactList.getListView());
        
        // TODO Add search contacts text
        searchText = new Label("Search contacts: ");
        searchText.getStyleClass().add("search-label");
        
        // add search input
        searchInput = new TextField();
        searchInput.getStyleClass().add("search-input");
        searchInput.setTooltip(new Tooltip("Enter any text to search contacts by names"));
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {

            System.out.println("Searching...");
            filterer.applyFilter(newValue);
        });
        
        // Add contacts button
        searchButton = new Button("Add new contact");
        searchButton.getStyleClass().add("search-button");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Opening new window to add a new contact");
                personView.receive(peopleDb.make());
                personView.setAddingMode(true);
                // TODO disable adding mode for person
            }
        });
        
        root = new StandardGridPane();
        root.add(searchText, 0, 0);
        root.add(searchInput, 1, 0);
        root.add(contactList.getListView(), 0, 1, 2, 1);
        root.add(searchButton, 0, 2);
        
        scene = new Scene(root, 480, 360);
        scene.getStylesheets().add(FXContactView.class.getResource("FXContactView.css").toExternalForm());
        
        primaryStage.setTitle("Contacts");
        primaryStage.setScene(scene);
        primaryStage.show();
        // TODO add some icon
        // primaryStage.getIcons().add( new Image( FXContactView.class.getResourceAsStream( "icon.png" )));
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
