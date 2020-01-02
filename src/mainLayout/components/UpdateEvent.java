/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout.components;

import javafx.event.Event;
import javafx.event.EventTarget;

/**
 *
 * @author stachu
 */
public class UpdateEvent extends Event {
    
    public UpdateEvent(Object src, EventTarget trg) {
        super(src, trg, null);
    }
}
