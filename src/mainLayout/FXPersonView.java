/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mainLayout.components.FXListSelector;
import mainLayout.components.SelectorReceiver;

/**
 *
 * @author stachu
 */
public class FXPersonView extends Application implements SelectorReceiver<Person> {
    private Stage stage;
    
    private FXPhoneView numberView;
    
    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label addressLabel;
    private Label contactsLabel;
    private Text firstNameValue;
    private Text lastNameValue;
    private Text addressText;
    private FXListSelector<PhoneNumber, FXPhoneView> contacts;
    private Button editPersonButton;
    private Button editAddressButton;
    private Button addPhoneNumberBtn;
    private Button removePhoneNumberBtn;
    
    private int selectedContact;
    private boolean doubleClick;
    
    private GridPane root;
    private Scene scene;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        numberView = new FXPhoneView();
        numberView.start(new Stage());
        
        firstNameLabel = new Label("First name: ");
        lastNameLabel = new Label("Last name: ");
        addressLabel = new Label("Address: ");
        contactsLabel = new Label("Phone numbers: ");
        
        firstNameValue = new Text();
        lastNameValue = new Text();
        addressText = new Text();
        
        contacts = new FXListSelector<PhoneNumber, FXPhoneView>();
        ObservableList<PhoneNumber> numbers = FXCollections.observableArrayList(new PhoneNumber(), new PhoneNumber(), new PhoneNumber());
        contacts.setItems(numbers);
        contacts.setTooltip("Click a number to edit it");
        contacts.setReceiver(numberView);
        
        editPersonButton = new Button();
        editPersonButton.setText("Edit personal information");
        editPersonButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Editing person...");
            }
        });
        
        editAddressButton = new Button();
        editAddressButton.setText("Edit address");
        editAddressButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Editing address...");
            }
        });
        
        addPhoneNumberBtn = new Button();
        addPhoneNumberBtn.setText("Add phone");
        addPhoneNumberBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Adding new phone...");
            }
        });
        
        removePhoneNumberBtn = new Button();
        removePhoneNumberBtn.setText("Remove phone");
        removePhoneNumberBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Removing phone...");
            }
        });
        
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(15, 15, 15, 15));
        
        root.add(firstNameLabel, 0, 0);
        root.add(firstNameValue, 1, 0);
        root.add(lastNameLabel, 0, 1);
        root.add(lastNameValue, 1, 1);
        root.add(addressLabel, 0, 2);
        root.add(addressText, 1, 2);
        root.add(contactsLabel, 0, 3, 2, 1);
        root.add(contacts.getListView(), 0, 4, 2, 1);
        root.add(addPhoneNumberBtn, 0, 5);
        root.add(removePhoneNumberBtn, 1, 5);
        root.add(editPersonButton, 0, 6, 2, 1);
        root.add(editAddressButton, 0, 7, 2, 1);
        
        scene = new Scene(root, 480, 360);
        
        primaryStage.setTitle("Information");
        primaryStage.setScene(scene);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Stage is closing");
                numberView.stop();
                stage.close();
            }
        });
    }
    
    @Override
    public void receive(Person person) {
        // TODO assign parameters
        String fName = person.getFirstName();
        String lName = person.getLastName();
        firstNameValue.setText(fName);
        lastNameValue.setText(lName);
        stage.setTitle(fName + " " + lName + " - Information");
        addressText.setText(person.getAddressString());
        stage.show();
    }
    
    public void close() {
        stage.close();
    }
    
    @Override
    public void stop() {
        numberView.stop();
        stage.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
