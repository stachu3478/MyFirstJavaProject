/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.IOException;
import models.PhoneNumber;

/**
 *
 * @author stachu
 */
public class PhoneRepository extends Repository<PhoneNumber> {
    private static final String filename = "phone.db";
    private static PhoneRepository rep = null;
    
    public static PhoneRepository getRepository() {
        if (rep == null) rep = new PhoneRepository();
        return rep;
    }
    
    public PhoneRepository() {
        super();
        try {
            setFile(filename);
            scan();
        } catch (IOException e) {
            System.out.println("No access. Dry run.");
        }
    }
    
    @Override
    public PhoneNumber make() {
        return new PhoneNumber();
    }
    
    @Override
    public PhoneNumber readItem() throws IOException {
        FileRecordReader reader = getReader();
        PhoneNumber phone = new PhoneNumber();
        phone.setCountry(CountryRepository.getRepository().getById(reader.readInteger()));
        phone.setNumberInt(reader.readInteger());
        return phone;
    };
    
    @Override
    public void writeItem(PhoneNumber phone) throws IOException {
        FileRecordReader writer = getReader();
        writer.writeInteger(phone.getCountry().getId());
        writer.writeInteger(phone.getNumberInt());
    };
}
