/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout;

import database.PostRepository;
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
import mainLayout.components.FXSearchView;
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
    private FXSearchView<PostOffice> postSearch;
    private PostOffice selectedPost;
    
    private PostRepository postDb;
    
    private Label cityLabel;
    private Label postLabel;
    private TextField cityInput;
    private Label chosenPost;
    private Button addPostButton;
    private Button editPostButton;
    private Button switchPostButton;
    private Button saveButton;
    private Button discardButton;
    
    private boolean addingMode;
    
    private EventHandler<ActionEvent> updateHandler;
    
    private StandardGridPane root;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        postDb = new PostRepository();
        
        postSearch = new FXSearchView<>();
        postSearch.start(new Stage());
        postSearch.setItemName("Post office");
        postSearch.setOnUpdate(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                PostOffice newPost = postSearch.getItem();
                if (newPost != null) {
                    chosenPost.setText(newPost.toString());
                    selectedPost = newPost;
                }
            }
        });
        
        postView = new FXEditPostView();
        postView.setCityDb(postDb.getCityDb());
        postView.setOnUpdate(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Updating interface...");
                cityInput.setText(editCity.getName());
                PostOffice selected = postView.getEditPost();
                if (selected != null) {
                    chosenPost.setText(selected.toString());
                    if (postView.getAddingMode()) {
                        postDb.addRecord(selected);
                        postDb.saveList();
                    }
                    selectedPost = selected;
                }
            }
        });
        postView.start(new Stage());
        
        cityLabel = new Label("City: ");
        postLabel = new Label("Post office: ");
        chosenPost = new Label("No post office");
        
        cityInput = new TextField();
        ObservableList<PostOffice> posts = postDb.getList();
        
        addPostButton = new Button("New");
        addPostButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Adding post...");
                postView.receive(new PostOffice());
                postView.setAddingMode(true);
            }
        });
        
        editPostButton = new Button("Edit");
        editPostButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Editing post...");
                postView.receive(selectedPost);
                postView.setAddingMode(false);
            }
        });
        
        switchPostButton = new Button("Choose");
        switchPostButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Switching post...");
                postSearch.show(posts);
            }
        });
        
        saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Saving changes...");
                editCity.setName(cityInput.getText());
                editCity.setPostOffice(selectedPost);
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
        root.add(cityInput, 1, 0, 4, 1);
        root.add(postLabel, 0, 1);
        root.add(chosenPost, 1, 1);
        root.add(addPostButton, 2, 1);
        root.add(editPostButton, 3, 1);
        root.add(switchPostButton, 4, 1);
        root.add(saveButton, 1, 2);
        root.add(discardButton, 2, 2);
        
        Scene scene = new Scene(root, 480, 360);
        
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
        selectedPost = city.getPostOffice();
        chosenPost.setText(selectedPost.toString());
        stage.setTitle("Edit - " + city.getName());
        stage.show();
    }
    
    @Override
    public void stop() {
        postView.stop();
        stage.close();
    }
    
    public void eetPostDb(PostRepository val) {
        this.postDb = val;
    }
    
    public void setAddingMode(boolean mode) {
        addingMode = mode;
        if (mode)
            stage.setTitle("New City");
    }
    
    public boolean getAddingMode() {
        return addingMode;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public City getCity() {
        return editCity; //To change body of generated methods, choose Tools | Templates.
    }
    
}
