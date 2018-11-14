package com.example.anumeha.personaldiary;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DisplayActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 1000;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 1001;
    ListView listView;
    EditText event_name_test , event_name_desc, event_sd,event_ed;
    ArrayList<String> eventDescs = new ArrayList<String>();
    ArrayList<String> eventTitles = new ArrayList<String>();
    ArrayList<String> startarray = new ArrayList<String>();
    ArrayList<String> endarray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        listView = findViewById(R.id.list_view);
        event_name_test = findViewById(R.id.event_name);
        event_name_desc = findViewById(R.id.event_desc);
        event_sd = findViewById(R.id.event_sd);
        event_ed = findViewById(R.id.event_ed);

    }

    private boolean isEventAlreadyExist(String eventTitle,int sdate,int smonth,int edate,int emonth) {
        final String[] INSTANCE_PROJECTION = new String[] {
                CalendarContract.Instances.EVENT_ID,      // 0
                CalendarContract.Instances.BEGIN,         // 1
                CalendarContract.Instances.TITLE          // 2
        };

        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2018, smonth, edate, 8, 00);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2018,  emonth,  edate, 8, 00);
        endMillis = endTime.getTimeInMillis();

        // The ID of the recurring event whose instances you are searching for in the Instances table
        String selection = CalendarContract.Instances.TITLE + " = ?";
        String[] selectionArgs = new String[] {eventTitle};

        // Construct the query with the desired date range.
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        // Submit the query
        Cursor cur =  getContentResolver().query(builder.build(), INSTANCE_PROJECTION, selection, selectionArgs, null);

        return cur.getCount() > 0;
    }

    public void addEvent(View view) {

        eventTitles.add("Course");
        eventDescs.add("register now");
        startarray.add("24-11");
        endarray.add("29-11");
        Log.v("Calendar", "Event:  " + eventDescs.get(0));
        String eventTitle = event_name_test.getText().toString();
        String eventDesc = event_name_desc.getText().toString();
        String startDate = event_sd.getText().toString();
        String[] sdates = startDate.split("-");
        String endDate = event_ed.getText().toString();
        String[] edates = endDate.split("-");
        int sdate = Integer.parseInt(sdates[0]);
        int smonth = Integer.parseInt(sdates[1]);
        int edate = Integer.parseInt(edates[0]);
        int emonth = Integer.parseInt(edates[1]);
        if (isEventAlreadyExist(eventTitle,sdate,smonth,edate,emonth)) {
            Snackbar.make(view, " event already exist!", Snackbar.LENGTH_SHORT).show();
            return;
        }

        long calID = 4;
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2018, smonth, sdate, 8, 00);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2018, edate, emonth, 8, 00);
        endMillis = endTime.getTimeInMillis();

        ContentResolver cr = getContentResolver();
        long startMillisn = 0;
        long endMillisn = 0;
        ContentValues valuesn = new ContentValues();

        //-------------------------------Keep adding the lines in this block with changes dates a, events and desc------------------------------------------------------------//

        Calendar beginTimen = Calendar.getInstance();
        beginTime.set(2018, 07,16, 8, 00);
        startMillisn = beginTimen.getTimeInMillis();
        Calendar endTimen = Calendar.getInstance();
        endTimen.set(2018,07,16, 5, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Branch Change");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Branch change for III sem students.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018, 07,17, 8, 00);
        startMillisn = beginTimen.getTimeInMillis();
        endTimen.set(2018,07,18, 5, 00);
        endMillisn = endTimen.getTimeInMillis();



        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Course Registration");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Course registration for all branches without fine.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,07,18, 8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,07,18,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Classes Starts");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"College resumes from today.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,07,19, 8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,07,26,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Registration with Fine");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"People need to pay fine for late registration.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,07,27, 8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,07,27, 5, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Registration Ends");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Last day to register for courses with fine.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,8,06,8, 00);
        startMillisn = beginTimen.getTimeInMillis();
        endTimen.set(2018,8,6,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Institute Foundation Day");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"No classes.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,8,8,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,8,8, 5, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Drop/cU option");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Last date to keep/drop the courses for the sem.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,9,13,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,9,13,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Ganesh Chaturthi");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Celebration in college. No classes.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,9,21,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,8,21,8 , 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Muharam");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"No classes.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,9,24,8,00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,9,29,8,00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Mid Semester Exam");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Exams will be held in their respective department.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,10,02,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,10,02,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Mahatma Gandhi's Birthday");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Campus cleaning program. No classes.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,10,05,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,10,05,8,00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Mid Semester Results");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Last day to check the papers.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,10,11,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,10,15, 8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Engineer Fest");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"No classes.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,10,16,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,10,16, 8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Monday's Timetable");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"None");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,10,18,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,10,18, 8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Friday's Timetable");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"None");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,10,19,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,10,19, 8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Dussehra");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"No classes.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,10,23,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,10,26,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Class Committee Metting");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Slots will be announced later.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,11,05,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,11,06,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Pre Registration");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Announcement of the courses for next semester.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,11,07,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,11,07,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Diwali");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Celebration at Sports Complex. No classes.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,11,15,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,11,15,5, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "CLASSES END");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Last teaching day for the semester.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,11,16,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,11,29,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "End Semester Exams");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Exams will be in department.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();
        beginTime.set(2018,12,03,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,12,03,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Exam Results");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Last day to check papers.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,12,05,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,12,05,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Appel on grades");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"None");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,12,10,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,12,14,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Semester Make-up Exams");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Location will be announced later.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        beginTime.set(2018,12,26,8, 00);
        startMillisn = beginTimen.getTimeInMillis();

        endTimen.set(2018,12,26,8, 00);
        endMillisn = endTimen.getTimeInMillis();


        valuesn.put(CalendarContract.Events.DTSTART, startMillisn);
        valuesn.put(CalendarContract.Events.DTEND, endMillisn);
        valuesn.put(CalendarContract.Events.TITLE, "Even Semester Reporting");
        valuesn.put(CalendarContract.Events.DESCRIPTION,"Registration for next Semester begins.");
        valuesn.put(CalendarContract.Events.CALENDAR_ID, calID);
        valuesn.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        valuesn.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, valuesn);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
        valuesn.clear();

        //--------------------------------------//
        //------------ Dont modify -------//
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, eventTitle);
        values.put(CalendarContract.Events.DESCRIPTION,eventDesc);
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        values.put(CalendarContract.Events.ORGANIZER, "anuzenith29@gmail.com");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            long eventID = Long.parseLong(uri.getLastPathSegment());

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
        }
//---------------------------------------------------------------------------------------------//


    }



    public void readEvents(View view) {
        final String[] INSTANCE_PROJECTION = new String[] {
                CalendarContract.Instances.EVENT_ID,      // 0
                CalendarContract.Instances.BEGIN,         // 1
                CalendarContract.Instances.TITLE,          // 2
                CalendarContract.Instances.ORGANIZER
        };

        // The indices for the projection array above.
        final int PROJECTION_ID_INDEX = 0;
        final int PROJECTION_BEGIN_INDEX = 1;
        final int PROJECTION_TITLE_INDEX = 2;
        final int PROJECTION_ORGANIZER_INDEX = 3;

        // Specify the date range you want to search for recurring event instances
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2018, 6, 1, 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2019, 1, 24, 8, 0);
        long endMillis = endTime.getTimeInMillis();


        // The ID of the recurring event whose instances you are searching for in the Instances table
        String selection = CalendarContract.Instances.EVENT_ID + " = ?";
        String[] selectionArgs = new String[] {"207"};

        // Construct the query with the desired date range.
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        // Submit the query
        Cursor cur =  getContentResolver().query(builder.build(), INSTANCE_PROJECTION, null, null, null);


        ArrayList<String> events = new ArrayList<>();
        events.clear();
        while (cur.moveToNext()) {

            // Get the field values
            long eventID = cur.getLong(PROJECTION_ID_INDEX);
            long beginVal = cur.getLong(PROJECTION_BEGIN_INDEX);
            String title = cur.getString(PROJECTION_TITLE_INDEX);
            String organizer = cur.getString(PROJECTION_ORGANIZER_INDEX);

            // Do something with the values.
            Log.i("Calendar", "Event:  " + title);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(beginVal);
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Log.i("Calendar", "Date: " + formatter.format(calendar.getTime()));

            events.add(String.format("Event: %s\nOrganizer: %s\nDate: %s", title, organizer, formatter.format(calendar.getTime())));
        }
        ;
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, events);
        listView.setAdapter(stringArrayAdapter);
    }
}