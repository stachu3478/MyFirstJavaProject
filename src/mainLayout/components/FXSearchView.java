/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout.components;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainLayout.FXContactView;

/**
 *
 * @author stachu
 */
public class FXSearchView<T> extends Application {
    private Stage stage;
    
    private ObservableListSelector<T> filterer;
    private Label searchText;
    private TextField searchInput;
    private Button chooseButton;
    private Button discardButton;
    
    private EventHandler<ActionEvent> updateHandler;
    
    private StandardGridPane root;
    private Scene scene;
    
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        
        searchText = new Label("Search: ");
        
        searchInput = new TextField();
        searchInput.setTooltip(new Tooltip("Enter any text to search"));
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {

            System.out.println("Searching...");
            filterer.applyFilter(newValue);
        });
        
        filterer = new ObservableListSelector<>();
        ListView<T> listView = filterer.getListView();
        listView.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                chooseButton.setDisable(false);
            }
        });
        
        chooseButton = new Button("Choose");
        chooseButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Choosing item...");
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
        root.add(searchText, 0, 0);
        root.add(searchInput, 1, 0);
        root.add(listView, 0, 1, 2, 1);
        root.add(chooseButton, 0, 2);
        root.add(discardButton, 1, 2);
        
        scene = new Scene(root, 480, 360);
        scene.getStylesheets().add(FXContactView.class.getResource("FXContactView.css").toExternalForm());
        
        stage.setTitle("Select");
        stage.setScene(scene);
    }
    
    public void setOnUpdate(EventHandler<ActionEvent> h) {
        this.updateHandler = h;
    }
    
    public void setItemName(String name) {
        stage.setTitle("Select - " + name);
        searchText.setText("Search " + name + ":");
    }
    
    public T getItem() {
        return filterer.getListView().getSelectionModel().getSelectedItem();
    }
    
    public void show() {
        stage.show();
        chooseButton.setDisable(true);
        filterer.getListView().getSelectionModel().clearSelection();
    }
    
    public void show(ObservableList<T> list) {
        filterer.setItems(list);
        show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
