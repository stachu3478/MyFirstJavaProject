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
public class PhoneNumber extends Record {
    private Country country;
    private int number;
    private int personId;
    
    public PhoneNumber() {
        this.number = 123456789;
        this.country = new Country();
    }
    
    public PhoneNumber(Person person) {
        this.number = 987654321;
        this.country = new Country();
        this.personId = person.getId();
    }
    
    public int getNumberInt() {
        return this.number;
    }
    
    public String setNumber(String num) throws NumberFormatException {
        if (num.length() != 9) throw new NumberFormatException();
        this.number = Integer.parseInt(num);
        return num;
    }
    
    public void setNumberInt(int num) {
        this.number = num;
    };
    
    public Country getCountry() {
        return this.country;
    }
    
    public Country setCountry(Country ctry) {
        return this.country = ctry;
    }
    
    public String getValue() {
        return Integer.toString(this.number);
    }
    
    public int getPersonId() {
        return this.personId;
    }
    
    public void setPersonId(int val) {
        this.personId = val;
    }
    
    @Override
    public String toString() {
        return this.country.getDirectional() + " " + getValue();
    }
}
