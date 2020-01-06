/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author stachu
 */
public abstract class Record {
    private int id;
    
    public int getId() {
        return id;
    }
    
    public int setId(int val) {
        return this.id = val;
    }
}
