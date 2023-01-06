package com.example.chatapplecation01.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplecation01.R;
import com.example.chatapplecation01.adapters.ProductAdapter;
import com.example.chatapplecation01.databinding.ActivityProductPageBinding;
import com.example.chatapplecation01.listeners.ProductListener;
import com.example.chatapplecation01.models.Product;
import com.example.chatapplecation01.utilities.Constants;
import com.example.chatapplecation01.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductPage extends BaseActivity {

    private ActivityProductPageBinding binding;
    private PreferenceManager preferenceManager;
    private Product product;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setListeners();
        getProducts();
    }

//    private void getProducts() {
//        loading(true);
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        database.collection(Constants.KEY_COLLECTION_PRODUCT)
//                .get()
//                .addOnCompleteListener(task -> {
//                    loading(false);
//                    String currentProductId = preferenceManager.getString(Constants.KEY_PRODUCT_ID);
//                    if(task.isSuccessful() && task.getResult() != null){
//                        List<Product> products = new ArrayList<>();
//                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
//                            if(currentProductId.equals(queryDocumentSnapshot.getId())){
//                                continue;
//                            }
//                            Product product = new Product();
//                            product.productName = queryDocumentSnapshot.getString(Constants.KEY_PRODUCT_NAME);
//                            product.productCategory = queryDocumentSnapshot.getString(Constants.KEY_PRODUCT_CATEGORY);
//                            product.nextServiceDate = queryDocumentSnapshot.getDate(Constants.KEY_NEXT_SERVICE_DATE);
//                            product.warrantyExpiryDate = queryDocumentSnapshot.getDate(Constants.KEY_WARRANTY_EXPIRY_DATE);
//                            product.productId = queryDocumentSnapshot.getId();
//                            products.add(product);
//                        }
//                        if (products.size() > 0){
//                            ProductAdapter productAdapter = new ProductAdapter(products);
//                            binding.productRecyclerView.setAdapter(productAdapter);
//                            binding.productRecyclerView.setVisibility(View.VISIBLE);
//                        }else{
//                            showErrorMessage();
//                        }
//                    }else {
//                        showErrorMessage();
//                    }
//                });
//    }

    private void getProducts() {
        loading(true);
        String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_PRODUCT)
                .whereEqualTo(Constants.KEY_OWNER_ID, currentUserId)
                .whereEqualTo(Constants.KEY_PRODUCT_TYPE, "1")
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);

                    if(task.isSuccessful() && task.getResult() != null){
                        List<Product> products = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            Product product = new Product();
                            product.productName = queryDocumentSnapshot.getString(Constants.KEY_PRODUCT_NAME);
                            product.productCategory = queryDocumentSnapshot.getString(Constants.KEY_PRODUCT_CATEGORY);
                            product.nextServiceDate = queryDocumentSnapshot.getDate(Constants.KEY_NEXT_SERVICE_DATE);
                            product.warrantyExpiryDate = queryDocumentSnapshot.getDate(Constants.KEY_WARRANTY_EXPIRY_DATE);
                            product.productImage = queryDocumentSnapshot.getString(Constants.KEY_PRODUCT_IMAGE);
                            product.productId = queryDocumentSnapshot.getId();
                            products.add(product);
                        }
                        if (products.size() > 0){
                            ProductAdapter productAdapter = new ProductAdapter(products);
                            binding.productRecyclerView.setAdapter(productAdapter);
                            binding.productRecyclerView.setVisibility(View.VISIBLE);
                        }else{
                            showErrorMessage();
                        }
                    }else {
                        showErrorMessage();
                    }
                });
    }


    private void setListeners() {
        binding.fabNewProduct.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),productCreateActivity.class)));
        binding.foodProduct.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),FoodProductPage.class)));
        binding.imageBack.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),MainPage.class)));
    }

    private Bitmap getBitmapFormEncodedString(String encodedImage){
        if (encodedImage != null){
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        }else {
            return null;
        }
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s", "No product"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showMessage(){
        binding.textErrorMessage.setText(String.format("%s", "Got product"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

}