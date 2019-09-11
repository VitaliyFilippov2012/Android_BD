package filippovvitaliyleonidovich.bstu.fit.lab3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.File;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TextView absolute,name,path,readwrite,external;
    InfoFile infoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadInfo();
    }
    private  void loadInfo()
    {
        absolute=(TextView) findViewById(R.id.absolute);
        name=(TextView) findViewById(R.id.name);
        path=(TextView) findViewById(R.id.path);
        readwrite=(TextView) findViewById(R.id.rw);
        external=(TextView) findViewById(R.id.ext);
        infoFile = new InfoFile();
    }

    public void setInfoAboutFileToTextView(){
        absolute.setText(infoFile.getAbsolute());
        name.setText(infoFile.getName());
        path.setText(infoFile.getPath());
        readwrite.setText(infoFile.getReadwrite());
        external.setText(infoFile.getExternal());

    }

    public void onClickGetFilesDir(View View){
        infoFile.getInfoAboutFile(getFilesDir());
        setInfoAboutFileToTextView();
    }

    public void onClickGetCashDir(View View){
        infoFile.getInfoAboutFile(getCacheDir());
        setInfoAboutFileToTextView();
    }
    public void onClickGetExternalFilesDir(View View){
        infoFile.getInfoAboutFile(getExternalFilesDir(Environment.DIRECTORY_DCIM));
        setInfoAboutFileToTextView();

    }
    public void onClickGetExtCashDir(View View){
        infoFile.getInfoAboutFile(getExternalCacheDir());
        setInfoAboutFileToTextView();
    }
    public void onClickGetExtStorDir(View View){
        infoFile.getInfoAboutFile(Environment.getExternalStorageDirectory());
        setInfoAboutFileToTextView();
    }
    public void onClickGetExtStorPubDir(View View){
        infoFile.getInfoAboutFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        setInfoAboutFileToTextView();
    }

}




