package com.ozan.landmarkbookjava;

public class Singleton {

    private landmark sentLandmark;
    private static Singleton singleton;
    //private Bitmap selectedBitmap

    private Singleton() { //private yapark diğer sınıflardan bu sınıfa ait obje oluşturamam

    }


    //getter-setter
    //getter
    public landmark getSentLandmark(){
        return sentLandmark;
    }
    //setter
    public void setSentLandmark(landmark sentLandmark){
        this.sentLandmark = sentLandmark;
    }


    public static Singleton getInstance(){ //nerede çağırırsam çağırayım hep aynı objeyi döndürecek
        if(singleton == null){ //ilk defa oluşturuluyorsa
            singleton = new Singleton(); //obje burada oluşturuluyor ve singleton referansına veriliyor, constuctor'da oluşturmuyoruz
        }
        return singleton; //(değilse) daha önceden oluşturulmuş objeyi dön
    }
}


/*
Veri gönderme yolları: static, intent, singleton
Singleton class: Sadece 1 adet objeye sahip sınıftır. Constructor'ı private yaparak oluşturulur.
1 adet obje oluşturabiliyorsam bütün sınıflardan çağırırsam çağırayım hep aynı objeye erişirim ve kontrol bende olur. static
kadar tehlikeli olmaz. Singleton her zaman aynı objeyi oluşturuyor.
 */