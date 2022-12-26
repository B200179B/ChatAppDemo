package com.example.chatapplecation01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.chatapplecation01.R;

public class productCreateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_create);

        selectCategory();
    }

    private void selectCategory(){ // drop down list
        String[] item ={"vehicle", "Air Conditioner", "Refrigerator", "Auto Gate", "Television", "Washing Machine", "Kitchen Appliances", "Others Electric Appliances"};

        AutoCompleteTextView autoCompleteTextView;

        ArrayAdapter<String> adapterCategory;

        autoCompleteTextView = findViewById(R.id.categorySelect);

        adapterCategory = new ArrayAdapter<String>(this, R.layout.list_item, item);

        autoCompleteTextView.setAdapter(adapterCategory);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(productCreateActivity.this, "category "+item, Toast.LENGTH_SHORT).show();
            }
        });
    }
}