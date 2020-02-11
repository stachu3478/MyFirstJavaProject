/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout;

import database.CityRepository;
import models.Address;
import models.City;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mainLayout.components.FXSearchView;
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
    private FXSearchView<City> citySearch;
    private CityRepository cityDb;
    private City selectedCity;
    
    private Label cityLabel;
    private Text cityName;
    private Label streetLabel;
    private Label nrLabel;
    private Label slashLabel;
    private TextField streetInput;
    private TextField nrInput;
    private TextField inNrInput;
    private Button addCityButton;
    private Button editCityButton;
    private Button chooseCityButton;
    private Button saveAddressButton;
    private Button discardButton;
    
    private EventHandler<ActionEvent> updateHandler;
    
    private StandardGridPane root;
    private boolean addingMode;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        
        EventHandler<ActionEvent> uHandler = new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Updating interface...");
                citySearch.refresh();
                streetInput.setText(editAddress.getStreet());
                nrInput.setText(editAddress.getNr());
                inNrInput.setText(editAddress.getNr2());
            }
        };
        
        cityDb = new CityRepository();
        
        cityView = new FXEditCityView();
        cityView.eetPostDb(cityDb.getPostRepository());
        cityView.setOnUpdate(uHandler);
        cityView.setOnUpdate(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Updating interface...");
                City selected = cityView.getCity();
                if (selected != null) {
                    cityName.setText(selected.toString());
                    if (cityView.getAddingMode()) {
                        cityDb.addRecord(selected);
                        cityDb.saveList();
                    }
                    selectedCity = selected;
                }
            }
        });
        cityView.start(new Stage());
        
        citySearch = new FXSearchView<>();
        citySearch.start(new Stage());
        citySearch.setItemName("Post office");
        citySearch.setOnUpdate(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                City newCity = citySearch.getItem();
                if (newCity != null) {
                    cityName.setText(newCity.toString());
                    selectedCity = newCity;
                }
            }
        });
        
        cityLabel = new Label("City: ");
        streetLabel = new Label("Street name: ");
        nrLabel = new Label("At: ");
        slashLabel = new Label(" / ");
        cityName = new Text("No city");
        // TODO change to Text in edit city view
        
        streetInput = new TextField();
        nrInput = new TextField();
        nrInput.setPrefColumnCount(4);
        inNrInput = new TextField();
        inNrInput.setPrefColumnCount(4);
        
        addCityButton = new Button("New");
        addCityButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Adding city...");
                cityView.receive(cityDb.make());
                cityView.setAddingMode(true);
            }
        });
        
        editCityButton = new Button("Edit");
        editCityButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Editing city...");
                cityView.receive(editAddress.getCity());
                cityView.setAddingMode(false);
            }
        });
        
        chooseCityButton = new Button("Choose");
        chooseCityButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Choosing city...");
                citySearch.show(cityDb.getList());
            }
        });
        
        saveAddressButton = new Button("Save");
        saveAddressButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("Saving changes...");
                    editAddress.setCity(selectedCity);
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
        root.add(cityName, 1, 0);
        root.add(addCityButton, 2, 0);
        root.add(chooseCityButton, 3, 0);
        root.add(editCityButton, 4, 0);
        root.add(streetLabel, 0, 1);
        root.add(streetInput, 1, 1, 4, 1);
        root.add(nrLabel, 0, 2);
        root.add(nrInput, 1, 2);
        root.add(slashLabel, 2, 2);
        root.add(inNrInput, 3, 2);
        root.add(saveAddressButton, 0, 3, 2, 1);
        root.add(discardButton, 2, 3, 2, 1);
        
        Scene scene = new Scene(root, 480, 360);
        
        stage.setTitle("Edit address");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Stage is closing");
                cityView.stop();
                stage.close();
                citySearch.stop();
            }
        });
    }
    
    public void setOnUpdate(EventHandler<ActionEvent> h) {
        this.updateHandler = h;
    }
    
    @Override
    public void receive(Address address) {
        this.editAddress = address;
        selectedCity = address.getCity();
        streetInput.setText(address.getStreet());
        nrInput.setText(address.getNr());
        inNrInput.setText(address.getNr2());
        cityName.setText(selectedCity.getName());
        stage.show();
    }
    
    @Override
    public void stop() {
        cityView.stop();
        stage.close();
        citySearch.stop();
    }
    
    public void setCityDb(CityRepository cDb) {
        cityDb = cDb;
    }
    
    public Address getAddress() {
        return this.editAddress;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void setAddingMode(boolean b) {
        this.addingMode = b; //To change body of generated methods, choose Tools | Templates.
        if (b)
            stage.setTitle("New Address");
        else
            stage.setTitle("Edit Address");
    }
    
    public boolean getAddingMode() {
        return this.addingMode;
    }
    
}
