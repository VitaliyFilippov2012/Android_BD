package bstu.fit.poibms.neva.lab6.birthdaysearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private EditText editDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePicker = (DatePicker) findViewById(R.id.date_birthday);
        editDate = (EditText) findViewById(R.id.edit_birthday);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener()
        {
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                String str = datePicker.getDayOfMonth()+"."+datePicker.getMonth()+"."+ datePicker.getYear();
                if(!editDate.getText().toString().equals(str)){
                    editDate.setText(str);
                }

            }
        });
        editDate.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String regexp = "^(([0-2]?[0-9])|3[0-1])[\\/\\.]([0-1][0-2]|[0-9])[\\/\\.]\\d{4}";
                if (editDate.getText().toString().matches(regexp)) {
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_birthday);
                    validIcon.setVisibility(View.VISIBLE);
                    String[] parts = editDate.getText().toString().split("[\\/\\.]");
                    int year =Integer.valueOf(parts[2]) ;
                    int month = Integer.valueOf(parts[1]);
                    int day = Integer.valueOf(parts[0]);
                    datePicker.updateDate(year+1-1,month-1+1,day+1-1);

                }
                else{
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_birthday);
                    validIcon.setVisibility(View.INVISIBLE);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }


}