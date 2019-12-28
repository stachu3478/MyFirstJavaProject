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
class Country {
    private String name;
    private Integer baseNumber;
    
    public Country() {
        this.name = "Poland";
        this.baseNumber = 48;
    }
    
    public String getDirectional() {
        return "+" + this.baseNumber.toString();
    }
    
    @Override
    public String toString() {
        return this.name + " (" + getDirectional() + ")";
    }
}
