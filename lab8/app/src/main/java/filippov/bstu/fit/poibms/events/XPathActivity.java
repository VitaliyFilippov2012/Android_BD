package filippov.bstu.fit.poibms.events;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import filippov.bstu.fit.poibms.events.Constants.Constants;
import filippov.bstu.fit.poibms.events.File.WorkWithFile;
import filippov.bstu.fit.poibms.events.File.WorkWithXML;

public class XPathActivity extends AppCompatActivity {
    EditText XPathEditText;
    ListView XPathListView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpath);

        XPathEditText = findViewById(R.id.XPathEditText);
        XPathListView = findViewById(R.id.XPathListView);
    }

    public void onClick_XPath(View view)
    {
        WorkWithFile wf = new WorkWithFile(Constants.EVENTS);
        WorkWithXML xml = new WorkWithXML(wf);

        ArrayList<String> list = xml.compileXPath(XPathEditText.getText().toString());
        if(list == null){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Некорректный запрос", Toast.LENGTH_SHORT);
            toast.show();

        }
        else {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            XPathListView.setAdapter(adapter);
        }

    }
}
