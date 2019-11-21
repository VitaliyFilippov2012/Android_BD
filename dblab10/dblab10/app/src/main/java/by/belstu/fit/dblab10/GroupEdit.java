package by.belstu.fit.dblab10;

import androidx.appcompat.app.AppCompatActivity;

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

public class GroupEdit extends AppCompatActivity {
    EditText faculty;
    EditText namegroup;
    EditText courcegroup;
    Spinner headgroup;
    TextView headedit;
    Button change;
    int idgroup;

    DBhelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor,uc2;
    List<String> heads = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_edit);

        Intent intent=getIntent();
        idgroup=intent.getIntExtra("id",-1);
        faculty=(EditText) findViewById(R.id.fedit);
        namegroup=(EditText) findViewById(R.id.medit);
        courcegroup=(EditText) findViewById(R.id.сedit);
        headgroup=(Spinner) findViewById(R.id.stspinner);
        headedit =(TextView) findViewById(R.id.stText);
        change =(Button) findViewById(R.id.changebutton);
        if (idgroup==-1) {
            change.setVisibility(View.GONE);
            headedit.setVisibility(View.GONE);
            headgroup.setVisibility(View.GONE);
        }
        databaseHelper = new DBhelper(getApplicationContext());
    }
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getWritableDatabase();

        //получаем данные из бд в виде курсора
        if (idgroup!=-1) {
            userCursor = db.rawQuery("select * from GROUPS where IDGROUP="+idgroup, null);
            userCursor.moveToFirst();
            faculty.setText(userCursor.getString(1));
            namegroup.setText(userCursor.getString(2));
            courcegroup.setText(userCursor.getString(4));
            uc2=db.rawQuery("select * from STUDENTS where IDGROUP="+idgroup, null);
            uc2.moveToFirst();
            if (uc2.getCount()>0) {
                while(!uc2.isClosed()) {
                    heads.add(uc2.getString(2));
                    if (!uc2.isLast()) {uc2.moveToNext();}
                    else {uc2.close();}
                }
            }
            else {
                uc2.close();
            }
            userCursor.close();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, heads);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            headgroup.setAdapter(adapter);
        }
    public void addClick(View view) {
        if (!faculty.getText().toString().isEmpty() && !namegroup.getText().toString().isEmpty()) {
            db.execSQL("insert into GROUPS(FACULTY,NAME,HEAD,COURSE) values('"
                    +faculty.getText().toString()
                    +"','"+namegroup.getText().toString()
                    +"',null,"+Integer.parseInt(courcegroup.getText().toString())+")");
            finish();
        }

    }

    public void ChangeClick(View view) {
        if (!faculty.getText().toString().isEmpty()
                && !namegroup.getText().toString().isEmpty()
                && !courcegroup.getText().toString().isEmpty()
                && headgroup.getSelectedItem()!=null) {

            db.execSQL("UPDATE GROUPS set FACULTY='"+faculty.getText().toString()+
                    "',NAME='"+namegroup.getText().toString()
                +"',HEAD='"+(String)(headgroup.getSelectedItem())+"',COURSE="+Integer.parseInt(courcegroup.getText().toString())
                    +" where IDGROUP="+idgroup);}
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
    }
}
