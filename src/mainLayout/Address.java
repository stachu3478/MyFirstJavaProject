/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainLayout;

/**
 *
 * @author stachu
 */
public class Address {
    private City city;
    private String street;
    private int nr;
    private int inNr;
    
    public Address() {
        this.city = new City();
        this.street = "New Dead";
        this.nr = 13;
        this.inNr = 27;
    }
    
    public City getCity() {
        return this.city;
    }
    
    public City setCity(City ct) {
        return this.city = ct;
    }
    
    public String getStreet() {
        return this.street;
    }
    
    public String setStreet(String str) {
        return this.street = str;
    }
    
    public String getNr() {
        return Integer.toString(this.nr);
    }
    
    public String setNr(String nr) throws NumberFormatException {
        this.nr = Integer.parseInt(nr);
        return nr;
    }
    
    public String getNr2() {
        return Integer.toString(this.inNr);
    }
    
    public String setNr2(String nr) throws NumberFormatException {
        this.inNr = Integer.parseInt(nr);
        return nr;
    }
    
    public String getString() {
        return
                city.getName() + "\n"
                + street + ", "
                + getNr() + "/"
                + getNr2() + "\n"
                + city.getPostalCode() + " "
                + city.getPostCityName();
    }
    
    @Override
    public String toString() {
        return
                city.getName() + " "
                + street + ", "
                + getNr() + "/"
                + getNr2() + " "
                + city.getPostalCode() + " "
                + city.getPostCityName();
    }
}
