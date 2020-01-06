/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.IOException;
import models.City;

/**
 *
 * @author stachu
 */
public class CityRepository extends Repository<City> {
    private static final String filename = "phone.db";
    private static CityRepository rep = null;
    
    public static CityRepository getRepository() {
        if (rep == null) rep = new CityRepository();
        return rep;
    }
    
    public CityRepository() {
        super();
        try {
            setFile(filename);
            scan();
        } catch (IOException e) {
            System.out.println("No access. Dry run.");
        }
    }
    
    @Override
    public City readItem() throws IOException {
        FileRecordReader reader = getReader();
        City city = new City();
        city.setName(reader.readString());
        city.setPostOffice(PostRepository.getRepository().getById(reader.readInteger()));
        return city;
    };
    
    @Override
    public void writeItem(City city) throws IOException {
        FileRecordReader writer = getReader();
        writer.writeString(city.getName());
        writer.writeInteger(city.getPostOffice().getId());
    };
}
