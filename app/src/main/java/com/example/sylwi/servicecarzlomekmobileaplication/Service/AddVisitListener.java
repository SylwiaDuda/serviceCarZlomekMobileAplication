package com.example.sylwi.servicecarzlomekmobileaplication.Service;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.sylwi.servicecarzlomekmobileaplication.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddVisitListener implements View.OnClickListener {
    private String visitDate;
    private Boolean isOverview;
    @Override
    public void onClick(View view) {
        ViewGroup layout = (ViewGroup)view.getParent().getParent();
        for (int i = 0; i < layout.getChildCount(); i++) {
            layout.getChildAt(i);
            getValue(view);
        }
    }

    public boolean getValue(View view) {
        switch (view.getId()) {
            case (R.id.date_form):
                String date = ((EditText)view).getText().toString();
                GregorianCalendar calendar = new GregorianCalendar();
                if(date.matches("[0-3]{1}[0-9]{1}-[0-1]{1}[0-9]{1}-["+ calendar.get(Calendar.YEAR)+" " + (calendar.get(Calendar.YEAR) + 1) + "]{1}"))
                this.visitDate = date;
                Log.d("Wizyta", this.visitDate);

            case (R.id.is_overview):
                this.isOverview = ((CheckBox)view).isChecked();
                Log.d("przegląd", Boolean.toString(this.isOverview));
                return true;
        }
        return false;
    }
}
