package filippovvitaliyleonidovich.bstu.fit.lab_4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    WorkWithFile workWithFile;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        workWithFile = new WorkWithFile(getFilesDir()+"infoText.txt");
        if(!workWithFile.checkFile()){
            AlertDialog.Builder b= new AlertDialog.Builder(this);
            b.setTitle("Создать infoText.txt").setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("Log_02", "Создание файла " + "infoText.txt");
                }
            });
            workWithFile.createFile();
            AlertDialog a=b.create();
            a.show();
        }
        else{
            updateTextFromFile();
            Toast.makeText(this, "Файл существует", Toast.LENGTH_SHORT).show();
        }


    }

    public void onClickInputInfo(View view){
        EditText ed_name = findViewById(R.id.ed_name);
        EditText ed_surname = findViewById(R.id.ed_surname);
        String name = ed_name.getText().toString();
        String surname = ed_surname.getText().toString();
        if(!surname.isEmpty() && !name.isEmpty()){
            workWithFile.writeFile(name+";"+surname+"\r\n");
        }
        updateTextFromFile();
    }

    private void updateTextFromFile(){
        TextView ed_text = findViewById(R.id.ed_info);
        ed_text.setText(workWithFile.readFile());
    }

}
