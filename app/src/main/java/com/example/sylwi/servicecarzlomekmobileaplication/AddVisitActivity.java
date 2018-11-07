package com.example.sylwi.servicecarzlomekmobileaplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddVisitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit);
        Button date = (Button)findViewById(R.id.visitDate);
        date.setOnClickListener(new DateButtonListener());
    }
}

class DateButtonListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
    }
}

