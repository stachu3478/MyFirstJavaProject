/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import database.Record;
import java.util.ArrayList;

/**
 *
 * @author stachu
 */
public class Person extends Record {
    private String firstName;
    private String lastName;
    private Address address;
    private ArrayList<PhoneNumber> contacts;
    private int addressId;
    
    public Person() {
        firstName = "Stan";
        lastName = "Players";
        address = new Address();
        contacts = new ArrayList<>();
    }
    
    public String getFirstName(){
        return this.firstName;
    }
    
    public String setFirstName(String val){
        return this.firstName = val;
    }
    
    public String getLastName(){
        return this.lastName;
    }
    
    public String setLastName(String val){
        return this.lastName = val;
    }
    
    public Address getAddress() {
        return this.address;
    }
    
    public String getAddressString() {
        return this.address.getString();
    }
    
    public void setAddress(Address addr) {
        this.address = addr;
        this.addressId = addr.getId();
    }
    
    public int getAddressId() {
        return this.addressId;
    }
    
    public void setAddressId(int val) {
        this.addressId = val;
    }
    
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
    
    public ArrayList<PhoneNumber> getPhoneList() {
        return this.contacts;
    }
    
    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + "\n" + this.address.toString();
    }
}
