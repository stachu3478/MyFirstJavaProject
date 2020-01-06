/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.IOException;
import java.io.RandomAccessFile;
/**
 *
 * @author stachu
 *//**
 *
 * @author stachu
 */
public class FileRecordReader {
    private RandomAccessFile file;
    
    public FileRecordReader(String filename) throws IOException {
        file = new RandomAccessFile(filename, "rw");
    };
    
    public FileRecordReader(RandomAccessFile f) throws IOException {
        this.file = f;
    };
    
    public String read() throws IOException {
        return file.readLine();
    };
    
    public Integer readInteger() throws IOException {
        return file.readInt();
    };
    
    public void writeInteger(int i) throws IOException {
        file.writeInt(i);
    };
    
    public String readString() throws IOException {
        int length = (int)file.readByte();
        String str = "";
        for (int n = 0; n < length; n++)
            str += file.readChar();
        return str;
    };
    
    public void writeString(String str) throws IOException {
        int length = str.length();
        file.write(length);
        file.writeChars(str);
    };
    
    public void resetPointer() throws IOException {
        file.seek(0L);
    };
    
    public void cutAndSave() throws IOException {
        file.setLength(file.getFilePointer());
        file.close();
    };
    
    public boolean isEmpty() throws IOException {
        return file.length() == 0;
    };
}
