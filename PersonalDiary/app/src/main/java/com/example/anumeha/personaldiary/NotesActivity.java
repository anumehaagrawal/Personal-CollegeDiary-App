package com.example.anumeha.personaldiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    EditText UserName, Notes;
    Button Add_Notes,Show_Notes;
    String NameHolder, NotesHolder;
    ListView ViewNotes;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder;
    SQLiteHelper sqLiteHelper;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Add_Notes = (Button) findViewById(R.id.AddNoteBut);
        Show_Notes = (Button)findViewById(R.id.button);
        UserName = (EditText)findViewById(R.id.editUserID);
        Notes = (EditText)findViewById(R.id.Addnotes);
        ViewNotes = (ListView) findViewById(R.id.viewnotes);
        sqLiteHelper = new SQLiteHelper(this);

        Add_Notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating SQLite database if dose n't exists
                SQLiteDataBaseBuild();

                // Creating SQLite table if dose n't exists.
                SQLiteTableBuild();

                // Checking EditText is empty or Not.
                CheckEditTextStatus();

                // Empty EditText After done inserting process.
                EmptyEditTextAfterDataInsert();


            }
        });
        Show_Notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arra = sqLiteHelper.getAppCategoryDetail();
                ArrayList<String> data_array = new ArrayList<>();

                for(int i=0;i<arra.size();i++){
                    data_array.add(arra.get(i).toString());
                }
                arrayAdapter = new ArrayAdapter<String>(NotesActivity.this, android.R.layout.simple_list_item_1,data_array);

                // DataBind ListView with items from ArrayAdapter
                ViewNotes.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();

            }
        });
    }

    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "
                + SQLiteHelper.TABLE_NOTE + "(" + SQLiteHelper.Table1_Column_ID + " INTEGER PRIMARY KEY," + SQLiteHelper.Table1_Column_1_Desc
                + " TEXT," + " FOREIGN KEY ("+ SQLiteHelper.Table1_Column_ID +") REFERENCES " + SQLiteHelper.TABLE_NAME + "("+SQLiteHelper.Table_Column_ID+"));");

    }

    public void InsertDataIntoSQLiteDatabase() {
        long a;
        // If editText is not empty then this block will executed.
        if (EditTextEmptyHolder == true) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("desc_id", NameHolder);
            contentValues.put("desc1", NotesHolder);
            a = sqLiteHelper.insert(SQLiteHelper.TABLE_NOTE, contentValues, SQLiteHelper.Table1_Column_1_Desc);
            if (a > 0) {
                Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
            }
            sqLiteDatabaseObj.close();

        }
    }

    public void EmptyEditTextAfterDataInsert(){

        UserName.getText().clear();

        Notes.getText().clear();

    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        NameHolder = UserName.getText().toString() ;
        NotesHolder = Notes.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(NotesHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
        InsertDataIntoSQLiteDatabase();
    }



}
