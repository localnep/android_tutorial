package com.ozan.landmarkbookjava;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ozan.landmarkbookjava.databinding.RecyclerRowBinding;

import java.util.ArrayList;

public class landmarkAdapter extends RecyclerView.Adapter<landmarkAdapter.landmarkHolder> {

    ArrayList<landmark> landmarkArrayList; //getItemCount'u 4 yerine global yaptık (ne kadar eleman varsa o kadar oluştursun)

    public landmarkAdapter(ArrayList<landmark> landmarkArrayList) {
        this.landmarkArrayList = landmarkArrayList;
    }

    @NonNull
    @Override
    public landmarkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //Binding (XML'i) bağlama işlemi
        RecyclerRowBinding recyclerRowBinding =  RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false); //inflate her zaman XML'i kodu birbirine bağlar
        return new landmarkHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull landmarkAdapter.landmarkHolder holder, int position) { //Layout'umuzun içerisinde hangi verileri göstermek istiyorsak, bağlandığında ne olacak
        holder.binding.recyclerViewText.setText(landmarkArrayList.get(position).name);

        //Tıklanma
        holder.itemView.setOnClickListener(new View.OnClickListener() { //herbir elemana tıklandığında ne olacak
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),DetailsActivity.class);
                //intent.putExtra("landmark",landmarkArrayList.get(position)); singleton ile gönderelim
                Singleton singleton = Singleton.getInstance();
                singleton.setSentLandmark(landmarkArrayList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { //XML'i kaç kare oluşturacağını söyleyecek, 4 elemanımız olduğu için (pisa,eiffel,col,londonbridge)
        return landmarkArrayList.size();
    }

    public class landmarkHolder extends RecyclerView.ViewHolder{ //bu sınıfın tek amacı görünümlerimizi tutacak <VH>: Görünüm Tutucu (view holder)
        //landmarkholder her oluşturulduğunda benden binding isteyecek ve birbirine bağlanacak
        private RecyclerRowBinding binding;

        public landmarkHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
