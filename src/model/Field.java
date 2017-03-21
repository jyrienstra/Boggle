package model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by jouke on 21-3-2017.
 */
public class Field {
    char value;
    ArrayList<Field> neighBors = new ArrayList<>();

    public Field(){
        value = Character.getRandomCharacter();
    }

    /**
     * Add a neighbor to this field
     * @param field
     */
    public void addNeighbor(Field field){
        neighBors.add(field);
    }

    /**
     * Get the value of the field
     * @return char
     */
    public char getValue(){
        return value;
    }

    /**
     * Get the neighbors as a list
     * @return ArrayList
     */
    public ArrayList<Field> getNeighborList(){
        return neighBors;
    }


}
