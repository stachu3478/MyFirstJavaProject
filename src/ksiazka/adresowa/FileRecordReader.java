/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksiazka.adresowa;

import java.io.IOException;
import java.io.RandomAccessFile;
/**
 *
 * @author stachu
 *//**
 *
 * @author stachu
 */
public abstract class FileRecordReader {
    private RandomAccessFile file;
    private Integer[] byteSequesnce;
    private Integer currentIndex;
    
    public String read() throws IOException {
        return file.readLine();
    };
    
    public Integer readInteger() throws IOException {
        return file.readInt();
    };
    
    public void init(String filename) throws IOException {
        file = new RandomAccessFile("filename", "rw");
    };
}
