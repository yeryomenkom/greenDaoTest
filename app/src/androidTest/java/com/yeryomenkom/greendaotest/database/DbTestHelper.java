package com.yeryomenkom.greendaotest.database;

import com.yeryomenkom.greendaotest.database.gen.DbAnimal;
import com.yeryomenkom.greendaotest.database.gen.DbHead;
import com.yeryomenkom.greendaotest.database.gen.DbLeg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Misha on 8/25/2015.
 */
public abstract class DbTestHelper {

    public static DbAnimal createTestAnimalCat(long _id) {
        DbAnimal dbAnimal = new DbAnimal(_id);
        dbAnimal.setName("cat");
        dbAnimal.setWeight(5);

        DbHead head = new DbHead();
        head.setNumberOfEyes(2);
        head.setVoice("mrrrr");

        dbAnimal.setHead(head);
        dbAnimal.setLegs(createLegs(4,20,_id));

        return dbAnimal;
    }

    public static DbAnimal createTestAnimalDog(long _id) {
        DbAnimal dbAnimal = new DbAnimal(_id);
        dbAnimal.setName("dog");
        dbAnimal.setWeight(10);

        DbHead head = new DbHead();
        head.setId(_id);
        head.setNumberOfEyes(2);
        head.setVoice("gav");

        dbAnimal.setHead(head);
        dbAnimal.setLegs(createLegs(4,40,_id));

        return dbAnimal;
    }

    public static List<DbLeg> createLegs(int _number, int _length, long _animal_id) {
        Random random = new Random();
        ArrayList<DbLeg> legs = new ArrayList<>(_number);

        DbLeg leg;
        for (int i=0; i<_number; i++) {
            leg = new DbLeg();
            leg.setId(generateLegID(_animal_id, i+1));
            leg.setAnimalIdForLeg(_animal_id);
            leg.setColor(random.nextBoolean() ? "black" : "white");
            leg.setLength(_length);
            legs.add(leg);
        }

        return legs;
    }

    public static long generateLegID(long _animalID, int _legCount) {
        return Long.parseLong(Long.toString(_animalID) + Long.toString(_legCount));
    }
}
