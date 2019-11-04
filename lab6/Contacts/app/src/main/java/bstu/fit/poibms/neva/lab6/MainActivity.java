package bstu.fit.poibms.neva.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSearchButton(View view){
        final Intent intent = new Intent(this, SearchInfo.class);
        startActivity(intent);
    }

    public void onClickCreateContactButton(View view){
        final Intent intent = new Intent(this, CreateContact.class);
        startActivity(intent);
    }
}
