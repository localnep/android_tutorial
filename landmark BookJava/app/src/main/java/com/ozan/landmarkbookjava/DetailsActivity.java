package com.ozan.landmarkbookjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ozan.landmarkbookjava.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    //TextView nameText;

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_details); VİewBinding işlemi yapacağımız için siliyoruz

        //nameText = findViewById(R.id.nameText); findViewById işlemi verimsiz, büyük projelerde çok fazla arama işlemi yapar.

        binding = ActivityDetailsBinding.inflate(getLayoutInflater()); //inflate XML (layout)'umuzla kodumuzu bağlamaya çalıştığımız yer
        View view = binding.getRoot();
        setContentView(view);


        /*
        //Veri aktarımının intent ile gönderimi
        Intent intent = getIntent();
        landmark selectedLandMark = (landmark) intent.getSerializableExtra("landmark"); //casting (landmark)
        binding.nameText.setText(selectedLandMark.name);
        binding.countryText.setText(selectedLandMark.country);
        binding.imageView.setImageResource(selectedLandMark.image);
         */

        //Veri aktarımının singleton ile gönderimi
        Singleton singleton = Singleton.getInstance(); //Adapter'de oluşturduğumuz singleton objesi döndürülecek
        landmark selectedLandMark = singleton.getSentLandmark(); //LandmarkAdapter içerisinde neyi set ettiysem onu döndürecektir
        binding.nameText.setText(selectedLandMark.name);
        binding.countryText.setText(selectedLandMark.country);
        binding.imageView.setImageResource(selectedLandMark.image);
    }
}

/*
Listview kullanması kolay ama verimsiz, Recyclerview kullanması zor ama verimli olan.
Listview büyük projelerde veri çekerken listeleme işlemi verimli olmayacaktır, bu yüzden Recyclerview kullanmalıyız.
Eleman sayısı arttıkça verim düşmeyecektir.
 */