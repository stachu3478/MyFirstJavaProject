/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout;

import database.CityRepository;
import models.City;
import models.PostOffice;
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
import mainLayout.components.FXSearchView;
import mainLayout.components.SelectorReceiver;
import mainLayout.components.StandardGridPane;

/**
 *
 * @author stachu
 */
public class FXEditPostView extends Application implements SelectorReceiver<PostOffice> {
    private Stage stage;
    private PostOffice editPost;
    private FXSearchView<City> citySearch;
    
    private CityRepository cityDb;
    
    private Label postLabel;
    private Label cityLabel;
    private Label hyphenLabel;
    private TextField postalCode1;
    private TextField postalCode2;
    private TextField cityInput;
    private RadioButton chooseCityBtn;
    private RadioButton createNewCityBtn;
    private ToggleGroup btnGroup;
    private Button switchCityButton;
    private Button saveButton;
    private Button discardButton;
    
    private boolean addingMode;
    
    private EventHandler<ActionEvent> updateHandler;
    
    private StandardGridPane root;
    
    @Override
    public void start(Stage primaryStage) {
        EventHandler<ActionEvent> uHandler = new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Updating interface...");
                if (btnGroup.getSelectedToggle() == chooseCityBtn)
                    cityInput.setText(citySearch.getItem().toString());
            }
        };
        
        stage = primaryStage;
        citySearch = new FXSearchView<>();
        citySearch.start(new Stage());
        citySearch.setItemName("City");
        citySearch.setOnUpdate(uHandler);
        
        postLabel = new Label("Postal code: ");
        cityLabel = new Label("City: ");
        hyphenLabel = new Label(" - ");
        
        postalCode1 = new TextField();
        postalCode1.setPrefColumnCount(2);
        postalCode2 = new TextField();
        postalCode2.setPrefColumnCount(3);
        // cityDb = new CityRepository();
        // ObservableList<City> cities = cityDb.getList();
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
                citySearch.show(cityDb.getList());
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
                        editPost.setCity(citySearch.getItem());
                    } else {
                        // create new city with the same post
                        City city = new City(editPost);
                        city.setName(cityInput.getText());
                        editPost.setCity(city);
                        cityDb.addRecord(city);
                        cityDb.saveList();
                    }
                    if (updateHandler != null) updateHandler.handle(event);
                    stage.close();
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid format of postal code. Please correct", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });
        
        discardButton = new Button("Cancel");
        discardButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Discarding changes...");
                setAddingMode(false);
                stage.close();
                citySearch.stop();
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
        
        setAddingMode(false);
        stage.setScene(scene);
    }
    
    public void setOnUpdate(EventHandler<ActionEvent> h) {
        this.updateHandler = h;
    }
    
    @Override
    public void receive(PostOffice post) {
        this.editPost = post;
        postalCode1.setText(post.getCode1());
        postalCode2.setText(post.getCode2());
        citySearch.setItem(post.getCity());
        stage.show();
    }
    
    public PostOffice getEditPost() {
        return editPost;
    }
    
    public void setAddingMode(boolean mode) {
        addingMode = mode;
        if (mode)
            stage.setTitle("New Post Office");
        else
            stage.setTitle("Edit Post Office");
    }
    
    public boolean getAddingMode() {
        return addingMode;
    }
    
    public void setCityDb(CityRepository repo) {
        this.cityDb = repo;
    }
    
    @Override
    public void stop() {
        stage.close();
        citySearch.stop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
