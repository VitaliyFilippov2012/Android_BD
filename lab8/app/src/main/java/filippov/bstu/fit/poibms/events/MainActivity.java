package filippov.bstu.fit.poibms.events;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import filippov.bstu.fit.poibms.events.Events.CalendarEvents;
import filippov.bstu.fit.poibms.events.Events.Category.Category;
import filippov.bstu.fit.poibms.events.Events.Event;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private EditText editDate;
    private EditText editInfo;
    private EditText editName;
    private TextView textViewPos;
    private Spinner editCategory;
    private Button remove;
    final int COUNT_VALID_FIELDS = 3;
    private ArrayList<Event> eventInDate;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePicker = (DatePicker) findViewById(R.id.date_birthday);
        editDate = (EditText) findViewById(R.id.edit_event_date);
        editName = (EditText) findViewById(R.id.edit_event_name);
        editCategory = (Spinner) findViewById(R.id.edit_event_category);
        editInfo = (EditText) findViewById(R.id.edit_event_info);
        textViewPos = (TextView) findViewById(R.id.text_pos_count);
        remove = findViewById(R.id.butt_remove);
        eventInDate = new ArrayList<>();
        position = 0;
        setCategory();

        datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> setEventToDatePicker());

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

    private void setCategory(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Category.getCategoryNames());
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        editCategory.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.new_p:
                LayoutInflater li = LayoutInflater.from(this);
                View promptsView = li.inflate(R.layout.prompt, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
                mDialogBuilder.setView(promptsView);
                final EditText input = (EditText) promptsView.findViewById(R.id.input_text);
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Category.addCategoryNames(input.getText().toString());
                                        setCategory();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.show();
                return true;
            case R.id.del_all_category:
                Category.clear();
                return true;
            case R.id.del_event:
                CalendarEvents.clear();
                return true;
            case R.id.del_category:
                LayoutInflater l = LayoutInflater.from(this);
                View p = l.inflate(R.layout.prompt, null);
                AlertDialog.Builder mDialogB = new AlertDialog.Builder(this);
                mDialogB.setView(p);
                final EditText inp = (EditText) p.findViewById(R.id.input_text);
                mDialogB
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Category.removeCategory(inp.getText().toString());
                                        setCategory();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertD = mDialogB.create();
                alertD.show();
                return true;
            case R.id.xpath:
                Intent intent = new Intent(this,XPathActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

   private void setEventToDatePicker(){
       eventInDate.clear();
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
   }


    private void resetField(){
        editInfo.setText("");
        editName.setText("");
    }

    public void onClickButtonSave(View view){
        if(checkCountValid()){
            Event newEvent = new Event(editName.getText().toString(),editInfo.getText().toString(),editDate.getText().toString(),editCategory.getSelectedItem().toString());
            CalendarEvents.addEvent(newEvent);
            setEventToDatePicker();
        }
    }

    public void onClickButtonNext(View view){
        int pos = Integer.valueOf(textViewPos.getText().toString().split("/")[0].toString());
        loadInfoOnActivityAboutEvent(pos);
    }

    public void onClickButtonPrev(View view){
        int pos = Integer.valueOf(textViewPos.getText().toString().split("/")[0].toString());
        loadInfoOnActivityAboutEvent(pos-2);
    }

    public void onClickButtonRemove(View view){
        Event removeEvent = new Event(editName.getText().toString(),editInfo.getText().toString(),editDate.getText().toString(),editCategory.getSelectedItem().toString());
        if(CalendarEvents.removeEvent(removeEvent)){
            resetField();
        }
    }


    private boolean checkEvent(String date){
        eventInDate = CalendarEvents.getEventByDate(date);
        if(!eventInDate.isEmpty()){
            loadInfoOnActivityAboutEvent(0);
            return true;
        }
        return false;
    }

    private void loadInfoOnActivityAboutEvent(int position){
        if(eventInDate.size() < position || position < 0){
            return;
        }
        textViewPos.setText((position+1)+"/"+eventInDate.size());
        if(eventInDate.size()-1 < position){
            resetField();
            return;
        }
        editName.setText(eventInDate.get(position).getName());
        editInfo.setText(eventInDate.get(position).getInfo());
        if(!Category.getCategoryNames().contains(eventInDate.get(position).getCategory())){
            Category.addCategoryNames(eventInDate.get(position).getCategory());
        }
        int p = Category.getCategoryNames().indexOf(eventInDate.get(position).getCategory());
        editCategory.setSelection(p);
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
