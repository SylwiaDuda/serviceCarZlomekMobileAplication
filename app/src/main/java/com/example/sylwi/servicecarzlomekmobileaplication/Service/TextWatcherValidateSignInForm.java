package com.example.sylwi.servicecarzlomekmobileaplication.Service;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by sylwi on 23.10.2018.
 */

public class TextWatcherValidateSignInForm implements TextWatcher{

    EditText editText;
    Context context;
    public TextWatcherValidateSignInForm(EditText editText, Context context) {
        this.editText = editText;
        this.context = context;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        editText.setError(null);
    }
}
