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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mainLayout.components.SelectorReceiver;
import mainLayout.components.StandardGridPane;

/**
 *
 * @author stachu
 */
public class FXEditAddressView extends Application implements SelectorReceiver<Address> {
    
    private Stage stage;
    private Address editAddress;
    
    private Label cityLabel;
    private Label streetLabel;
    private Label nrLabel;
    private Label slashLabel;
    private ChoiceBox<City> cityChoice;
    private TextField streetInput;
    private TextField nrInput;
    private TextField inNrInput;
    private Button addNewCityButton;
    private Button saveAddressButton;
    private Button discardButton;
    
    private EventHandler<ActionEvent> updateHandler;
    
    private StandardGridPane root;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        
        cityLabel = new Label("City: ");
        streetLabel = new Label("Street name: ");
        nrLabel = new Label("At: ");
        slashLabel = new Label(" / ");
        
        cityChoice = new ChoiceBox();
        ObservableList<City> cities = FXCollections.observableArrayList(new City(), new City(), new City());
        cityChoice.setItems(cities);
        streetInput = new TextField();
        nrInput = new TextField();
        inNrInput = new TextField();
        
        addNewCityButton = new Button("New city");
        addNewCityButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Adding new city...");
            }
        });
        
        saveAddressButton = new Button("Save");
        saveAddressButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Saving address...");
            }
        });
        
        discardButton = new Button("Cancel");
        discardButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Discarding changes...");
            }
        });
        
        root = new StandardGridPane();
        root.add(cityLabel, 0, 0);
        root.add(cityChoice, 1, 0);
        root.add(addNewCityButton, 2, 0, 2, 1);
        root.add(streetLabel, 0, 1);
        root.add(streetInput, 1, 1, 3, 1);
        root.add(nrLabel, 0, 2);
        root.add(nrInput, 1, 2);
        root.add(slashLabel, 2, 2);
        root.add(inNrInput, 3, 2);
        root.add(saveAddressButton, 0, 3, 2, 1);
        root.add(discardButton, 2, 3, 2, 1);
        
        Scene scene = new Scene(root, 480, 250);
        
        primaryStage.setTitle("Edit");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void receive(Address address) {
        this.editAddress = address;
        cityChoice.setValue(address.getCity());
        streetInput.setText(address.getStreet());
        nrInput.setText(address.getNr());
        inNrInput.setText(address.getNr2());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
