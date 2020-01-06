/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout.components;

import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;

/**
 *
 * @author stachu
 */
public class ObservableListSelector<T> {
    private ObservableList<T> preList;
    private FilteredList<T> itemList;
    private ListView<T> frontList;
    private String filterValue;
    private Predicate<T> filter;
    
    public ObservableListSelector() {
        frontList = new ListView();
        filterValue = "";
        filter = new Predicate<T>() {
            
            @Override
            public boolean test(T item) {
                System.out.println("Filtering...");
                System.out.println(filterValue);
                boolean passes = item.toString().toLowerCase().contains(filterValue);
                if (passes) System.out.println("Passed");
                else System.out.println("Filtered");
                return passes;
            }
        };
    }
    
    public void setListView(ListView<T> list) {
        this.frontList = list;
        setItems(list.getItems());
    }
    
    public ListView<T> getListView() {
        return this.frontList;
    }
    
    public void setItems(ObservableList<T> items) {
        this.preList = items;
        this.itemList = items.filtered(filter);
        frontList.setItems(itemList);
    }
    
    public void applyFilter(String val) {
        this.filterValue = val.toLowerCase();
        this.itemList = preList.filtered(filter);
        frontList.setItems(itemList);
    }
}
