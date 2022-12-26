package com.example.chatapplecation01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.chatapplecation01.R;
import com.example.chatapplecation01.databinding.ActivityFoodProductPageBinding;
import com.example.chatapplecation01.databinding.ActivityProductPageBinding;
import com.example.chatapplecation01.utilities.PreferenceManager;

public class FoodProductPage extends BaseActivity {

    private ActivityFoodProductPageBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodProductPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
    }

    private void setListeners() {
//        binding.fabNewProduct.setOnClickListener(v ->
//                startActivity(new Intent(getApplicationContext(),FoodProductPage.class)));
        binding.electricProduct.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),ProductPage.class)));
        binding.imageBack.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),MainPage.class)));
    }
}