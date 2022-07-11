package com.ozan.javamaps.view;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.ozan.javamaps.R;
import com.ozan.javamaps.databinding.ActivityMapsBinding;
import com.ozan.javamaps.model.Place;
import com.ozan.javamaps.roomdb.PlaceDao;
import com.ozan.javamaps.roomdb.PlaceDatabase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener { //haritaya uzun tıklanınca marker eklemek için GoogleMap.OnMapClickListener

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    ActivityResultLauncher<String> permissionLauncher;
    LocationManager locationManager;
    LocationListener locationListener;
    SharedPreferences sharedPreferences;
    boolean info;
    PlaceDatabase db;
    PlaceDao placeDao;
    Double selectedLatitude;
    Double selecedLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        registerLauncher();

        sharedPreferences = this.getSharedPreferences("com.ozan.javamaps",MODE_PRIVATE); //küçük database
        info = false;

        db = Room.databaseBuilder(getApplicationContext(),PlaceDatabase.class,"Places").build(); //db ismi Places
        placeDao = db.placeDao(); //placeDao abstract method

        selecedLongitude = 0.0;
        selectedLatitude = 0.0;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) { //harita hazır olunca bu metod çağrılıyor ne yapılacağını söylüyor
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this); //uzun tıklanınca marker eklemek için

        binding.saveButton.setEnabled(false); //kullanıcı bir yer seçmezse save butonu tıklanamayacak, onMapLongClick'de aktif hale getireceğiz

        /*
        LatLng eiffel = new LatLng(48.8588513354701, 2.294312648263595);
        mMap.addMarker(new MarkerOptions().position(eiffel).title("Eiffel Tower"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eiffel,15)); //zoom seviyesi 15
         */

        //konum alma işlemi

        //konum yöneticisi: android konum servislerine erişim sağlayabilmek için
         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); //casting
        //LocationListener konum yöneticimizden (LocationManager)'dan konumun değiştiğine dair uyarıları alabilmek için kullandığımız arayüz
         locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) { //konum değiştiğinde ne yapılacak?, sadece 1 defa çağrılmasını istiyoruz
                //System.out.println("Location: " + location.toString());

                //harita üzerinde rastgele dolaşabilmek için (kamerayı sürekli konumumuza sabitlememek için), sadece 1 kez çalışmasını istiyoruz

                //1 defa çalıştırıldı mı? çalıştırılmadı mı? bilgisini kayıt etmek istiyoruz
                info = sharedPreferences.getBoolean("info",false); //sharedPreferences'da "info" diye kayıtlı key var dedim ama yoksa DefaultValue false olsun
                if(info == false){ //ilk defa çalıştırılıyorsa false dönecektir ve kamerayı değişen(güncellenen) konuma doğru çevir, !info da diyebilirsin
                    LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));
                    sharedPreferences.edit().putBoolean("info",true).apply(); //1 defa çalıştıktan sonra burası true dönecektir ve onLocationChanged fonksiyonu
                    //ne kadar çağırılırsa çağrılsın bu blok bir daha çalışmayacak
                }
            }
        };

        //İzinler -> access_fine_location
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){ //izin verilmemiş mi kontrolü?
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){ //kullanıcıya mantık göstermemiz gerekiyorsa
                Snackbar.make(binding.getRoot(),"Permission needed for maps",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() { //give permission izin ver butonu
                    @Override
                    public void onClick(View view) { //butona tıklanınca yapılacak eylem
                        //request permission: izin isteme
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                }).show();
            } else { //mantığı göstermediği taktirde bile izin istenecek
                //request permission
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        } else { //izin daha önce verildiyse
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener); //harita gps alma, güncelleme işlemi

            /*
            en son bilinen konumu alma: özellikle ilk başlatıldığında izinlerimizi aldıktan sonra son bilinen konum varsa en baştan kamerayı oraya odaklayabiliriz.
            Böylece uygulama ilk açılışında konum değişikliğini beklemek zorunda kalmıyoruz.
            */

            //last location
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //son bilinen location alma işlemi
            if(lastLocation != null){ //gerçekten bir konum geliyorsa
                LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude()); //son konumumuzun enlem ve boylamını aldık
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));
            }
            mMap.setMyLocationEnabled(true); //benim konumumum etkin olduğundan emin ol, bulunduğumuz yerde mavi gösterge olarak bizi gösteriyor
        }

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener); //harita gps alma, güncelleme işlemi

        /*
        // Add a marker in Sydney and move the camera --> Avustralya Sydney de olmak üzere işaretçi (marker) eklenmiş
        LatLng sydney = new LatLng(-34, 151); //enlem ve boylam yazılmış latitude:enlem , longitude:boylam
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney")); //işaretçi ekleniyor
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); //kamerayı sydney'e çevirmiş,odaklanmış.
         */

    }

    private void registerLauncher(){
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) { //result: izin verilir yada verilmedi döner
                if(result){ //izin verildi
                    //permission granted
                    if(ContextCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){ //tekrar emin olmak kaydıyla eşit mi sorgusunu soruyoruz (== sorgusu ile sorduk)
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener); //harita gps alma, güncelleme işlemi

                        //last location
                        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //son bilinen location alma işlemi
                        if(lastLocation != null){ //gerçekten bir konum geliyorsa
                            LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude()); //son konumumuzun enlem ve boylamını aldık
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));
                        }
                    }
                } else { //izin verilmedi
                    //permission denied
                    Toast.makeText(MapsActivity.this,"Permission needed!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onMapLongClick(LatLng latLng) { //uzun tıklanınca marker eklemek için
        mMap.clear(); //daha önce eklenen Marker'ları kaldırmak için, tek 1 tane marker koyulabilsin
        mMap.addMarker(new MarkerOptions().position(latLng));

        selectedLatitude = latLng.latitude;
        selecedLongitude = latLng.longitude;

        binding.saveButton.setEnabled(true); //kullanıcı bir yer seçmeden tıklanamaz, onMapReady'de false yaptık bu bloğa girmezse tıklanamaz
    }

    public void save(View view){ //view: görünüm tarafından çağrılacak
        //placeDao.insert();
        Place place = new Place(binding.placeNameText.getText().toString(),selectedLatitude,selecedLongitude); //enlem,boylam (onMapLongClick'de veriliyordu) ve isim istiyordu
        //placeDao.insert(place); direk bu şekilde eklenemez, uygulama çöker, yeni bir teknoloji, RxJava sayesinde ekleyeceğiz

    }

    public void delete(View view){

    }
}


/*
API Nedir? - Application Programming Interface - Uygulama Programlama Arayüzü
Google maps'i kullanabilmek için google maps'in sunucularına bunu kullandığımızı söylememiz kendimizi oraya tanımlamamız
(gmail) ve oradan ücretsiz olarak API anahtarı almamız gerekiyor. google_maps_api_xml' de String ifade de YOUR_KEY_HERE
bölümüne key'imizi gireceğiz. Key'i alabilmek için url linki verilmiş.

latitude:enlem , longitude:boylam
 */

