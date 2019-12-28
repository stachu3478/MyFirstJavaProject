/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainLayout.components.SelectorReceiver;

/**
 *
 * @author stachu
 */
public class FXPhoneView extends Application implements SelectorReceiver<PhoneNumber> {
    private Stage stage;
    
    private Label numberLabel;
    private Label countryLabel;
    private Text numberValue;
    private ChoiceBox<Country> countryValue;
    private Button saveButton;
    private Button cancelButton;
    
    private GridPane root;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        
        numberLabel = new Label("Phone number: ");
        countryLabel = new Label("Country: ");
        
        numberValue = new Text();
        countryValue = new ChoiceBox();
        ObservableList<Country> countries = FXCollections.observableArrayList(new Country(), new Country(), new Country());
        countryValue.setItems(countries);
        // TODO add countries to choice box
        
        saveButton = new Button();
        saveButton.setText("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Saving changes...");
                stage.close();
            }
        });
        
        cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Discarding changes...");
                stage.close();
            }
        });
        
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(15, 15, 15, 15));
        
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
    
    public void receive(PhoneNumber number) {
        // TODO assign parameters
        numberValue.setText(number.getValue());
        countryValue.setValue(number.getCountry());
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
