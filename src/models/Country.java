/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import database.Record;

/**
 *
 * @author stachu
 */
public class Country extends Record {
    private String name;
    private int baseNumber;
    
    public Country() {
        this.name = "Poland";
        this.baseNumber = 48;
    }
    
    public Country(String name, int dir) {
        this.name = name;
        this.baseNumber = dir;
    }
    
    public String getDirectional() {
        return "+" + Integer.toString(baseNumber);
    }
    
    public int getDirectionalNum() {
        return this.baseNumber;
    };
    
    public void setDirectional(int val) {
        this.baseNumber = val;
    };
    
    public String getName() {
        return this.name;
    };
    
    public void setName(String val) {
        this.name = val;
    };
    
    @Override
    public String toString() {
        return this.name + " (" + getDirectional() + ")";
    }
}
