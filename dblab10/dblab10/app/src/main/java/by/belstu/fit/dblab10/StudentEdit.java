package by.belstu.fit.dblab10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentEdit extends AppCompatActivity {
    int idstudent;
    EditText namestudent;
    Spinner group;
    Button change;

    DBhelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor,uc2;
    List<String> groups = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit);
        Intent intent=getIntent();
        idstudent=intent.getIntExtra("id",-1);
        change =(Button) findViewById(R.id.changebutton);
        namestudent=(EditText) findViewById(R.id.medit);
        group=(Spinner) findViewById(R.id.grspinner);
        if (idstudent==-1) {
            change.setVisibility(View.GONE);
        }
        databaseHelper = new DBhelper(getApplicationContext());
    }
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getWritableDatabase();

        //получаем данные из бд в виде курсора
        if (idstudent!=-1) {
            userCursor = db.rawQuery("select * from STUDENTS where IDSTUDENT="+idstudent, null);
            userCursor.moveToFirst();
            namestudent.setText(userCursor.getString(2));

            userCursor.close();
        }

        uc2=db.rawQuery("select * from GROUPS", null);
        uc2.moveToFirst();
        if (uc2.getCount()>0) {
            while(!uc2.isClosed()) {
                groups.add(Integer.toString(uc2.getInt(0))+" "+uc2.getString(2));
                if (!uc2.isLast()) {uc2.moveToNext();}
                else {uc2.close();}
            }
        }
        else {
            uc2.close();
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle("Внимание").setPositiveButton("Хорошо", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    
                    Intent intent = new Intent(StudentEdit.this, GroupEdit.class);
                    startActivity(intent);

                }
            }).setNegativeButton("Не надо",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    finish();

                }
            }).setMessage("Нет групп, давайте их создадим");
            AlertDialog dialog2 = builder2.create();
            dialog2.show();

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        group.setAdapter(adapter);
    }
    public void addClick(View view) {
        String[] split =group.getSelectedItem().toString().split(" ",2);

        if (!namestudent.getText().toString().isEmpty() && group.getSelectedItem()!=null) {
            db.execSQL("insert into STUDENTS(NAME,IDGROUP) values('"
                    +namestudent.getText().toString()
                    +"',"+split[0]+")");
            finish();
        }

    }

    public void ChangeClick(View view) {
        String[] split =group.getSelectedItem().toString().split(" ",2);

        if (!namestudent.getText().toString().isEmpty() && group.getSelectedItem()!=null) {

            db.execSQL("UPDATE STUDENTS set NAME='"+namestudent.getText().toString()
                    +"',IDGROUP="+(String)(split[0])
                    +" where IDSTUDENT="+idstudent);}
    }
}
