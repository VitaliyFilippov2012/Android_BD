package bstu.fit.poibms.filippov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button insert, select, select_raw,delete,update;
    EditText f,t,id;
    String result="";
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insert = (Button) findViewById(R.id.button);
        insert.setOnClickListener((View.OnClickListener) this);

        select = (Button) findViewById(R.id.button2);
        select.setOnClickListener((View.OnClickListener) this);

        select_raw = (Button) findViewById(R.id.button3);
        select_raw.setOnClickListener((View.OnClickListener) this);

        update = (Button) findViewById(R.id.button4);
        update.setOnClickListener((View.OnClickListener) this);

        delete = (Button) findViewById(R.id.button5);
        delete.setOnClickListener((View.OnClickListener) this);
        id=(EditText) findViewById(R.id.edit_id);
        f = (EditText) findViewById(R.id.edit_f);
        t = (EditText) findViewById(R.id.edit_t);

        dbHelper = new DBHelper(this);
    }
    public void check(SQLiteDatabase db, Integer id_value){
        String queryString1="SELECT distinct _id,f,t from "+Config.TABLE+ " where _id="+id_value;
        Cursor cursorCheck=db.rawQuery(queryString1,null);
        cursorCheck.moveToFirst();
        if(!cursorCheck.isAfterLast()){
            result=cursorCheck.getString(0);
        }
        cursorCheck.close();
    }
    public void output(){
        Toast toast = Toast.makeText(getApplicationContext(),"This id exists in db", Toast.LENGTH_SHORT);
        toast.show();
        id.setText(null);
        f.setText(null);
        t.setText(null);
    }

    @Override
    public void onClick(View v) {
        Integer id_value=Integer.parseInt(id.getText().toString());
        String f_value =f.getText().toString();
        String t_value = t.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        switch (v.getId()) {

            case R.id.button:

                check(database,id_value);

                if(result.isEmpty()) {

                    contentValues.put(Config.KEY_ID, id_value);
                    contentValues.put(Config.KEY_F, f_value);
                    contentValues.put(Config.KEY_T, t_value);
                    database.insert(Config.TABLE, null, contentValues);
                    f.setText(null);
                    t.setText(null);
                }
                else{
                    output();
                }

                break;
            case R.id.button2:
                Log.d("Lab9", "--- Select values from table: ---");
                Cursor c;
                String selection = "_id = ?";
                String[] selectionArgs = new String[] { id.getText().toString() };
                String columns[] = new String[] { "_id", "f", "t" };
                c = database.query(Config.TABLE, columns, selection, selectionArgs, null, null,
                        null);
                if (c.moveToFirst()) {
                    int fIndex = c.getColumnIndex(Config.KEY_F);
                    int tIndex = c.getColumnIndex(Config.KEY_T);
                    do {
                        f.setText(c.getString(fIndex));
                        t.setText(c.getString(tIndex));
                    }
                    while(c.moveToNext());
                }
                else
                    Log.d("Lab9","0 rows");
                c.close();
                break;
            case R.id.button3:
                Log.d("Lab9", "--- Select_raw table: ---");
                String queryString="SELECT distinct f,t from "+Config.TABLE+" where _id="+id_value;
                Cursor cursor=database.rawQuery(queryString,null);
                cursor.moveToFirst();
                if(!cursor.isAfterLast()){
                    f.setText(cursor.getString(0));
                    t.setText(cursor.getString(1));
                }
                cursor.close();
                break;

            case R.id.button4:
                result="";
                check(database,id_value);
                if(result.isEmpty()) {
                    Log.d("Lab9", "--- Update table: ---");
                    contentValues.put(Config.KEY_F,f_value);
                    contentValues.put(Config.KEY_T, t_value);
                    int updCount = database.update(Config.TABLE, contentValues, "_id = ?",
                            new String[] { id_value.toString() });
                    Log.d("Lab9", "updated rows count = " + updCount);
                    f.setText(null);
                    t.setText(null);
                }
                else{
                    output();
                }

                break;
            case R.id.button5:
                result="";
                check(database,id_value);

                if(result.isEmpty()) {
                    Log.d("Lab9", "--- Delete value from  table: ---");
                    int delCount = database.delete(Config.TABLE, "_id = " + id_value, null);
                    Log.d("Lab9", "deleted rows count = " + delCount);
                    f.setText(null);
                    t.setText(null);
                }
                else{
                    output();
                }
                break;
        }
        dbHelper.close();
    }
}
