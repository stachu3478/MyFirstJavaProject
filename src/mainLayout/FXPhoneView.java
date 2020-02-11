/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout;

import database.CountryRepository;
import models.PhoneNumber;
import models.Country;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import mainLayout.components.SelectorReceiver;
import mainLayout.components.StandardGridPane;

/**
 *
 * @author stachu
 */
public class FXPhoneView extends Application implements SelectorReceiver<PhoneNumber> {
    private Stage stage;
    private PhoneNumber editNumber;
    
    private Label numberLabel;
    private Label countryLabel;
    private TextField numberValue;
    private ChoiceBox<Country> countryValue;
    private Button saveButton;
    private Button cancelButton;
    
    private boolean addingMode = true;
    
    private EventHandler<ActionEvent> updateHandler;
    
    private GridPane root;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        
        numberLabel = new Label("Phone number: ");
        countryLabel = new Label("Country: ");
        
        numberValue = new TextField();
        countryValue = new ChoiceBox();
        ObservableList<Country> countries = new CountryRepository().getList().sorted();
        countryValue.setItems(countries);
        
        saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("Saving changes...");
                    editNumber.setNumber(numberValue.getText());
                    editNumber.setCountry(countryValue.getValue());
                    if (updateHandler != null) updateHandler.handle(event);
                    stage.close();
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(AlertType.ERROR, "Entered phone has invalid format. It should consist of 9 digits.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });
        
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Discarding changes...");
                stage.close();
            }
        });
        
        root = new StandardGridPane();
        root.add(numberLabel, 0, 0);
        root.add(countryLabel, 0, 1);
        root.add(numberValue, 1, 0);
        root.add(countryValue, 1, 1);
        root.add(saveButton, 0, 2);
        root.add(cancelButton, 1, 2);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Phone number selection");
        primaryStage.setScene(scene);
    }
    
    public void setOnUpdate(EventHandler<ActionEvent> h) {
        this.updateHandler = h;
    }
    
    public void receive(PhoneNumber number) {
        this.editNumber = number;
        numberValue.setText(number.getValue());
        countryValue.setValue(number.getCountry());
        stage.show();
    }
    
    @Override
    public void stop() {
        stage.close();
    }
    
    public void setAddingMode(boolean val) {
        addingMode = val;
        if (val)
            stage.setTitle("New phone number");
        else
            stage.setTitle("Edit phone number");
    }
    
    public boolean getAddingMode() {
        return addingMode;
    }
    
    public PhoneNumber getPhoneNumber() {
        return editNumber;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
