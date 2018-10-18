package com.example.sylwi.servicecarzlomekmobileaplication.Service;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.example.sylwi.servicecarzlomekmobileaplication.R;

/**
 * Created by sylwi on 18.10.2018.
 */

public class FocusChangeListenerValidate implements View.OnFocusChangeListener{

    EditText editText;
    Context context;
    public FocusChangeListenerValidate(EditText editText, Context context) {
        this.editText = editText;
        this.context = context;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        String text = editText.getText().toString();
        boolean empty = TextUtils.isEmpty(text);
        boolean pattern=true;
        if(editText.getId()== R.id.email){
           /* Pattern pattern = Pattern.compile("[A-ZĄĆĘŁŃÓŚŻŹ][a-ząćęłńóśżź]+");
            Matcher matcher = pattern.matcher(text);*/
            pattern= android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
        }
        if(!pattern && !b){
            editText.setError(context.getString(R.string.error_invalid_email));
        }else if(pattern){
            editText.setError(null);
        }
        if(empty && !b){
            editText.setError(context.getString(R.string.error_field_required));
        }
    }
}
