package by.belstu.fit.dblab10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    DBhelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DBhelper(this);
        db = helper.getWritableDatabase();

    }

    public void ClickGroupsActivity(View view) {
        Intent intent = new Intent(MainActivity.this,GroupsActivity.class);
        startActivity(intent);
    }

    public void ClickStudentsActivity(View view) {

        Intent intent = new Intent(MainActivity.this,StudentsActivity.class);
        startActivity(intent);
    }
}
