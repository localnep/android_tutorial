package com.ozan.landmarkbookjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ozan.landmarkbookjava.databinding.ActivityDetailsBinding;
import com.ozan.landmarkbookjava.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    ArrayList<landmark> landmarkArrayList;

    /*
    Viev Binding
    https://developer.android.com/topic/libraries/view-binding#java
    GradleScript build.gradle(module) buildFeatures aktif hale getirildi.
     */

    private ActivityMainBinding binding;

    //static landmark chosenLandmark; //static yaparak değişkenlere heryerden ulaşabiliriz. Güvenli kod değildir. Intent daha güvenli.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater()); //inflate XML (layout)'umuzla kodumuzu bağlamaya çalıştığımız yer
        View view = binding.getRoot();
        setContentView(view);

        landmarkArrayList = new ArrayList<>();

        //Data
        landmark pisa = new landmark("Pisa","Italy",R.drawable.pisa); //görmediği zaman file-> invalidate caches/restart yap.
        landmark eiffel = new landmark("Eiffel","France",R.drawable.eiffel);
        landmark colosseum = new landmark("Colosseum","Italy",R.drawable.colosseum);
        landmark londonBridge = new landmark("London Bridge","UK",R.drawable.bridge);

        landmarkArrayList.add(pisa);
        landmarkArrayList.add(eiffel);
        landmarkArrayList.add(colosseum);
        landmarkArrayList.add(londonBridge);

        //Recyclerview
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this)); //alt alta oluşturmak istiyoruz
        landmarkAdapter landmarkAdapter = new landmarkAdapter(landmarkArrayList);
        binding.recyclerView.setAdapter(landmarkAdapter);

        //bitmap: jpeg görsel obje, verimli değil. Bitmap kullanarakda setImageResource gibi görselleri göstermek için kullanabiliyoruz
        Bitmap pisaBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.pisa);
        //bitmap'i alıp intent ile yollayamazsınız, çünkü intent'lerde limit vardır, Singleton ile istediğimiz gibi gönderebiliriz.




          /*
          //Adapter --> Veri kaynağımızla XML'i ve listview'i birbirine bağlayan yapı.
            //Listview
              //mapping: neyi neye çevireyim
               ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                       landmarkArrayList.stream().map(landmark -> landmark.name).collect(Collectors.toList()) //bu şekilde string tipindeki name'leri (pisa,eifell..)aldık.
               ); //simple_list_item1 tek bir adet metin göstermek için
               binding.listView.setAdapter(arrayAdapter);

               //Tıklanma
                     binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //position: kaçıncı elemana tıklandıysa onu alacak, tıklanan şeyi toast olarak yazdıralım
                    //Toast.makeText(MainActivity.this, landmarkArrayList.get(position).name,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class); //Diğer activity'e gideceğimiz için
                    intent.putExtra("landmark",landmarkArrayList.get(position)); //landmark sınıfı serializable olduğu için verimi bu şekilde gönderebilirim
                    startActivity(intent);
                    }
              }); //1 adet elemana (item'e) tıklandığında ne olması gerektiğini kodlayacaz
           */
    }
}

/*
Recycler View Özelindeki Gösterimleri:
1-Alt alta: linear layout
2-Yan Yana: grid layout

Android Jetpack: Yazılımcılar için geliştirilmiş bütün işlemlerimizi hızlandıran daha verimli hale getiren kütüphaneler topluluğudur.
 */
