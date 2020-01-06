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
public class PostOffice extends Record {
    private City city;
    private int postalCode;
    private int cityId;
    
    private String feed0(int n) {
        if (n < 1) return "";
        else return "0" + feed0(n - 1);
    }
    
    public PostOffice() {
        this.city = new City(this);
        this.postalCode = 69029;
    }
    
    public String getCode() {
        String zip = Integer.toString(postalCode);
        zip = feed0(5 - zip.length()) + zip;
        return zip.substring(0, 2) + "-" + zip.substring(2);
    }
    
    public String setCode(String p1, String p2) throws NumberFormatException {
        if (p1.length() != 2 || p2.length() != 3)
            throw new NumberFormatException();
        String strCode = p1 + p2;
        this.postalCode = Integer.parseInt(strCode);
        return strCode;
    }
    
    public int getCodeInt() {
        return this.postalCode;
    }
    
    public int setCodeInt(int code) {
        return this.postalCode = code;
    }
    
    public String getCode1() {
        return getCode().substring(0, 2);
    }
    
    public String getCode2() {
        return getCode().substring(3);
    }
    
    public String getCityName() {
        return this.city.getName();
    }
    
    public City getCity() {
        return this.city;
    }
    
    public City setCity(City ct) {
        return this.city = ct;
    }
    
    public int getCityId() {
        return this.cityId;
    }
    
    public int setCityId(int ct) {
        return this.cityId = ct;
    }
    
    @Override
    public String toString() {
        return getCode() + " " + this.city.toString();
    }
}
