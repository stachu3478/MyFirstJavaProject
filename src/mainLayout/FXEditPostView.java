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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mainLayout.components.SelectorReceiver;
import mainLayout.components.StandardGridPane;

/**
 *
 * @author stachu
 */
public class FXEditPostView extends Application implements SelectorReceiver<PostOffice> {
    private Stage stage;
    private PostOffice editPost;
    
    private Label postLabel;
    private Label cityLabel;
    private Label hyphenLabel;
    private TextField postalCode1;
    private TextField postalCode2;
    private ChoiceBox<City> cityChoice;
    private TextField cityInput;
    private RadioButton chooseCityBtn;
    private RadioButton createNewCityBtn;
    private ToggleGroup btnGroup;
    private Button switchCityButton;
    private Button saveButton;
    private Button discardButton;
    
    private EventHandler<ActionEvent> updateHandler;
    
    private StandardGridPane root;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        
        postLabel = new Label("Postal code: ");
        cityLabel = new Label("City: ");
        hyphenLabel = new Label(" - ");
        
        postalCode1 = new TextField();
        postalCode1.setPrefColumnCount(2);
        postalCode2 = new TextField();
        postalCode2.setPrefColumnCount(3);
        cityChoice = new ChoiceBox();
        ObservableList<City> cities = FXCollections.observableArrayList(new City(), new City(), new City());
        cityChoice.setItems(cities);
        cityInput = new TextField();
        chooseCityBtn = new RadioButton("Choose existing city: ");
        chooseCityBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Choosing city mode");
                cityInput.setDisable(true);
                switchCityButton.setDisable(false);
            }
        });
        createNewCityBtn = new RadioButton("Create new city");
        createNewCityBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Creating new city mode");
                cityInput.setDisable(false);
                switchCityButton.setDisable(true);
            }
        });
        btnGroup = new ToggleGroup();
        chooseCityBtn.setToggleGroup(btnGroup);
        createNewCityBtn.setToggleGroup(btnGroup);
        
        btnGroup.selectToggle(chooseCityBtn);
        cityInput.setDisable(true);
        
        switchCityButton = new Button("Choose");
        switchCityButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Choosing city...");
            }
        });
        
        saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("Saving changes...");
                    editPost.setCode(postalCode1.getText(), postalCode2.getText());
                    if (btnGroup.getSelectedToggle() == chooseCityBtn) {
                        // assign this city
                        editPost.setCity(cityChoice.getValue());
                    } else {
                        // create new city with the same post
                        City city = new City(editPost);
                        city.setName(cityInput.getText());
                        editPost.setCity(city);
                    }
                    if (updateHandler != null) updateHandler.handle(event);
                    stage.close();
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Entered phone has invalid format. It should consist of 9 digits.", ButtonType.OK);
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
        root.add(postLabel      , 0, 0, 3, 1);
        root.add(postalCode1    , 0, 1);
        root.add(hyphenLabel    , 1, 1);
        root.add(postalCode2    , 2, 1);
        root.add(cityLabel      , 1, 2, 3, 1);
        root.add(chooseCityBtn  , 0, 3, 2, 1);
        root.add(switchCityButton, 2, 3);
        root.add(createNewCityBtn, 0, 4, 3, 1);
        root.add(cityInput      , 0, 5, 3, 1);
        root.add(saveButton     , 1, 6);
        root.add(discardButton  , 2, 6);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Edit Post Office");
        primaryStage.setScene(scene);
    }
    
    public void setOnUpdate(EventHandler<ActionEvent> h) {
        this.updateHandler = h;
    }
    
    @Override
    public void receive(PostOffice post) {
        this.editPost = post;
        postalCode1.setText(post.getCode1());
        postalCode2.setText(post.getCode2());
        cityChoice.setValue(post.getCity());
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
