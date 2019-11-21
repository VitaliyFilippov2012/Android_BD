package by.belstu.fit.dblab10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
    public DBhelper(Context context){
        super(context,"STUDENTSDB",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL(
                "create table if not exists GROUPS("
                        + "IDGROUP integer primary key  autoincrement,"
                        + "FACULTY text,"
                        + "NAME text,"
                        + "HEAD text,"
                        + "COURSE int);"
        );
//        db.execSQL("insert into GROUPS(FACULTY,NAME,HEAD,COURSE) VALUES('FIT','POIBMS',null,3)");

        db.execSQL("create table if not exists STUDENTS("
                        + "IDSTUDENT integer primary key  autoincrement,"
                        + "IDGROUP integer,"
                        + "NAME text, FOREIGN KEY (IDGROUP) REFERENCES GROUPS(IDGROUP)  ON UPDATE CASCADE ON DELETE CASCADE);"
        );
  //      db.execSQL("insert into STUDENTS(IDGROUP,NAME) VALUES(1,'name surname')");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("drop table if exists STUDENTS");
        db.execSQL("drop table if exists GROUPS");
        db.execSQL(
                "create table GROUPS("
                        + "IDGROUP integer primary key  autoincrement,"
                        + "FACULTY text,"
                        + "NAME text,"
                        + "HEAD text,"
                        + "COURSE int);"
        );
        db.execSQL("PRAGMA foreign_keys=ON;");
        db.execSQL(
                "create table STUDENTS("
                        + "IDSTUDENT integer primary key  autoincrement,"
                        + "IDGROUP integer,"
                        + "NAME text,FOREIGN KEY (IDGROUP) REFERENCES GROUPS(IDGROUP)  ON UPDATE CASCADE ON DELETE CASCADE);"
        );
    }

}
