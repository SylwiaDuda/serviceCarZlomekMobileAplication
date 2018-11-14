package com.example.sylwi.servicecarzlomekmobileaplication.Service;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.example.sylwi.servicecarzlomekmobileaplication.R;

/**
 * Created by sylwi on 18.10.2018.
 */

public class FocusChangeListenerValidateSignInForm implements View.OnFocusChangeListener{

    EditText editText;
    Context context;
    public FocusChangeListenerValidateSignInForm(EditText editText, Context context) {
        this.editText = editText;
        this.context = context;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        String text = editText.getText().toString();
        boolean empty = TextUtils.isEmpty(text);
        boolean pattern=true;
       // editText.addTextChangedListener(new TextWatcherValidateForm(editText,context));
        if(editText.getId()== R.id.email){
            pattern= android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
        }
        if(!pattern && !b){
            editText.setError(context.getString(R.string.error_invalid_email));
        }else if(pattern){
            //editText.setError(null);
        }
        if(empty && !b){
            editText.setError(context.getString(R.string.error_field_required));
        }
    }
}
