package com.example.sylwi.servicecarzlomekmobileaplication.dialogs;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.example.sylwi.servicecarzlomekmobileaplication.activity.AddVisitActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public DatePicker() {
    }

    private AddVisitActivity activity;

    @SuppressLint("ValidFragment")
    public DatePicker(AddVisitActivity activity) {
        this.activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
        GregorianCalendar picked = new GregorianCalendar(year, month, day);
        if (this.activity.validateDate(picked)) {
            activity.setVisitDate(picked.getTime());
        } else
            activity.notifyDateProblem();
        this.activity.updateDateView();
    }
}
