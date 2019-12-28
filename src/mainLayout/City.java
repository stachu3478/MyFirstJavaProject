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
class City {
    private PostOffice postOffice;
    private String name;
    
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
    
    public String getPostalCode() {
        return this.postOffice.getCode();
    }
    
    public String getPostCityName() {
        return this.postOffice.getCityName();
    }
}
