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
class PostOffice {
    private City city;
    private Integer postalCode;
    
    public PostOffice() {
        this.city = new City(this);
        this.postalCode = 69029;
    }
    
    public String getCode() {
        String zip = this.postalCode.toString();
        return zip.substring(0, 2) + "-" + zip.substring(2);
    }
    
    public String getCityName() {
        return this.city.getName();
    }
}
