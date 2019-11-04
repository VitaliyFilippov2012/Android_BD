package bstu.fit.poibms.neva.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SearchInfo extends AppCompatActivity {

    private RadioGroup radioGroup;
    private TextView textSearchString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_type);
        textSearchString = (TextView)findViewById(R.id.edit_searchString);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                if(checkedRadioButton.getText().equals("Name")){
                    textSearchString.setHint(R.string.text_enterName);
                    Log.d("lab6","onSearchTypeChanged:Name");

                }
                else{
                    textSearchString.setHint(R.string.text_enterSurname);
                    Log.d("lab6","onSearchTypeChanged:Surname");

                }
            }
        });
    }
}
