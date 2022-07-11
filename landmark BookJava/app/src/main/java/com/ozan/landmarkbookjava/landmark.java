package com.ozan.landmarkbookjava;

import java.io.Serializable;

//
public class landmark implements Serializable { //bu sınıf serializable, veriyi biryerden biryere yollarken bunu veriye çevirerek diğer tarafta tekrar eski halien çevirebiliyor. ->intent.putExtra için

    String name;
    String country;
    int image; //drawable'a herhangi birşey kaydettiğimizde R.drawable.name(int bir değer verir)

    //Constructor
    public landmark(String name, String country, int image) {
        this.name = name;
        this.country = country;
        this.image = image;
    }
}
