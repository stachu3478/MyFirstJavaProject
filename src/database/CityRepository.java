/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.IOException;
import javafx.collections.ObservableList;
import models.City;

/**
 *
 * @author stachu
 */
public class CityRepository extends Repository<City> {
    private static final String filename = "city.db";
    private PostRepository postDb;
    
    private void standardInit() {
        try {
            setFile(filename);
            scan();
        } catch (IOException e) {
            System.out.println("No access. Dry run.");
        }
    }
    
    public CityRepository() {
        super();
        standardInit();
        postDb = new PostRepository(this);
        scannedDone();
    }

    public CityRepository(PostRepository aThis) {
        super();
        standardInit();
        postDb = aThis;
        scannedDone();
    }
    
    @Override
    public City make() {
        return new City(postDb.any());
    }
    
    @Override
    public City readItem() throws IOException {
        FileRecordReader reader = getReader();
        City city = new City();
        city.setName(reader.readString());
        city.setPostId(reader.readInteger());
        return city;
    };
    
    @Override
    public void writeItem(City city) throws IOException {
        FileRecordReader writer = getReader();
        writer.writeString(city.getName());
        writer.writeInteger(city.getPostOffice().getId());
    };
    
    public void scannedDone() {
        ObservableList<City> rList = getList();
        for (int i = 0; i < rList.size(); i++) {
            City city = rList.get(i);
            city.setPostOffice(postDb.getById(city.getPostId()));
        }
    }
    
    public PostRepository getPostRepository() {
        return this.postDb;
    }
}
