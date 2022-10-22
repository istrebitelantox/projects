package com.example.sixproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.GridView;


public class MainActivity extends Activity implements
        OnClickListener {

    final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
    EditText etName, grade, etID;

    String[] data = {"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k"};

    GridView gvMain;
    ArrayAdapter<String> adapter;


    DBHelper dbHelper;

    /** Called when the activity is first created. */   @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {     super.onCreate(savedInstanceState);     setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);     btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);     btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);     btnClear.setOnClickListener(this);

        btnUpd = (Button) findViewById(R.id.btnUpd);     btnUpd.setOnClickListener(this);

        btnDel = (Button) findViewById(R.id.btnDel);     btnDel.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);     grade = (EditText) findViewById(R.id.grade);
        etID = (EditText) findViewById(R.id.etID);



        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }




    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String email = grade.getText().toString();
        String id = etID.getText().toString();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()) {     case R.id.btnAdd:
            Log.d(LOG_TAG, "--- Insert in mytable: ---");
            // подготовим данные для вставки в виде пар: наименование столбца -       // значение
            cv.put("name", name);       cv.put("email", email);
            // вставляем запись и получаем ее ID
            long rowID = db.insert("mytable", null, cv);       Log.d(LOG_TAG, "row inserted, ID = " + rowID);       break;     case R.id.btnRead:
            Log.d(LOG_TAG, "--- Rows in mytable: ---");       // делаем запрос всех данных из таблицы mytable, получаем Cursor
            Cursor c = db.query("mytable", null, null, null, null, null, null);

            // ставим позицию курсора на первую строку выборки       // если в выборке нет строк, вернется false
            if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int emailColIndex = c.getColumnIndex("email");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) + ", name = "
                                + c.getString(nameColIndex) + ", grade = "
                                + c.getString(emailColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false -
                // выходим из цикла
            } while (c.moveToNext());       } else
        Log.d(LOG_TAG, "0 rows");
        c.close();       break;     case R.id.btnClear:
        Log.d(LOG_TAG, "--- Clear mytable: ---");
        // удаляем все записи
        int clearCount = db.delete("mytable", null, null);       Log.d(LOG_TAG, "deleted rows count = " + clearCount);       break;     case R.id.btnUpd:
        if (id.equalsIgnoreCase("")) {         break;
        }
        Log.d(LOG_TAG, "--- Update mytable: ---");       // подготовим значения для обновления
             cv.put("name", name);       cv.put("email", email);       // обновляем по id
        int updCount = db.update("mytable", cv, "id = ?",           new String[] { id });
        Log.d(LOG_TAG, "updated rows count = " + updCount);       break;     case R.id.btnDel:
        if (id.equalsIgnoreCase("")) {         break;
        }
        Log.d(LOG_TAG, "--- Delete from mytable: ---");
        // удаляем по id
        int delCount = db.delete("mytable", "id = " + id, null);
        Log.d(LOG_TAG, "deleted rows count = " + delCount);       break;
    }
    // закрываем подключение к БД
        dbHelper.close();
}

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {       // конструктор суперкласса
         super(context, "myDB", null, 1);
    }
    public void onCreate(SQLiteDatabase db) {       Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "email text" + ");");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
public void onClick1(View view)
{
    Intent intent = new Intent(this, ActivityTwo.class);
    intent.putExtra("username", etName.toString());
    // в ключ gift пихаем текст из второго текстового поля
    intent.putExtra("gift", grade.toString());
    startActivity(intent);
}

}

