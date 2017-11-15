package com.magicsu.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.magicsu.criminalintent.database.CrimeDbSchema.CrimeTable;
import com.magicsu.criminalintent.model.Crime;

/**
 * Created by admin on 2017/11/15.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("create table %s( _id integer primary key autoincrement, %s, %s, %s, %s)",
                CrimeTable.NAME, CrimeTable.Cols.UUID, CrimeTable.Cols.TITLE, CrimeTable.Cols.DATE, CrimeTable.Cols.SOLVED)
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
