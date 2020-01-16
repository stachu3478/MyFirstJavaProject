/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author stachu
 */
public abstract class Repository<T extends Record> {
    private int records;
    private int lastId;
    private int[] indexes;
    private FileRecordReader reader;
    private ObservableList<T> list;
    private boolean indexToReload;
    
    private static boolean isInit = false;
    private static String homeDir;
    private static String sep;
    
    public Repository() {
        records = 0;
        lastId = -1;
        indexToReload = true;
    };
    
    public void setFile(String file) throws IOException {
        if (!isInit) init();
        reader = new FileRecordReader(homeDir + sep + file);
        if (reader.isEmpty()) records = 0;
        else records = reader.readInteger();
        list = FXCollections.observableList(new ArrayList<>());
    };
    
    public int getRecordCount() {
        return records;
    };
    
    public FileRecordReader getReader() {
        return reader;
    };
    
    public static void init() {
        try {
            sep = System.getProperty("file.separator");
            homeDir = System.getProperty("user.home") + sep + ".KsiazkaAdresowa";
        } catch (SecurityException e) {
            sep = "\\";
            homeDir = "%AppData%\\.KsiazkaAdresowa";
        }
        File file = new File(homeDir);
        file.mkdir();
        isInit = true;
    };
    
    public ObservableList<T> scan() {
        list = FXCollections.observableList(new ArrayList<>());
        try {
            reader.resetPointer();
            records = reader.readInteger();
            lastId = reader.readInteger();
            System.out.println(lastId);
            indexes = new int[lastId + 1];
            for (int n = 0; n < records; n++) {
                T rec = readRecord();
                list.add(rec);
                indexes[rec.getId()] = n;
            }
            scanned();
        } catch (IOException e) {
            // Unable to load or partial load
            if (list.isEmpty()) System.out.println("Unable to load file.");
            else System.out.println("Partial read with errors.");
        }
        return FXCollections.observableList(list);
    };
    
    public ObservableList<T> getList() {
        if (list == null) list = FXCollections.observableList(new ArrayList<T>());
        return list;
    };
    
    public void saveList() {
        try {
            reader.resetPointer();
            records = list.size();
            reader.writeInteger(records);
            reader.writeInteger(lastId);
            for (int n = 0; n < records; n++)
                writeRecord(list.get(n));
        } catch (IOException e) {
            // Unable to save, rollback changes?
            System.out.println("File corrupted. Working on restore system");
        }
    };
    
    public void prepareIndex() {
        if (indexToReload) {
            indexes = new int[lastId + 1];
            int n = list.size();
            for (int i = 0; i < n; i++)
                indexes[list.get(i).getId()] = i;
            indexToReload = false;
        }
    };
    
    public T getById(int id) {
        if (id > lastId) return null;
        prepareIndex();
        return list.get(indexes[id]);
    };
    
    public void addRecord(T item) {
        item.setId(++lastId);
        list.add(item);
        indexToReload = true;
        records++;
    };
    
    public T readRecord() throws IOException {
        int id = reader.readInteger();
        T item = readItem();
        item.setId(id);
        return item;
    };
    
    public void writeRecord(T rec) throws IOException {
        reader.writeInteger(rec.getId());
        writeItem(rec);
    };
    
    public void scanned() {
        System.out.println("Scanned done");
    };
    
    public T any() {
        if (records == 0)
            addRecord(make());
        return list.get(records - 1);
    }
    
    public abstract T make();
    public abstract T readItem() throws IOException;
    public abstract void writeItem(T rec) throws IOException;
}
