/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout;

import java.util.ArrayList;

/**
 *
 * @author stachu
 */
public class Person {
    private String firstName;
    private String lastName;
    private Address address;
    private ArrayList<PhoneNumber> contacts;
    
    public Person() {
        firstName = "Stan";
        lastName = "Players";
        address = new Address();
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
    
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
    
    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + "\n" + this.address.toString();
    }
}
