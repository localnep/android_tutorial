package com.ozan.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView scoreText;
    TextView timeText;
    int score;

    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray;

    Handler handler;//Runnable'ı kullanabilmemiz için gereken library
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        timeText = findViewById(R.id.timeText);
        scoreText = findViewById(R.id.scoreText);

        score = 0;

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
        imageArray = new ImageView[] {imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};

        new CountDownTimer(10000,1000){ //10 saniyeyi saniyede 1 sayarak azalt
            @Override
            public void onTick(long millisUntilFinished) { //her 1 saniyede ne yapayım?
                timeText.setText("Time: " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {//countDownTimer bitince ne olacak? Oyunu bitirme işlemleri
                timeText.setText("Time Off");
                handler.removeCallbacks(runnable); //Runnable stop

                //kenny'i oyun sonunda tekrar görünmez hale getirme işlemi
                for(ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE); //görünmez yapacaktır
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart?");
                alert.setMessage("Are you sure to restart game?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { //YES'e tıklanırsa ne olacak, Restart atma işlemi
                        //onCreate'i baştan oluşturmak için; Aynı activity'e baştan başlatma işlemi;
                        Intent intent = getIntent();
                        finish();//güncel activity'mizi sonlandır
                        startActivity(intent);//kendi (aynı) activity'mizi baştan başlatırız.
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Game Over!",Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();
            }

        }.start();

        hideImages();
    }

    public void increaseScore(View view){ //ImageView'larımızın onClick metodu. Bu metodun bir view(görünüm) tarafından (buton,text,image..) çağrılacağını bildirmek için View parametresini ekliyoruz
        score++; //her tıklandığında score +1 arttırılacak
        scoreText.setText("Score: " + score);
    }

    public void hideImages(){

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                for(ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE); //görünmez yapacaktır
                }

                Random random = new Random();
                int i = random.nextInt(9); //i 0-8 arasında rastgele bir değer
                imageArray[i].setVisibility(View.VISIBLE); //imageArray içerisindeki random image'i görünür hale getirecek

                handler.postDelayed(this , 500); //Runnable'ı rötarlı çalıştırma, yarım saniyede çalıştırma
            }
        };

        handler.post(runnable);
    }
}

/*
Grid Layout ile çalışırken XML ile çalışmak daha doğru ve kolay (row,column,OnClick ayarlamasını XML'de yaptık)
 */