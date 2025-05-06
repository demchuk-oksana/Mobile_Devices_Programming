package com.example.lab_work_8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private Context context;
    private ArrayList<String> petId, petName, petType, petBreed, petPassport;
    private ArrayList<Integer> petAge;

    public PetAdapter(Context context, ArrayList<String> petId, ArrayList<String> petName,
                      ArrayList<String> petType, ArrayList<String> petBreed,
                      ArrayList<Integer> petAge, ArrayList<String> petPassport) {
        this.context = context;
        this.petId = petId;
        this.petName = petName;
        this.petType = petType;
        this.petBreed = petBreed;
        this.petAge = petAge;
        this.petPassport = petPassport;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pet_item, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        holder.tvPetId.setText(String.valueOf(petId.get(position)));
        holder.tvPetName.setText(petName.get(position));
        holder.tvPetType.setText("Тип: " + petType.get(position));
        holder.tvPetBreed.setText("Порода: " + petBreed.get(position));
        holder.tvPetAge.setText("Вік: " + petAge.get(position) + " років");
        holder.tvPetPassport.setText("Паспорт: " + petPassport.get(position));
    }

    @Override
    public int getItemCount() {
        return petId.size();
    }

    public class PetViewHolder extends RecyclerView.ViewHolder {
        TextView tvPetId, tvPetName, tvPetType, tvPetBreed, tvPetAge, tvPetPassport;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPetId = itemView.findViewById(R.id.tv_pet_id);
            tvPetName = itemView.findViewById(R.id.tv_pet_name);
            tvPetType = itemView.findViewById(R.id.tv_pet_type);
            tvPetBreed = itemView.findViewById(R.id.tv_pet_breed);
            tvPetAge = itemView.findViewById(R.id.tv_pet_age);
            tvPetPassport = itemView.findViewById(R.id.tv_pet_passport);
        }
    }

    // Method to clear all data
    public void clearData() {
        petId.clear();
        petName.clear();
        petType.clear();
        petBreed.clear();
        petAge.clear();
        petPassport.clear();
        notifyDataSetChanged();
    }
}