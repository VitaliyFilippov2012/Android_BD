package filippov.bstu.fit.poibms.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

import filippov.bstu.fit.poibms.events.Events.CalendarEvents;
import filippov.bstu.fit.poibms.events.Events.Event;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private EditText editDate;
    private EditText editInfo;
    private EditText editName;
    private Button remove;
    final int COUNT_VALID_FIELDS = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePicker = (DatePicker) findViewById(R.id.date_birthday);
        editDate = (EditText) findViewById(R.id.edit_event_date);
        editName = (EditText) findViewById(R.id.edit_event_name);
        editInfo = (EditText) findViewById(R.id.edit_event_info);
        remove = findViewById(R.id.butt_remove);


        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener()
        {
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                String date = datePicker.getYear()+"."+datePicker.getMonth()+"."+datePicker.getDayOfMonth() ;
                if(!editDate.getText().toString().equals(date)){
                    editDate.setText(date);
                }
                if(checkEvent(date)){
                    remove.setVisibility(View.VISIBLE);
                }
                else {
                    remove.setVisibility(View.INVISIBLE);
                    resetField();
                }

            datePicker.setForegroundTintList(ColorStateList.valueOf(65));
            }
        });

        editDate.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String regexp = "^\\d{4}[\\/\\.]([0-1][0-2]|[0-9])[\\/\\.](([0-2]?[0-9])|3[0-1])";
                if (editDate.getText().toString().matches(regexp)) {
                    ImageView validIcon = (ImageView)findViewById(R.id.valid_date);
                    validIcon.setVisibility(View.VISIBLE);
                    String[] parts = editDate.getText().toString().split("[\\/\\.]");
                    int year =Integer.valueOf(parts[0]) ;
                    int month = Integer.valueOf(parts[1]);
                    int day = Integer.valueOf(parts[2]);
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
                if (!editInfo.getText().toString().isEmpty() && editInfo.getText().toString().length()>5) {
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
                if (!editName.getText().toString().isEmpty()) {
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

    private void resetField(){
        editInfo.setText("");
        editName.setText("");
    }

    public void onClickButtonSave(View view){
        if(checkCountValid()){
            Event newEvent = new Event(editName.getText().toString(),editInfo.getText().toString(),editDate.getText().toString());
            CalendarEvents.addEvent(newEvent);
            remove.setVisibility(View.VISIBLE);

        }
    }


    public void onClickButtonRemove(View view){
        Event removeEvent = new Event(editName.getText().toString(),editInfo.getText().toString(),editDate.getText().toString());
        if(CalendarEvents.removeEvent(removeEvent)){
            resetField();
        }
    }


    private boolean checkEvent(String date){
        Event event = CalendarEvents.getEventByDate(date);
        if(event != null){
            loadInfoOnActivityAboutEvent(event);
            return true;
        }
        return false;
    }

    private void loadInfoOnActivityAboutEvent(Event e){
        editName.setText(e.getName());
        editInfo.setText(e.getInfo());
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
