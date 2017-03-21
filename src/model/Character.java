package model;

import java.util.Random;

/**
 * Created by jouke on 21-3-2017.
 */
public class Character {
    /**
     * Get a random Character from the alphabet
     * @return A random Character from the alphabet
     */
    public static char getRandomCharacter(){
        //Make certain letters more frequent so it's easyer to make words
        int chance = 1 + (int)(Math.random() * 1000);
        if(chance > 500){
            //Make these chars more frequent
            String letterRange1 = "aeiuo"; //klinkers
            Random random = new Random();
            return letterRange1.charAt(random.nextInt(letterRange1.length()));
        }else{
            String letterRange2 = "bcdfghjklmnpqrstvwxyz"; //medeklinkers
            Random random = new Random();
            return letterRange2.charAt(random.nextInt(letterRange2.length()));
        }
    }
}
