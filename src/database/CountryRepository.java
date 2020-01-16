/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.IOException;
import models.Country;

/**
 *
 * @author stachu
 */
public class CountryRepository extends Repository<Country> {
    private static final String filename = "country";
    private static CountryRepository rep = null;
    
    public static CountryRepository getRepository() {
        if (rep == null) rep = new CountryRepository();
        return rep;
    }
    
    public CountryRepository() {
        super();
        try {
            setFile(filename);
            if (getReader().isEmpty()) {
                addRecord(new Country("Afganistan", 93));
                addRecord(new Country("Åland Islands", 358));
                addRecord(new Country("Albania", 355));
                addRecord(new Country("Algieria", 213));
                addRecord(new Country("Pakistan", 92));
                addRecord(new Country("Kazakhstan", 76));
                addRecord(new Country("Korea, North", 850));
                addRecord(new Country("Korea, South", 82));
                addRecord(new Country("Kosovo", 383));
                addRecord(new Country("Philippines", 63));
                addRecord(new Country("Poland", 48));
                addRecord(new Country("Israel", 972));
                addRecord(new Country("Kyrgyzstan", 996));
                addRecord(new Country("Tajikistan", 992));
                addRecord(new Country("Turkmenistan", 993));
                addRecord(new Country("Uzbekistan", 993));
                addRecord(new Country("Ukraine", 380));
                addRecord(new Country("Turkey", 90));
                addRecord(new Country("Tanzania", 255));
                addRecord(new Country("Taiwan", 886));
                addRecord(new Country("Syria", 963));
                addRecord(new Country("Singapore", 65));
                addRecord(new Country("Saudi Arabia", 966));
                addRecord(new Country("Russia", 7));
                addRecord(new Country("Romania", 40));
                addRecord(new Country("Portugal", 351));
                addRecord(new Country("Canada", 1));
                addRecord(new Country("Bulgaria", 359));
                addRecord(new Country("China", 86));
                addRecord(new Country("Austria", 43));
                addRecord(new Country("Bangladesh", 880));
                addRecord(new Country("Belgium", 32));
                addRecord(new Country("Easter Island", 56));
                addRecord(new Country("Egypt", 20));
                addRecord(new Country("Ethiopia", 251));
                addRecord(new Country("France", 33));
                addRecord(new Country("Germany", 49));
                addRecord(new Country("Greece", 30));
                addRecord(new Country("Iran", 98));
                addRecord(new Country("Spain", 34));
                addRecord(new Country("Sweden", 46));
                addRecord(new Country("Taiwan", 886));
                addRecord(new Country("Serbia", 381));
                addRecord(new Country("Slovakia", 421));
                addRecord(new Country("Madagaskar", 261));
                addRecord(new Country("Italy", 39));
                addRecord(new Country("Vatikan", 379));
                addRecord(new Country("Vietnam", 84));
                addRecord(new Country("United States", 1));
                addRecord(new Country("Uruguay", 598));
                addRecord(new Country("Thailand", 66));
                addRecord(new Country("Christmas Island", 6189164));
                addRecord(new Country("Australia", 61));
                addRecord(new Country("Denmark", 45));
                addRecord(new Country("Czech Republic", 420));
                saveList();
                System.out.println("SaveD.");
            } else {
                scan();
            }
        } catch (IOException e) {
            System.out.println("No access. Dry run.");
        }
    }
    
    @Override
    public Country make() {
        return new Country();
    }
    
    @Override
    public Country readItem() throws IOException {
        FileRecordReader reader = getReader();
        Country country = new Country();
        country.setDirectional(reader.readInteger());
        country.setName(reader.readString());
        return country;
    };
    
    @Override
    public void writeItem(Country country) throws IOException {
        FileRecordReader writer = getReader();
        writer.writeInteger(country.getDirectionalNum());
        writer.writeString(country.getName());
    };
}
