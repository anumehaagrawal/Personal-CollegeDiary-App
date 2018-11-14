package com.example.anumeha.personaldiary;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.anumeha.personaldiary.SQLiteHelper.TABLE_EXPE;
import static com.example.anumeha.personaldiary.SQLiteHelper.TABLE_NAME;
import static com.example.anumeha.personaldiary.SQLiteHelper.Table1_Column_ID;
import static com.example.anumeha.personaldiary.SQLiteHelper.Table21_Column_1_salary;
import static com.example.anumeha.personaldiary.SQLiteHelper.Table21_Column_ID;
import static com.example.anumeha.personaldiary.SQLiteHelper.Table2_Column_2_monthlyexp;
import static com.example.anumeha.personaldiary.SQLiteHelper.Table_Column_ID;

public class ExpensesActivity extends AppCompatActivity {

    EditText Uname,Salary,Mexp,Extras,Sav;
    ListView showtext;
    Button Add_exp,Showexp;
    String UNameHolder, SalaryHolder, MexpHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder;
    SQLiteHelper sqLiteHelper;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Add_exp = (Button) findViewById(R.id.sub);
        showtext = (ListView) findViewById(R.id.textView2);
        Showexp = (Button) findViewById(R.id.button2);
        Uname = (EditText) findViewById(R.id.exN);
        Salary = (EditText) findViewById(R.id.exS);
        Mexp = (EditText) findViewById(R.id.exE);


        sqLiteHelper = new SQLiteHelper(this);

        Add_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating SQLite database if dose n't exists
                SQLiteDataBaseBuild();

                // Creating SQLite table if dose n't exists.
                SQLiteTableBuild();

                // Checking EditText is empty or Not.
                InsertDataIntoSQLiteDatabase();

                // Empty EditText After done inserting process.
                EmptyEditTextAfterDataInsert();


            }
        });
        Showexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arra = sqLiteHelper.getAppCategoryDetail1();
                ArrayList<String> data_array = new ArrayList<>();

                for(int i=0;i<arra.size();i++){
                    data_array.add(arra.get(i).toString());
                }
                 arrayAdapter = new ArrayAdapter<String>(ExpensesActivity.this, android.R.layout.simple_list_item_1,data_array);

                // DataBind ListView with items from ArrayAdapter
                showtext.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();


            }
        });


    }
    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_EXPE + "(" + Table21_Column_ID + " INTEGER PRIMARY KEY," + Table21_Column_1_salary
                + " TEXT," + Table2_Column_2_monthlyexp
                + " TEXT," + " FOREIGN KEY ("+ Table21_Column_ID +") REFERENCES " + TABLE_NAME + "("+Table_Column_ID+"));");
    }

    public void InsertDataIntoSQLiteDatabase() {
        long a;
        // If editText is not empty then this block will executed.
        UNameHolder = Uname.getText().toString() ;
        SalaryHolder = Salary.getText().toString();
        MexpHolder = Mexp.getText().toString();


        ContentValues contentValues = new ContentValues();
        contentValues.put("exp_id", UNameHolder);
            contentValues.put("salary", SalaryHolder);
        contentValues.put("monthlyexp", MexpHolder);
            a = sqLiteHelper.insert(TABLE_EXPE, contentValues, SQLiteHelper.Table21_Column_1_salary);
            if (a > 0) {
                Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
            }
            sqLiteDatabaseObj.close();

        }

    public void EmptyEditTextAfterDataInsert(){

        Uname.getText().clear();
        Salary.getText().clear();
        Mexp.getText().clear();



    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        UNameHolder = Uname.getText().toString() ;
        SalaryHolder = Salary.getText().toString();


        if(TextUtils.isEmpty(UNameHolder) || TextUtils.isEmpty(SalaryHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }

    }


}


