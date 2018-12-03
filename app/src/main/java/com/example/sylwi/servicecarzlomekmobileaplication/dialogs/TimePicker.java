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
        if (this.activity.validateTime(hourOfDay)) {
            String currentMinute = (minute < 10) ? "0" + Integer.toString(minute) :
                    Integer.toString(minute);
            String hour = (hourOfDay < 10) ? "0" + Integer.toString(hourOfDay) :
                    Integer.toString(hourOfDay);
            this.activity.setHour(hour + ":" + currentMinute);
        } else
            this.activity.notifyTimeProblem();
        this.activity.updateDateView();
    }
}
