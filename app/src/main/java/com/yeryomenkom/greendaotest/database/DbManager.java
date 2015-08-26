package com.yeryomenkom.greendaotest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.yeryomenkom.greendaotest.database.gen.DaoMaster;
import com.yeryomenkom.greendaotest.database.gen.DaoSession;
import com.yeryomenkom.greendaotest.database.gen.DbAnimal;
import com.yeryomenkom.greendaotest.database.gen.DbAnimalDao;
import com.yeryomenkom.greendaotest.database.gen.DbHead;
import com.yeryomenkom.greendaotest.database.gen.DbHeadDao;
import com.yeryomenkom.greendaotest.database.gen.DbLeg;
import com.yeryomenkom.greendaotest.database.gen.DbLegDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.async.AsyncSession;

/**
 * Created by Misha on 8/25/2015.
 */
public class DbManager {

    private static DbManager instance;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;

    private DbManager(final Context _context) {
        mHelper = new DaoMaster.DevOpenHelper(_context, "animals-database", null);
    }


    public static DbManager getInstance(final Context _context) {
        if (instance == null) {
            instance = new DbManager(_context);
        }

        return instance;
    }

    /**
     * Query for readable DB
     */
    private void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
    }

    public void dropDatabase() {
        try {
            openWritableDb();
            DaoMaster.dropAllTables(database, true); // drops all tables
            mHelper.onCreate(database);              // creates the tables
            asyncSession.deleteAll(DbAnimal.class);    // clear all elements from a table
            asyncSession.deleteAll(DbLeg.class);
            asyncSession.deleteAll(DbHead.class);

            asyncSession.waitForCompletion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Query for writable DB
     */
    private void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
    }

    public final void write(final DbAnimal _animal) {
        try {
            openWritableDb();

            DbHeadDao dbHeadDao = daoSession.getDbHeadDao();
            dbHeadDao.insertOrReplace(_animal.getHead());

            DbLegDao dbLegDao = daoSession.getDbLegDao();
            dbLegDao.insertOrReplaceInTx(_animal.getLegs());

            DbAnimalDao dbAnimalDao = daoSession.getDbAnimalDao();
            dbAnimalDao.insertOrReplace(_animal);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            daoSession.clear();
        }
    }

    public final void remove(final DbAnimal _animal) {
        try {
            openWritableDb();

            DbHeadDao dbHeadDao = daoSession.getDbHeadDao();
            dbHeadDao.deleteInTx(_animal.getHead());

            DbLegDao dbLegDao = daoSession.getDbLegDao();
            dbLegDao.deleteInTx(_animal.getLegs());

            DbAnimalDao dbAnimalDao = daoSession.getDbAnimalDao();
            dbAnimalDao.deleteInTx(_animal);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            daoSession.clear();
        }
    }

    public final List<DbAnimal> getAnimalsWichWeightLessThen(int _weight) {
        ArrayList<DbAnimal> animals = new ArrayList<>();
        try {
            openReadableDb();
            DbAnimalDao dbAnimalDao = daoSession.getDbAnimalDao();
            animals.addAll(dbAnimalDao.queryBuilder().where(DbAnimalDao.Properties.Weight.lt(_weight)).list());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            daoSession.clear();
        }

        return animals;
    }

    public final DbAnimal getAnimalById(final long _id) {
        DbAnimal dbAnimal = null;
        try {
            openReadableDb();
            DbAnimalDao dbAnimalDao = daoSession.getDbAnimalDao();
            dbAnimal = dbAnimalDao.loadDeep(_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            daoSession.clear();
        }

        return dbAnimal;
    }

    public final List<DbAnimal> getAllAnimals() {
        ArrayList<DbAnimal> animals = new ArrayList<>();
        try {
            openReadableDb();
            DbAnimalDao dbAnimalDao = daoSession.getDbAnimalDao();
            animals.addAll(dbAnimalDao.loadAll());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            daoSession.clear();
        }

        return animals;
    }
}
