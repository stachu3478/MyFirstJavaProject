/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.IOException;
import javafx.collections.ObservableList;
import models.City;
import models.PostOffice;

/**
 *
 * @author stachu
 */
public class PostRepository extends Repository<PostOffice> {
    private static final String filename = "phone.db";
    private static PostRepository rep = null;
    
    public static PostRepository getRepository() {
        if (rep == null) rep = new PostRepository();
        return rep;
    }
    
    public PostRepository() {
        super();
        try {
            setFile(filename);
            scan();
        } catch (IOException e) {
            System.out.println("No access. Dry run.");
        }
    }
    
    @Override
    public PostOffice readItem() throws IOException {
        FileRecordReader reader = getReader();
        PostOffice post = new PostOffice();
        post.setCityId(reader.readInteger());
        post.setCodeInt(reader.readInteger());
        return post;
    };
    
    @Override
    public void writeItem(PostOffice post) throws IOException {
        FileRecordReader writer = getReader();
        writer.writeInteger(post.getCity().getId());
        writer.writeInteger(post.getCodeInt());
    };
    
    @Override
    public void scanned() {
        // TODO implement cit bind
        ObservableList<PostOffice> rList = getList();
        Repository<City> cityRep = CityRepository.getRepository();
        for (int i = 0; i < rList.size(); i++) {
            PostOffice post = rList.get(i);
            post.setCity(cityRep.getById(post.getCityId()));
        }
    }
}
