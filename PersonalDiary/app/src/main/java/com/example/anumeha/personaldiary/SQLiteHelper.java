package com.example.anumeha.personaldiary;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class SQLiteHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME="UserDataBase";

    public static final String TABLE_NAME = "UserTable";

    public static final String Table_Column_ID="id";

    public static final String Table_Column_1_Name="name";

    public static final String Table_Column_2_Email="email";

    public static final String Table_Column_3_Password="password";

    public static final String TABLE_NOTE = "DescTable";

    public static final String Table1_Column_ID = "desc_id";

    public static final String Table1_Column_1_Desc="desc1";

    public static final String TABLE_EXPE="ExpTable";

    public static final String Table21_Column_ID="exp_id";

    public static final String Table21_Column_1_salary="salary";

    public static final String Table2_Column_2_monthlyexp="monthlyexp";

    public static final String Table2_Column_2_savings="savings";

    public static final String Table2_Column_2_extras="extras";

    // private ContentValues cValues;
    //private SQLiteDatabase dataBase = null;
    private static final String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_ID+" INTEGER PRIMARY KEY , "+Table_Column_1_Name+" VARCHAR, "+Table_Column_2_Email+" VARCHAR, "+Table_Column_3_Password+" VARCHAR)";

    private static final String CREATE_TABLE_NOTE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NOTE + "(" + Table1_Column_ID + " INTEGER PRIMARY KEY," + Table1_Column_1_Desc
            + " TEXT," + " FOREIGN KEY ("+ Table1_Column_ID +") REFERENCES " + TABLE_NAME + "("+Table_Column_ID+"));";


    private static final String CREATE_TABLE_EXPENSES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_EXPE + "(" + Table21_Column_ID + " INTEGER PRIMARY KEY," + Table21_Column_1_salary
            + " TEXT," + Table2_Column_2_monthlyexp
            + " TEXT," + " FOREIGN KEY ("+ Table21_Column_ID +") REFERENCES " + TABLE_NAME + "("+Table_Column_ID+"));";
    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
        database.execSQL(CREATE_TABLE_NOTE);
        database.execSQL(CREATE_TABLE_EXPENSES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPE);

        onCreate(db);

    }

    public int updateRecord(String email, String password) {

        SQLiteDatabase dataBase = getWritableDatabase();
        ContentValues cValues = new ContentValues();

        //cValues.put(Table_Column_1_Name, name);
        cValues.put(Table_Column_2_Email, email);
        cValues.put(Table_Column_3_Password,password);
        //    Update data from database table
        long insertchek=dataBase.update(SQLiteHelper.TABLE_NAME, cValues,
                Table_Column_2_Email+"='"+email+"' AND "+Table_Column_3_Password+"='"+password+"'", null);
        dataBase.close();
        return (int)insertchek;
    }

    public long insert(String table,ContentValues cv,String whereclm)
    {
        SQLiteDatabase dataBase = getWritableDatabase();
        long a;

            a = dataBase.insert(table,whereclm,cv);
        return a;
    }

    public ArrayList<String> getAppCategoryDetail() {

        ArrayList<String> notes = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String note = cursor.getString(cursor.getColumnIndex(Table1_Column_1_Desc));
                note = note+'\n';
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }
    public ArrayList<String> getAppCategoryDetail1() {

        ArrayList<String> notes = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXPE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String note = "Expenses are";
               note = note+ cursor.getString(cursor.getColumnIndex(Table21_Column_1_salary));
                notes.add(note.toString());
                String notee = "Savings are";
                notee = notee + cursor.getString(cursor.getColumnIndex(Table2_Column_2_monthlyexp));
                notee = notee +'\n';
                notes.add(notee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }




}
