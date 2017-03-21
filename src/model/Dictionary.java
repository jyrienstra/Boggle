package model;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jouke on 21-3-2017.
 */
public class Dictionary {
    File file;

    public Dictionary(File file){
        //    File file = new File("src/wordlist.txt"); //set default file to dutch
        this.file = file;
    }

    /**
     * Convert a file to a array list containing words
     * Every newline is a new word
     * @return
     */
    public ArrayList<String> getWordList(){
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
}
