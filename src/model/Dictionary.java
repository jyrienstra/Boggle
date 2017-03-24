package model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Use this class to experiment with different collections and their speed
 * Created by jouke on 21-3-2017.
 */
public class Dictionary {
    File file;

    public Dictionary(File file){
        this.file = file;
    }

    public void setFile(File file){
        this.file = file;
    }

    /**
     * Convert a file to a array list containing words
     * Every newline is a new word
     * @return
     */
    public ArrayList<String> getArrayList(){
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String currentLine = null;
            try {
                while ((currentLine = bufferedReader.readLine()) != null) {
                    list.add(currentLine);
                }
            }catch (IOException i){
                System.out.println(i.getStackTrace());
            }
        }catch (FileNotFoundException f){
            System.out.println(f.getStackTrace());
        }
        return list;
    }

    /**
     * Convert a file to an hash map containing words
     * Every newline is a new word
     * @return
     */
    public HashSet<String> getHashSet(){
        HashSet<String> set = new HashSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String currentLine = null;
            try {
                while ((currentLine = bufferedReader.readLine()) != null) {
                    if(currentLine.length() >= 3)
                        set.add(currentLine);
                }
            }catch (IOException i){
                System.out.println(i.getStackTrace());
            }
        }catch (FileNotFoundException f){
            System.out.println(f.getStackTrace());
        }
        return set;
    }

    /**
     * Convert a file to an hash map containing words
     * Every newline is a new word
     * @return
     */
    public TreeSet<String> getTreeSet(){
        TreeSet<String> set = new TreeSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String currentLine = null;
            try {
                while ((currentLine = bufferedReader.readLine()) != null) {
                    set.add(currentLine);
                }
            }catch (IOException i){
                System.out.println(i.getStackTrace());
            }
        }catch (FileNotFoundException f){
            System.out.println(f.getStackTrace());
        }
        return set;
    }
}
