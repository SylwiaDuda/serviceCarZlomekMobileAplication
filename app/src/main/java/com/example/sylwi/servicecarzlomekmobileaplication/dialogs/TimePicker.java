package com.example.sylwi.servicecarzlomekmobileaplication.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.sylwi.servicecarzlomekmobileaplication.activity.AddVisitActivity;

import java.util.Calendar;
import java.util.Date;

public class TimePicker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private AddVisitActivity activity;

    @SuppressLint("ValidFragment")
    public TimePicker(AddVisitActivity activity) {
        this.activity = activity;
    }

    public TimePicker() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        if(this.activity.validateTime(hourOfDay) && activity.getVisitDate() != null) {
            activity.setVisitDate(new Date(activity.getVisitDate().getTime()+(hourOfDay*3600+ minute*60)*1000));
        }
    }
}
