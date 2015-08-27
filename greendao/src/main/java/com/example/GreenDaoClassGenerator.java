package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;
import de.greenrobot.daogenerator.ToOne;

public class GreenDaoClassGenerator {
    private static final String PROJECT_DIR = System.getProperty("user.dir").replace("\\", "/");

    private static final String OUT_DIR = PROJECT_DIR + "/src/main/java/";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.yeryomenkom.greendaotest.database.gen");

        //позволяет избежать полной регенерации кода сущностей
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        new DaoGenerator().generateAll(schema, OUT_DIR.replace("greendao","app"));
    }

    /**
     * Create tables and the relationships between them
     */
    private static void addTables(Schema schema) {
        /* entities */
        Entity animal = addAnimal(schema);
        Entity leg = addLeg(schema);
        Entity head = addHead(schema);

        /* properties */
        Property animalIdForLeg = leg.addLongProperty("animalIdForLeg").notNull().getProperty();
        Property headIDForAnimal = animal.addLongProperty("headID").notNull().getProperty();

        /* relationships between entities */
        animal.addToOne(head, headIDForAnimal, "head");
        animal.addToMany(leg, animalIdForLeg, "legs");
    }

    private static Entity addAnimal(Schema schema) {
        Entity animal = schema.addEntity("DbAnimal");
        animal.addIdProperty().primaryKey().notNull();
        animal.addStringProperty("name");
        animal.addIntProperty("weight");

        return animal;
    }

    private static Entity addLeg(Schema schema) {
        Entity leg = schema.addEntity("DbLeg");
        leg.addIdProperty().primaryKey().notNull();
        leg.addIntProperty("length");
        leg.addStringProperty("color");

        return leg;
    }

    private static Entity addHead(Schema schema) {
        Entity head = schema.addEntity("DbHead");
        head.addIdProperty().notNull().primaryKey();
        head.addIntProperty("numberOfEyes");
        head.addStringProperty("voice");

        return head;
    }
}
