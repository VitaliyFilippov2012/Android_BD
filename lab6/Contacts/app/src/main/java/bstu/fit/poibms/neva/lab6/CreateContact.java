package bstu.fit.poibms.neva.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

public class CreateContact extends AppCompatActivity {

    private DatePicker datePicker;
    private EditText editDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        datePicker = (DatePicker) findViewById(R.id.date_birthday);
        editDate = (EditText) findViewById(R.id.edit_birthday);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener()
        {
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                editDate.setText(datePicker.getDayOfMonth()+"."+datePicker.getMonth()+"."+ datePicker.getYear());
            }
        });

        editDate.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String regexp = "([0-2][0-9]|3[0-1])[\\/\\.][0-1][0-2][\\/\\.]\\d{4}";
                if (editDate.getText().toString().matches(regexp)) {
                    String[] parts = editDate.getText().toString().split("[\\/\\.]");
                    int year =Integer.valueOf(parts[2]) ;
                    int month = Integer.valueOf(parts[1]);
                    int day = Integer.valueOf(parts[0]);
                    datePicker.updateDate(year+1-1,month-1+1,day+1-1);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    public void onClickButtonWriteA(View view){

    }

    public void  onClickButtonWriteB(View view){

    }


}
