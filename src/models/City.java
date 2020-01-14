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
public class City extends Record {
    private PostOffice postOffice;
    private String name;
    private int postId;
    
    public City() {
        this.postOffice = new PostOffice();
        this.name = "Anchor City";
    }
    
    public City(PostOffice office) {
        this.postOffice = office;
        this.name = "Karkanaum";
    }
    
    public String getName() {
        return this.name;
    }
    
    public String setName(String nm) {
        return this.name = nm;
    }
    
    public String getPostalCode() {
        return this.postOffice.getCode();
    }
    
    public String getPostCityName() {
        return this.postOffice.getCityName();
    }
    
    public PostOffice getPostOffice() {
        return this.postOffice;
    }
    
    public PostOffice setPostOffice(PostOffice pst) {
        this.postId = pst.getId();
        return this.postOffice = pst;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    public int getPostId() {
        return this.postId; //To change body of generated methods, choose Tools | Templates.
    }

    public void setPostId(int readInteger) {
        this.postId = readInteger; //To change body of generated methods, choose Tools | Templates.
    }
}
