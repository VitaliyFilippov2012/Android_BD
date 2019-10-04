package filippov.bstu.fit.poibms.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import static filippov.bstu.fit.poibms.lab5.Constants.FILE_NAME;

public class MainActivity extends AppCompatActivity {
    private EditText mKeyInput;

    private EditText mValueInput;

    private EditText mKeyOutput;

    private TextView mValueOutput;

    private Button mInput;

    private Button mOutput;

    private WorkWithFile mWorkWithFile;
    private File internalDir;
    private File baseFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWorkWithFile = new WorkWithFile();
        internalDir = getFilesDir();


        mKeyInput = findViewById(R.id.key_input);
        mKeyOutput = findViewById(R.id.key_output);
        mValueInput = findViewById(R.id.value_input);
        mValueOutput = findViewById(R.id.value_output);
        mInput = findViewById(R.id.input);
        mOutput = findViewById(R.id.output);

        loadFile();

    }

    public int getHashCode(String value){
        String hash = String.valueOf(value.hashCode());
        if(hash.length()<= 9){
            int n = 9 - hash.length();
            for(int i = 0; i < n;i++){
                hash = hash + "0";
            }
        }
        else
        {
            hash = hash.substring(0,9);
        }

        return Integer.valueOf(hash);

    }

    public void onClickOutput(View view){
        final int hash = getHashCode(mKeyOutput.getText().toString());
        final List<String> list = mWorkWithFile.getValue(baseFile, hash);
        mValueOutput.setText(list.toString());
    }

    public void onClickInput(View view){
        final int hash = getHashCode(mKeyInput.getText().toString());

        Log.d(Constants.TAG,String.valueOf(hash));
        final String value = mValueInput.getText().toString();
        Log.d(Constants.TAG,"1");
        mWorkWithFile.addValue(baseFile, value, hash);
        Log.d(Constants.TAG,"1_1");
        mKeyInput.setText("");
        mValueInput.setText("");
    }

    private void loadFile(){
        if (!mWorkWithFile.exists(internalDir, FILE_NAME)) {
            Toast.makeText(this, "Created new file", Toast.LENGTH_SHORT).show();
            try {
                baseFile = mWorkWithFile.createFile(internalDir, FILE_NAME);
            } catch (Exception e) {
                Log.d(Constants.TAG, "FileLibrary did't create");
            } finally {
                baseFile = new File(internalDir, FILE_NAME);
            }
        } else {
            baseFile = new File(internalDir, FILE_NAME);
        }
    }


}
