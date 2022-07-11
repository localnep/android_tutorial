package com.ozan.javamaps.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//create table işlemi görür () açmazsak sınıf ismi ile (Place) tablo ismi oluşturur
@Entity //bu roomdatabase'e bu sınıfı kullanacağımızı ve entity sınıfı, model olarak kullanacağımızı söyleyen reaper.
public class Place {

    @PrimaryKey(autoGenerate = true) //otomatikmen bütün id'leri kendisi oluşturuyor
    public int id;

    @ColumnInfo(name = "name") //kaydedilen yerin ismi olacak
    public String name;

    @ColumnInfo(name = "latitude")
    public Double latitude; //enlem

    @ColumnInfo(name = "longitude")
    public Double longitude; //boylam

    public Place(String name, Double latitude, Double longitude){
        this.name = name; //ismi activity.maps içerisinden kullanıcının editText'e gireceği yerden alınacak
        this.latitude = latitude; //haritadan seçilen bölgeden gelecek
        this.longitude = longitude; //haritadan seçilen bölgeden gelecek
    }

}
