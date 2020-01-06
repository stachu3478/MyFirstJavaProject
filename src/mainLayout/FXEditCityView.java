/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout;

import models.City;
import models.PostOffice;
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
public class FXEditCityView extends Application implements SelectorReceiver<City> {
    
    private Stage stage;
    private City editCity;
    
    private FXEditPostView postView;
    
    private Label cityLabel;
    private Label postLabel;
    private TextField cityInput;
    private ChoiceBox<PostOffice> postChoice;
    private Button editPostButton;
    private Button switchPostButton;
    private Button saveButton;
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
                cityInput.setText(editCity.getName());
                // TODO refresh postChoice
            }
        };
        
        postView = new FXEditPostView();
        postView.setOnUpdate(uHandler);
        postView.start(new Stage());
        
        cityLabel = new Label("City: ");
        postLabel = new Label("Post office: ");
        
        cityInput = new TextField();
        postChoice = new ChoiceBox();
        ObservableList<PostOffice> posts = FXCollections.observableArrayList(new PostOffice(), new PostOffice(), new PostOffice());
        postChoice.setItems(posts);
        
        editPostButton = new Button("Edit");
        editPostButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Editing post...");
                postView.receive(editCity.getPostOffice());
            }
        });
        
        switchPostButton = new Button("Change");
        switchPostButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Switching post...");
            }
        });
        
        saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Saving changes...");
                editCity.setName(cityInput.getText());
                editCity.setPostOffice(postChoice.getValue());
                if (updateHandler != null) updateHandler.handle(event);
                stage.close();
            }
        });
        
        discardButton = new Button("Cancel");
        discardButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Discarding changes...");
                postView.stop();
                stage.close();
            }
        });
        
        root = new StandardGridPane();
        root.add(cityLabel, 0, 0);
        root.add(cityInput, 1, 0, 3, 1);
        root.add(postLabel, 0, 1);
        root.add(postChoice, 1, 1);
        root.add(editPostButton, 2, 1);
        root.add(switchPostButton, 3, 1);
        root.add(saveButton, 1, 2);
        root.add(discardButton, 2, 2);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Edit");
        primaryStage.setScene(scene);
    }
    
    public void setOnUpdate(EventHandler<ActionEvent> h) {
        this.updateHandler = h;
    }
    
    @Override
    public void receive(City city) {
        this.editCity = city;
        cityInput.setText(city.getName());
        postChoice.setValue(city.getPostOffice());
        stage.setTitle("Edit - " + city.getName());
        stage.show();
    }
    
    @Override
    public void stop() {
        postView.stop();
        stage.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
