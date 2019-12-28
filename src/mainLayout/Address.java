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
class Address {
    private City city;
    private String street;
    private Integer nr;
    private Integer inNr;
    
    public Address() {
        this.city = new City();
        this.street = "New Dead";
        this.nr = 13;
        this.inNr = 27;
    }
    
    public String getString() {
        return
                city.getName() + "\n"
                + street + ", "
                + nr.toString() + "/"
                + inNr.toString() + "\n"
                + city.getPostalCode() + " "
                + city.getPostCityName();
    }
}
