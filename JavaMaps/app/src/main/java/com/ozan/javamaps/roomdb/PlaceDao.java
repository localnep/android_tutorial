package com.ozan.javamaps.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ozan.javamaps.model.Place;

import java.util.List;

@Dao
public interface PlaceDao {

    @Query("SELECT * FROM Place") //geri Liste döndürecek
    List<Place> getAll(); //bütün verileri al

    @Insert
    void insert(Place place); //artık insert işlemi yapabiliriz

    @Delete
    void delete(Place place); //void geri birşey döndürmeyecek
}
