package com.yeryomenkom.greendaotest.database;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.yeryomenkom.greendaotest.database.gen.DbAnimal;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Created by Misha on 8/25/2015.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    @After
    public void dropDB() {
        DbManager.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext()).dropDatabase();
    }

    @Test
    public void WriteAndRemoveAnimal() {
        DbManager dbManager = DbManager.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
        DbAnimal cat = DbTestHelper.createTestAnimalCat(12);
        dbManager.write(cat);

        dbManager.remove(cat);

        DbAnimal resultAnimal = dbManager.getAnimalById(cat.getId());

        Assert.assertNull("Result is not null", resultAnimal);
    }

    @Test
    public void WriteAndFindAnimalWichWeightLessThen() {
        DbManager dbManager = DbManager.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
        DbAnimal cat = DbTestHelper.createTestAnimalCat(12);
        DbAnimal dog = DbTestHelper.createTestAnimalDog(13);

        dbManager.write(cat);
        dbManager.write(dog);

        List<DbAnimal> resultAnimals = dbManager.getAnimalsWichWeightLessThen(dog.getWeight());

        Assert.assertEquals("Result size is incorrect", 1, resultAnimals.size());

        DbAnimal resultCat = resultAnimals.get(0);

        Assert.assertEquals("Cat id not equal", cat.getId(), resultCat.getId());
        Assert.assertEquals("Cat name not equal", cat.getName(), resultCat.getName());
        Assert.assertEquals("Cat weight not equal", cat.getWeight(), resultCat.getWeight());
        Assert.assertEquals("Cat head(voice) not equal", cat.getHead().getVoice(), resultCat.getHead().getVoice());
        Assert.assertEquals("Cat head(number of eyes) not equal", cat.getHead().getNumberOfEyes(),
                resultCat.getHead().getNumberOfEyes());
        Assert.assertEquals("Cat number of legs not equal", cat.getLegs().size(), resultCat.getLegs().size());

        for (int i=0; i<resultCat.getLegs().size(); i++) {
            Assert.assertEquals("Leg(length) not equal", cat.getLegs().get(i).getLength(),
                    resultCat.getLegs().get(i).getLength());
            Assert.assertEquals("Leg(color) not equal", cat.getLegs().get(i).getColor(),
                    resultCat.getLegs().get(i).getColor());
        }
    }

    @Test
    public void WriteAndFindAnimalById() {
        DbManager dbManager = DbManager.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
        DbAnimal cat = DbTestHelper.createTestAnimalCat(12);
        DbAnimal dog = DbTestHelper.createTestAnimalDog(13);

        dbManager.write(cat);
        dbManager.write(dog);

        DbAnimal resultCat = dbManager.getAnimalById(cat.getId());

        Assert.assertEquals("Cat id not equal", cat.getId(), resultCat.getId());
        Assert.assertEquals("Cat name not equal", cat.getName(), resultCat.getName());
        Assert.assertEquals("Cat weight not equal", cat.getWeight(), resultCat.getWeight());
        Assert.assertEquals("Cat head(voice) not equal", cat.getHead().getVoice(), resultCat.getHead().getVoice());
        Assert.assertEquals("Cat head(number of eyes) not equal", cat.getHead().getNumberOfEyes(),
                resultCat.getHead().getNumberOfEyes());
        Assert.assertEquals("Cat number of legs not equal", cat.getLegs().size(), resultCat.getLegs().size());

        for (int i=0; i<resultCat.getLegs().size(); i++) {
            Assert.assertEquals("Leg(length) not equal", cat.getLegs().get(i).getLength(),
                    resultCat.getLegs().get(i).getLength());
            Assert.assertEquals("Leg(color) not equal", cat.getLegs().get(i).getColor(),
                    resultCat.getLegs().get(i).getColor());
        }
    }

    @Test
    public void WriteAndReadAnimals() {
        DbManager dbManager = DbManager.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
        DbAnimal cat = DbTestHelper.createTestAnimalCat(12);
        DbAnimal dog = DbTestHelper.createTestAnimalDog(13);

        dbManager.write(cat);
        dbManager.write(dog);

        List<DbAnimal> resultAnimals = dbManager.getAllAnimals();

        Assert.assertEquals("Result size of collection is incorrect", 2, resultAnimals.size());

        DbAnimal resultCat = resultAnimals.get(0);
        DbAnimal resultDog = resultAnimals.get(1);

        Assert.assertEquals("Cat id not equal", cat.getId(), resultCat.getId());
        Assert.assertEquals("Cat name not equal", cat.getName(), resultCat.getName());
        Assert.assertEquals("Cat weight not equal", cat.getWeight(), resultCat.getWeight());
        Assert.assertEquals("Cat head(voice) not equal", cat.getHead().getVoice(), resultCat.getHead().getVoice());
        Assert.assertEquals("Cat head(number of eyes) not equal", cat.getHead().getNumberOfEyes(),
                resultCat.getHead().getNumberOfEyes());
        Assert.assertEquals("Cat number of legs not equal", cat.getLegs().size(), resultCat.getLegs().size());

        for (int i=0; i<resultCat.getLegs().size(); i++) {
            Assert.assertEquals("Leg(length) not equal", cat.getLegs().get(i).getLength(),
                    resultCat.getLegs().get(i).getLength());
            Assert.assertEquals("Leg(color) not equal", cat.getLegs().get(i).getColor(),
                    resultCat.getLegs().get(i).getColor());
        }


        Assert.assertEquals("Dog id not equal", dog.getId(), resultDog.getId());
        Assert.assertEquals("Dog name not equal", dog.getName(), resultDog.getName());
        Assert.assertEquals("Dog weight not equal", dog.getWeight(), resultDog.getWeight());
        Assert.assertEquals("Dog head(voice) not equal", dog.getHead().getVoice(), resultDog.getHead().getVoice());
        Assert.assertEquals("Dog head(number of eyes) not equal", dog.getHead().getNumberOfEyes(),
                resultDog.getHead().getNumberOfEyes());
        Assert.assertEquals("Dog number of legs not equal", dog.getLegs().size(), resultDog.getLegs().size());

        for (int i=0; i<resultDog.getLegs().size(); i++) {
            Assert.assertEquals("Leg(length) not equal", dog.getLegs().get(i).getLength(),
                    resultDog.getLegs().get(i).getLength());
            Assert.assertEquals("Leg(color) not equal", dog.getLegs().get(i).getColor(),
                    resultDog.getLegs().get(i).getColor());
        }

    }
}
