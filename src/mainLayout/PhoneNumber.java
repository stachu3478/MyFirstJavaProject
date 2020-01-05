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
    private int number;
    
    public PhoneNumber() {
        this.number = 123456789;
        this.country = new Country();
    }
    
    public String setNumber(String num) throws NumberFormatException {
        if (num.length() != 9) throw new NumberFormatException();
        this.number = Integer.parseInt(num);
        return num;
    }
    
    public Country setCountry(Country ctry) {
        return this.country = ctry;
    }
    
    public String getValue() {
        return Integer.toString(this.number);
    }
    
    @Override
    public String toString() {
        return this.country.getDirectional() + " " + getValue();
    }
    
    public Country getCountry() {
        return this.country;
    }
}
