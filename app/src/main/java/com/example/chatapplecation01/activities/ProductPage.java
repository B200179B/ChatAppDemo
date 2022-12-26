package com.example.chatapplecation01.activities;

import android.content.Intent;
import android.os.Bundle;
import com.example.chatapplecation01.databinding.ActivityProductPageBinding;

public class ProductPage extends BaseActivity {

    private ActivityProductPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.fabNewProduct.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),productCreateActivity.class)));
        binding.foodProduct.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),FoodProductPage.class)));
        binding.imageBack.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),MainPage.class)));
    }
}