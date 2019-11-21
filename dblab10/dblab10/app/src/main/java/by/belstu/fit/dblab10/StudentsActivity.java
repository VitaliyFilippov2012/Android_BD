package by.belstu.fit.dblab10;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentsActivity extends AppCompatActivity {
    ListView studList;
    TextView header;
    DBhelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    StudentAdapter studentAdapter;
    Intent intent;
    private List<Student> students = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        intent= new Intent(StudentsActivity.this,StudentEdit.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        header = (TextView)findViewById(R.id.header);
        studList = (ListView)findViewById(R.id.list_students);
        databaseHelper = new DBhelper(getApplicationContext());
        registerForContextMenu(studList);
    }
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
        //получаем данные из бд в виде курсора
        userCursor =  db.rawQuery("select * from STUDENTS", null);
        header.setText("Найдено элементов: " + String.valueOf(userCursor.getCount()));
        students.clear();
        if (userCursor.moveToFirst()) {
            while(!userCursor.isClosed()) {
                students.add(new Student(userCursor.getInt(0),userCursor.getInt(1),
                        userCursor.getString(2)));
                if (!userCursor.isLast()) {userCursor.moveToNext();}
                else {userCursor.close();}
            }
        }
        studentAdapter = new StudentAdapter(this,R.layout.line3_list_item,students);

        studList.setAdapter(studentAdapter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        if(!userCursor.isClosed()) {userCursor.close();};
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MenuItem item1=item;
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("Внимание").setPositiveButton("Хорошо", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(StudentsActivity.this, StudentEdit.class);
                        intent.putExtra("id", (int)students.get(info.position).IDSTUDENT);
                        startActivity(intent);

                    }
                }).setNegativeButton("Не надо",null).setMessage("Сейчас вы сможете отредактировать эту запись");
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                return true;

            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Внимание").setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete((int)students.get(info.position).IDSTUDENT);
                    }
                }).setNegativeButton("Нет",null).setMessage("Удалить эту запись?");
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
    public void delete(int id) {
        int iddel;
        userCursor =  db.rawQuery("select * from STUDENTS", null);
        userCursor.moveToFirst();
        userCursor.move(id);
        iddel=userCursor.getInt(0);
        db.execSQL("DELETE FROM STUDENTS WHERE IDSTUDENT="+iddel);
        onResume();
    }
}
