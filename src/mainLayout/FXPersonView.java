/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout;

import database.AddressRepository;
import database.PhoneRepository;
import models.PhoneNumber;
import models.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mainLayout.components.FXListSelector;
import mainLayout.components.FXSearchView;
import mainLayout.components.SelectorReceiver;
import mainLayout.components.StandardGridPane;
import models.Address;

/**
 *
 * @author stachu
 */
public class FXPersonView extends Application implements SelectorReceiver<Person> {
    private Stage stage;
    
    private Person shownPerson;
    
    private FXPhoneView numberView;
    private FXEditPersonView editView;
    private FXEditAddressView editAddressView;
    private FXSearchView<Address> addressSearch;
    
    private PhoneRepository phoneDb;
    private AddressRepository addressDb;
    
    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label addressLabel;
    private Label contactsLabel;
    private Text firstNameValue;
    private Text lastNameValue;
    private Text addressText;
    private FXListSelector<PhoneNumber, FXPhoneView> contacts;
    private Button newAddressButton;
    private Button changeAddressButton;
    private Button editPersonButton;
    private Button editAddressButton;
    private Button addPhoneNumberBtn;
    private Button removePhoneNumberBtn;
    private Button savePersonBtn;
    // TODO hide button when not needed
    
    private boolean addingMode;
    private EventHandler<ActionEvent> updateHandler;
    
    private ObservableList<PhoneNumber> numbers;
    private StandardGridPane root;
    private Scene scene;
    
    @Override
    public void start(Stage primaryStage) {
        
        EventHandler<ActionEvent> uHandler = new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Updating interface...");
                firstNameValue.setText(shownPerson.getFirstName());
                lastNameValue.setText(shownPerson.getLastName());
                stage.setTitle(shownPerson.getFullName() + " - Information");
                addressText.setText(shownPerson.getAddressString());
                if (numberView.getAddingMode()) {
                    PhoneNumber newPhone = numberView.getPhoneNumber();
                    phoneDb.addRecord(newPhone);
                    phoneDb.saveList();
                    shownPerson.getPhoneList().add(newPhone);
                    contacts.getItems().add(newPhone);
                    numberView.setAddingMode(false);
                }
                contacts.refresh();
            }
        };
        
        stage = primaryStage;
        numberView = new FXPhoneView();
        numberView.setOnUpdate(uHandler);
        numberView.start(new Stage());
        
        editView = new FXEditPersonView();
        editView.setOnUpdate(uHandler);
        editView.start(new Stage());
        
        editAddressView = new FXEditAddressView();
        editAddressView.setOnUpdate(uHandler);
        editAddressView.start(new Stage());
        
        addressSearch = new FXSearchView<>();
        addressSearch.setOnUpdate(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                // TODO implement 
                shownPerson.setAddress(addressSearch.getItem());
                addressText.setText(shownPerson.getAddressString());
            };
        });
        
        firstNameLabel = new Label("First name: ");
        lastNameLabel = new Label("Last name: ");
        addressLabel = new Label("Address: ");
        contactsLabel = new Label("Phone numbers: ");
        
        firstNameValue = new Text();
        lastNameValue = new Text();
        addressText = new Text();
        
        contacts = new FXListSelector<>();
        contacts.setTooltip("Click a number to edit it");
        contacts.setReceiver(numberView);
        
        editPersonButton = new Button("Edit personal information");
        editPersonButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Editing person...");
                editView.receive(shownPerson);
            }
        });
        
        changeAddressButton = new Button("Change address");
        changeAddressButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Switching address...");
                addressSearch.show(addressDb.getList());
            }
        });
        
        newAddressButton = new Button("New");
        newAddressButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Adding address...");
                editAddressView.receive(addressDb.make());
                editAddressView.setAddingMode(true);
            }
        });
        
        editAddressButton = new Button("Edit address");
        editAddressButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Editing address...");
                editAddressView.receive(shownPerson.getAddress());
                editAddressView.setAddingMode(false);
            }
        });
        
        addPhoneNumberBtn = new Button("Add phone");
        addPhoneNumberBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Adding new phone...");
                numberView.receive(new PhoneNumber(shownPerson));
                numberView.setAddingMode(true);
                // TODO make false on list selector
            }
        });
        
        removePhoneNumberBtn = new Button("Remove phone");
        removePhoneNumberBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Removing phone...");
            }
        });
        
        savePersonBtn = new Button("Save");
        savePersonBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Saving person...");
                if (updateHandler != null)
                    updateHandler.handle(event);
            }
        });
        
        //TODO add missing buttons
        root = new StandardGridPane();
        root.add(firstNameLabel, 0, 0);
        root.add(firstNameValue, 1, 0);
        root.add(lastNameLabel, 2, 0);
        root.add(lastNameValue, 3, 0);
        root.add(addressLabel, 0, 1);
        root.add(addressText, 1, 1);
        root.add(contactsLabel, 0, 2, 2, 1);
        root.add(contacts.getListView(), 0, 3, 2, 1);
        root.add(addPhoneNumberBtn, 0, 4);
        root.add(removePhoneNumberBtn, 1, 4);
        root.add(editPersonButton, 0, 5, 2, 1);
        root.add(editAddressButton, 2, 4, 2, 1);
        
        scene = new Scene(root, 480, 360);
        
        stage.setTitle("Information");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Stage is closing");
                numberView.stop();
                editView.stop();
                editAddressView.stop();
                stage.close();
            }
        });
    }
    
    @Override
    public void receive(Person person) {
        this.shownPerson = person;
        numbers = FXCollections.observableArrayList(person.getPhoneList());
        contacts.setItems(numbers);
        firstNameValue.setText(person.getFirstName());
        lastNameValue.setText(person.getLastName());
        stage.setTitle(person.getFullName() + " - Information");
        addressText.setText(person.getAddressString());
        stage.show();
    }
    
    public Person getPerson() {
        return this.shownPerson;
    }
    
    public void close() {
        stage.close();
    }
    
    @Override
    public void stop() {
        numberView.stop();
        editView.stop();
        editAddressView.stop();
        stage.close();
    }
    
    public void setAddingMode(boolean val) {
        addingMode = val;
        if (val)
            stage.setTitle("New person");
        else if (shownPerson != null)
            stage.setTitle(shownPerson.getFullName() + " - Information");
    }
    
    public boolean getAddingMode() {
        return addingMode;
    }
    
    public void setPhoneRepository(PhoneRepository repo) {
        phoneDb = repo;
    }
    
    public void setAddressRepository(AddressRepository repo) {
        addressDb = repo;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    void setOnUpdate(EventHandler<ActionEvent> eventHandler) {
        this.updateHandler = eventHandler; //To change body of generated methods, choose Tools | Templates.
    }
    
}
