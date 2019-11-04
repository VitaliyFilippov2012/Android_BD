package bstu.fit.poibms.neva.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

public class CreateContact extends AppCompatActivity {

    private DatePicker datePicker;
    private EditText editDate;
    private EditText editPhone;
    private EditText editName;
    private EditText editSurname;
    final int COUNT_VALID_FIELDS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        datePicker = (DatePicker) findViewById(R.id.date_birthday);
        editDate = (EditText) findViewById(R.id.edit_birthday);
        editName = (EditText) findViewById(R.id.edit_name);
        editSurname = (EditText) findViewById(R.id.edit_surname);
        editPhone = (EditText) findViewById(R.id.edit_phone);

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

        editPhone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String regexp = "^[+]375[\\s]?[(]?(29|33)[)]?[\\s]?\\d{3}([\\s-]?\\d{2}){2}$";
                if (editPhone.getText().toString().matches(regexp)) {
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_phone);
                    validIcon.setVisibility(View.VISIBLE);
                }
                else{
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_phone);
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

        editSurname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String regexp = "^[a-zA-Z-]{2,33}$";
                if (editSurname.getText().toString().matches(regexp)) {
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_surname);
                    validIcon.setVisibility(View.VISIBLE);
                }
                else{
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_surname);
                    validIcon.setVisibility(View.INVISIBLE);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }

    public void onClickButtonWriteA(View view){
        if(checkCountValid()){
        }
    }

    public void  onClickButtonWriteB(View view){
        if(checkCountValid()){

        }
    }

    private boolean checkCountValid(){
        ImageView[] arrImageView = {findViewById(R.id.valid_phone),findViewById(R.id.valid_name),findViewById(R.id.valid_surname),findViewById(R.id.valid_birthday)};
        int currentCountValidFields = 0;
        for(ImageView item : arrImageView){
            if(item.getVisibility() == View.VISIBLE){
                currentCountValidFields++;
            }
        }
        if(currentCountValidFields !=COUNT_VALID_FIELDS){
            return false;
        }
        return true;
    }

}
