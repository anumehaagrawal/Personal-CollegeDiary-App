package com.example.anumeha.personaldiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class SMSActivity extends AppCompatActivity {
    TextView smview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        Intent intent = getIntent();
        String message = intent.getStringExtra(DashboardActivity.SMSContent);
        smview = (TextView)findViewById(R.id.textView3);
        smview.setText(message);

    }
}
