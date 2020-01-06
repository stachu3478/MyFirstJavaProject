/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout;

import models.Address;
import models.City;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mainLayout.components.SelectorReceiver;
import mainLayout.components.StandardGridPane;

/**
 *
 * @author stachu
 */
public class FXEditAddressView extends Application implements SelectorReceiver<Address> {
    
    private Stage stage;
    private Address editAddress;
    
    private FXEditCityView cityView;
    
    private Label cityLabel;
    private Label streetLabel;
    private Label nrLabel;
    private Label slashLabel;
    private ChoiceBox<City> cityChoice;
    private TextField streetInput;
    private TextField nrInput;
    private TextField inNrInput;
    private Button editCityButton;
    private Button saveAddressButton;
    private Button discardButton;
    
    private EventHandler<ActionEvent> updateHandler;
    
    private StandardGridPane root;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        
        EventHandler<ActionEvent> uHandler = new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Updating interface...");
                // TODO refresh city list
                // cityChoice.refresh();
                // cityChoice.setValue(editAddress.getCity());
                streetInput.setText(editAddress.getStreet());
                nrInput.setText(editAddress.getNr());
                inNrInput.setText(editAddress.getNr2());
            }
        };
        
        cityView = new FXEditCityView();
        cityView.setOnUpdate(uHandler);
        cityView.start(new Stage());
        
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
        
        editCityButton = new Button("Edit");
        editCityButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Editing city...");
                cityView.receive(editAddress.getCity());
            }
        });
        
        saveAddressButton = new Button("Save");
        saveAddressButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("Saving changes...");
                    editAddress.setCity(cityChoice.getValue());
                    editAddress.setStreet(streetInput.getText());
                    editAddress.setNr(nrInput.getText());
                    editAddress.setNr2(inNrInput.getText());
                    if (updateHandler != null) updateHandler.handle(event);
                    stage.close();
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Entered number(s) have invalid format. Please correct the errors", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });
        
        discardButton = new Button("Cancel");
        discardButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Discarding changes...");
                stage.close();
            }
        });
        
        root = new StandardGridPane();
        root.add(cityLabel, 0, 0);
        root.add(cityChoice, 1, 0);
        root.add(editCityButton, 2, 0, 2, 1);
        root.add(streetLabel, 0, 1);
        root.add(streetInput, 1, 1, 3, 1);
        root.add(nrLabel, 0, 2);
        root.add(nrInput, 1, 2);
        root.add(slashLabel, 2, 2);
        root.add(inNrInput, 3, 2);
        root.add(saveAddressButton, 0, 3, 2, 1);
        root.add(discardButton, 2, 3, 2, 1);
        
        Scene scene = new Scene(root, 480, 250);
        
        stage.setTitle("Edit address");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Stage is closing");
                cityView.stop();
                stage.close();
            }
        });
    }
    
    public void setOnUpdate(EventHandler<ActionEvent> h) {
        this.updateHandler = h;
    }
    
    @Override
    public void receive(Address address) {
        this.editAddress = address;
        cityChoice.setValue(address.getCity());
        streetInput.setText(address.getStreet());
        nrInput.setText(address.getNr());
        inNrInput.setText(address.getNr2());
        stage.show();
    }
    
    @Override
    public void stop() {
        cityView.stop();
        stage.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
