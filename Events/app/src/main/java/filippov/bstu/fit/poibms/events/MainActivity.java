package filippov.bstu.fit.poibms.events;

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
    private EditText editInfo;
    private EditText editName;
    final int COUNT_VALID_FIELDS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePicker = (DatePicker) findViewById(R.id.date_birthday);
        editDate = (EditText) findViewById(R.id.edit_event_date);
        editName = (EditText) findViewById(R.id.edit_event_name);
        editInfo = (EditText) findViewById(R.id.edit_event_info);

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
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_date);
                    validIcon.setVisibility(View.VISIBLE);
                    String[] parts = editDate.getText().toString().split("[\\/\\.]");
                    int year =Integer.valueOf(parts[2]) ;
                    int month = Integer.valueOf(parts[1]);
                    int day = Integer.valueOf(parts[0]);
                    datePicker.updateDate(year+1-1,month-1+1,day+1-1);

                }
                else{
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_date);
                    validIcon.setVisibility(View.INVISIBLE);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        editInfo.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (editInfo.getText().toString().isEmpty()) {
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_info);
                    validIcon.setVisibility(View.VISIBLE);
                }
                else{
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_info);
                    validIcon.setVisibility(View.INVISIBLE);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        editName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String regexp = "^[a-zA-Z-]{2,33}$";
                if (editName.getText().toString().matches(regexp)) {
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_name);
                    validIcon.setVisibility(View.VISIBLE);
                }
                else{
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_name);
                    validIcon.setVisibility(View.INVISIBLE);

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }

    public void onClickButtonSave(View view){
        if(checkCountValid()){
        }
    }


    private boolean checkCountValid(){
        ImageView[] arrImageView = {findViewById(R.id.valid_info),findViewById(R.id.valid_name),findViewById(R.id.valid_date)};
        int currentCountValidFields = 0;
        for(ImageView item : arrImageView){
            if(item.getVisibility() == View.VISIBLE){
                currentCountValidFields++;
            }
        }
        if(currentCountValidFields != COUNT_VALID_FIELDS){
            return false;
        }
        return true;
    }
}
