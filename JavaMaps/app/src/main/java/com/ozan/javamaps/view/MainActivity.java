package com.ozan.javamaps.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ozan.javamaps.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override //
    public boolean onCreateOptionsMenu(Menu menu) { //travelmenu baglama islemi
        MenuInflater menuInflater = getMenuInflater(); //XML ile kodu birbirine bağlamak: Inflater
        menuInflater.inflate(R.menu.travel_menu,menu); //baglama islemi
        return super.onCreateOptionsMenu(menu);
    }

    @Override //travel_menu, menüden birşey seçilirse,tıklanırsa ne olacak?
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_place){ //travel menu'de olusturdugumuz add_place mi tıklandı kontrolu
            Intent intent = new Intent(MainActivity.this,MapsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

/*
ActivityMaps Constraitleri aşağıdan yukarıya doğru verdik. (responsive tasarım gibi), harita height 0 dp match_constait ama aşağıdaki butonlara
göre bütün ekranı kaplayacak.
 */

/*
Room Database
sql kodunu minimize edip database kullanma;
room kodlarını kullanabilmek için google-->room database kodlarını kullanabilmek için android'deki kodları
build.gradle(app) implementation'lar ekledik
rxJava:Room database ile iletişime geçerken kullanacağımız teknoloji
DataAccess Objects: Veriye erişme objesi: Verileri yazarken,alırken query yaparken, silerken bu arayüzü kullanacağız.
Entities: Model: Kolonlarda ne olacak?, değerleri ne olacak? create table derken yazdığımız özellikler burada olacak
DAO:
 */