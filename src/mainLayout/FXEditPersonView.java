/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout;

import models.Person;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainLayout.components.SelectorReceiver;
import mainLayout.components.StandardGridPane;

/**
 *
 * @author stachu
 */
public class FXEditPersonView extends Application implements SelectorReceiver<Person> {
    private Stage stage;
    private Person editPerson;
    
    private Label firstNameLabel;
    private Label lastNameLabel;
    private TextField firstNameValue;
    private TextField lastNameValue;
    private Button savePersonButton;
    private Button discardButton;
    
    private EventHandler<ActionEvent> updateHandler;
    
    private StandardGridPane root;
    
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        stage.setTitle("Edit");
        
        firstNameLabel = new Label("First name: ");
        lastNameLabel = new Label("Last name: ");
        
        firstNameValue = new TextField();
        lastNameValue = new TextField();
        
        savePersonButton = new Button("Save");
        savePersonButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Saving changes...");
                editPerson.setFirstName(firstNameValue.getText());
                editPerson.setLastName(lastNameValue.getText());
                if (updateHandler != null) updateHandler.handle(event);
                stage.close();
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
        root.add(firstNameLabel, 0, 0);
        root.add(firstNameValue, 1, 0);
        root.add(lastNameLabel, 0, 1);
        root.add(lastNameValue, 1, 1);
        root.add(savePersonButton, 0, 2);
        root.add(discardButton, 1, 2);
        
        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        
    }
    
    public void setOnUpdate(EventHandler<ActionEvent> h) {
        this.updateHandler = h;
    }
    
    @Override
    public void receive(Person person) {
        this.editPerson = person;
        firstNameValue.setText(person.getFirstName());
        lastNameValue.setText(person.getLastName());
        stage.setTitle("Edit - " + person.getFullName());
        stage.show();
    }
    
    @Override
    public void stop() {
        stage.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
