/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.IOException;
import javafx.collections.ObservableList;
import models.Address;
import models.Person;
import models.PhoneNumber;

/**
 *
 * @author stachu
 */
public class PeopleRepository extends Repository<Person> {
    private static final String filename = "people.db";
    private PhoneRepository phoneDb;
    private AddressRepository addressDb;
    // TODO fix address repository initialization
    
    private void standardInit() {
        try {
            setFile(filename);
            scan();
        } catch (IOException e) {
            System.out.println("No access. Dry run.");
        }
    }
    
    public PeopleRepository() {
        super();
        standardInit();
        phoneDb = new PhoneRepository();
        addressDb = new AddressRepository();
        scannedDone();
    }
    
    @Override
    public Person make() {
        return new Person(addressDb.any());
    }
    
    @Override
    public Person readItem() throws IOException {
        FileRecordReader reader = getReader();
        Person person = new Person();
        person.setFirstName(reader.readString());
        person.setLastName(reader.readString());
        person.setAddressId(reader.readInteger());
        return person;
    };
    
    @Override
    public void writeItem(Person person) throws IOException {
        FileRecordReader writer = getReader();
        writer.writeString(person.getFirstName());
        writer.writeString(person.getLastName());
        writer.writeInteger(person.getAddressId());
    };
    
    public void scannedDone() {
        ObservableList<Person> rList = getList();
        for (int i = 0; i < rList.size(); i++) {
            Person person = rList.get(i);
            person.setAddress(addressDb.getById(person.getAddressId()));
        }
        
        ObservableList<PhoneNumber> pList = phoneDb.getList();
        for (int i = 0; i < pList.size(); i++) {
            PhoneNumber phone = pList.get(i);
            Person person = getById(phone.getPersonId());
            if (person != null)
                person.getPhoneList().add(phone);   
            // Unfort. trashes in database
        }
    }

    public AddressRepository getAddressDb() {
        return addressDb; //To change body of generated methods, choose Tools | Templates.
    }
    
    public PhoneRepository getPhoneDb() {
        return phoneDb;
    }
}
