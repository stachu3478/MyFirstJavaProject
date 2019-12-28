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
class PhoneNumber {
    private Country country;
    private Integer number;
    
    public PhoneNumber() {
        this.number = 123456789;
        this.country = new Country();
    }
    
    public String getValue() {
        return this.number.toString();
    }
    
    public String getFullNumber() {
        return this.country.getDirectional() + getValue();
    }
    
    public Country getCountry() {
        return this.country;
    }
}
