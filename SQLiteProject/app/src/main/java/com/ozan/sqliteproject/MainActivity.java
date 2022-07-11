package com.ozan.sqliteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            SQLiteDatabase database = this.openOrCreateDatabase("Musicians",MODE_PRIVATE,null);//database oluşturulmuşsa açıyor yoksa oluşturuyor
            database.execSQL("CREATE TABLE IF NOT EXISTS musicians (id INTEGER PRIMARY KEY, name VARCHAR, age INT)");


            database.execSQL("INSERT INTO musicians (name, age) VALUES ('James',50)");
            database.execSQL("INSERT INTO musicians (name, age) VALUES ('Lars',60)");
            database.execSQL("INSERT INTO musicians (name, age) VALUES ('Kirk',55)");

            database.execSQL("UPDATE musicians SET age = 61 WHERE name = 'Lars'"); //UPDATE
            database.execSQL("DELETE FROM musicians WHERE id = 2"); //DELETE

            Cursor cursor = database.rawQuery("SELECT * FROM musicians",null); //verilerimizi okumak için cursor:imleç
           //Cursor cursor = database.rawQuery("SELECT * FROM musicians WHERE id = 2",null); //id'si 2 olan verileri getirir (filtering)
            //Cursor cursor = database.rawQuery("SELECT * FROM musicians WHERE name LIKE '%s',null); //name'in sonu s ile biten verileri getirir (filtering)
            int nameIx = cursor.getColumnIndex("name"); //name'in kaçıncı sütunda olduğunu verir
            int ageIx = cursor.getColumnIndex("age");
            int idIx = cursor.getColumnIndex("id");
            while(cursor.moveToNext()){
                System.out.println("Name: " + cursor.getString(nameIx));
                System.out.println("Age: " + cursor.getInt(ageIx));
                System.out.println("Id: " + cursor.getInt(idIx));
            }
            cursor.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}

/*
Veritabanı sıfırlama: Telefondan app'i silmek, veya emulatorü wipe data etmek
 */