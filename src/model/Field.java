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
     * Get the string value of the field
     * @return String
     */
    public String getValue(){
        return java.lang.Character.toString(value);
    }

    /**
     * Get the neighbors as a list
     * @return ArrayList
     */
    public ArrayList<Field> getNeighborList(){
        return neighBors;
    }

    /**
     * Return the ammount of neighbours of the currentfield
     * @return int
     */
    public int getAmountOfNeighbours(){
        return neighBors.size();
    }
}
