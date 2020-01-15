/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.IOException;
import javafx.collections.ObservableList;
import models.Address;

/**
 *
 * @author stachu
 */
public class AddressRepository extends Repository<Address> {
    private static final String filename = "address.db";
    private CityRepository cityDb;
    
    private void standardInit() {
        try {
            setFile(filename);
            scan();
        } catch (IOException e) {
            System.out.println("No access. Dry run.");
        }
    }
    
    public AddressRepository() {
        super();
        standardInit();
        cityDb = new CityRepository();
        scannedDone();
    }

    public AddressRepository(CityRepository cDb) {
        super();
        standardInit();
        cityDb = cDb;
        scannedDone();
    }
    
    @Override
    public Address readItem() throws IOException {
        FileRecordReader reader = getReader();
        Address addr = new Address();
        addr.setNr(reader.readInteger());
        addr.setNr2(reader.readInteger());
        addr.setStreet(reader.readString());
        addr.setCityId(reader.readInteger());
        return addr;
    };
    
    @Override
    public void writeItem(Address addr) throws IOException {
        FileRecordReader writer = getReader();
        writer.writeInteger(addr.getNrInt());
        writer.writeInteger(addr.getNrInt2());
        writer.writeString(addr.getStreet());
        writer.writeInteger(addr.getCityId());
    };
    
    public void scannedDone() {
        // TODO implement cit bind
        ObservableList<Address> rList = getList();
        for (int i = 0; i < rList.size(); i++) {
            Address addr = rList.get(i);
            addr.setCity(cityDb.getById(addr.getCityId()));
        }
    }

    public CityRepository getCityDb() {
        return cityDb; //To change body of generated methods, choose Tools | Templates.
    }
}
