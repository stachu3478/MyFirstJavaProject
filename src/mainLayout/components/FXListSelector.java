/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout.components;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author stachu
 */
public class FXListSelector<T, V extends SelectorReceiver<T>> {
    private ListView<T> listView;
    private boolean doubleClick;
    private int selected;
    private V receiver;
    
    public FXListSelector() {
        listView = new ListView<T>();
        listView.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                int newSelection = listView.getSelectionModel().getSelectedIndex();
                if (selected != newSelection || doubleClick) {
                    selected = newSelection;
                    doubleClick = false;
                    if (selected != -1)
                        receiver.receive(listView.getItems().get(selected));
                } else if (selected != -1) {
                    doubleClick = true;
                }
            }
        });
    }
    
    public void setItems(ObservableList<T> items) {
        listView.setItems(items);
    }
    
    public void setTooltip(String str) {
        listView.setTooltip(new Tooltip(str));
    }
    
    public void setReceiver(V rvr) {
        receiver = rvr;
    }
    
    public ListView getListView() {
        return listView;
    }
    
    public void refresh() {
        listView.refresh();
    }

    public ObservableList<T> getItems() {
        return listView.getItems();
    }
}
