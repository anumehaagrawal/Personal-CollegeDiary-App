package com.example.anumeha.personaldiary;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    String EmailHolder;
    private static final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 1000;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 1001;
    ListView listView;
    TextView Email;
    Button LogOUT,SmsAdd;
    Button Calendar, CalendarAdd, Notes, Expenses;
    public static final String SMSContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Email = (TextView) findViewById(R.id.textView1);
        LogOUT = (Button) findViewById(R.id.button1);
        Calendar = (Button) findViewById(R.id.calbut);
        CalendarAdd = (Button) findViewById(R.id.caladd);
        Notes = (Button) findViewById(R.id.detailadd);
        Expenses = (Button) findViewById(R.id.expbut);
        SmsAdd = (Button)findViewById(R.id.smsadd);

        Intent intent = getIntent();
        final StringBuilder smsBuilder = new StringBuilder();
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_ALL = "content://sms/";

        Uri uri = Uri.parse(SMS_URI_INBOX);
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        Cursor cur = getContentResolver().query(uri, projection, "address='+919481271324'", null, "date desc");
        if (cur.moveToFirst()) {
            int index_Address = cur.getColumnIndex("address");
            int index_Person = cur.getColumnIndex("person");
            int index_Body = cur.getColumnIndex("body");
            int index_Date = cur.getColumnIndex("date");
            int index_Type = cur.getColumnIndex("type");
            do {
                String strAddress = cur.getString(index_Address);
                int intPerson = cur.getInt(index_Person);
                String strbody = cur.getString(index_Body);
                long longDate = cur.getLong(index_Date);
                int int_Type = cur.getInt(index_Type);

                smsBuilder.append("[ ");
                smsBuilder.append(strAddress + ", ");
                smsBuilder.append(intPerson + ", ");
                smsBuilder.append(strbody + ", ");
                smsBuilder.append(longDate + ", ");
                smsBuilder.append(int_Type);
                smsBuilder.append(" ]\n\n");
            } while (cur.moveToNext());

            if (!cur.isClosed()) {
                cur.close();
                cur = null;
            }
        } else {
            smsBuilder.append("no result!");
        } // end if




        // Receiving User Email Send By MainActivity.
        EmailHolder = intent.getStringExtra(MainActivity.UserEmail);

        // Setting up received email to TextView.
        Email.setText(Email.getText().toString() + EmailHolder);

        // Adding click listener to Log Out button.
        LogOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Finishing current DashBoard activity on button click.
                finish();

                Toast.makeText(DashboardActivity.this, "Log Out Successfull", Toast.LENGTH_LONG).show();

            }
        });

        Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, CalendarActivity.class));
            }
        });
        CalendarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, DisplayActivity.class));
            }
        });
        Notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, NotesActivity.class));
            }
        });
        Expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ExpensesActivity.class));
            }
        });
        SmsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,SMSActivity.class);
                intent.putExtra(SMSContent,smsBuilder.toString());
                startActivity(intent);
            }
        });


    }
}
