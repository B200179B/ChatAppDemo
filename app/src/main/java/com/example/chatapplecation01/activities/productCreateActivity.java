package com.example.chatapplecation01.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapplecation01.R;
import com.example.chatapplecation01.databinding.ActivityProductCreateBinding;
import com.example.chatapplecation01.utilities.Constants;
import com.example.chatapplecation01.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class productCreateActivity extends BaseActivity {

    private ActivityProductCreateBinding binding;
    private PreferenceManager preferenceManager;
    private EditText etNextServiceDate, etLastServiceDate, etWarrantyDate,remindDateSelect;
    private String encodedImage;
    private String selectedCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());

        selectCategory();
//        selectRemindBefore();
        selectNextServiceDate();
        selectLastServiceDate();
        selectWarrantyDate();
        setListeners();
//        addProduct();

    }

    private void setListeners(){
        binding.buttonCancel.setOnClickListener(v -> onBackPressed());
        binding.layoutImage.setOnClickListener(v -> {  //select image
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
        binding.buttonAddProduct.setOnClickListener(v -> addProduct());
    }


    private void addProduct(){
        loading(true);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> product = new HashMap<>();
        product.put(Constants.KEY_OWNER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        product.put(Constants.KEY_PRODUCT_NAME, binding.inputProductName.getText().toString());
        product.put(Constants.KEY_PRODUCT_NOTES, binding.inputNotes.getText().toString());
        product.put(Constants.KEY_PRODUCT_CATEGORY, selectCategory());
        product.put(Constants.KEY_PRODUCT_IMAGE, encodedImage);
        product.put(Constants.KEY_PRODUCT_TYPE, "1");


        String nextServiceDate=etNextServiceDate.getText().toString();
        Calendar calendar = Calendar.getInstance();
        String[] NextServiceDate = nextServiceDate.split("/");
        int year = Integer.parseInt(NextServiceDate[2]);
        int month = Integer.parseInt(NextServiceDate[1]) - 1; // months are 0-based
        int day = Integer.parseInt(NextServiceDate[0]);
        calendar.set(year, month, day, 0 , 0 , 0);
        Date date = calendar.getTime();
        product.put(Constants.KEY_NEXT_SERVICE_DATE, date);

        String lastServiceDate=etLastServiceDate.getText().toString();
        String[] LastServiceDate = lastServiceDate.split("/");
        year = Integer.parseInt(LastServiceDate[2]);
        month = Integer.parseInt(LastServiceDate[1]) - 1; // months are 0-based
        day = Integer.parseInt(LastServiceDate[0]);
        calendar.set(year, month, day, 0 , 0 , 0);
        date = calendar.getTime();
        product.put(Constants.KEY_LAST_SERVICE_DATE, date);

        String warrantyDate=etWarrantyDate.getText().toString();
        String[] WarrantyDate = warrantyDate.split("/");
        year = Integer.parseInt(WarrantyDate[2]);
        month = Integer.parseInt(WarrantyDate[1]) - 1; // months are 0-based
        day = Integer.parseInt(WarrantyDate[0]);
        calendar.set(year, month, day, 0 , 0 , 0);
        date = calendar.getTime();
        product.put(Constants.KEY_WARRANTY_EXPIRY_DATE, date);

//        String remindDate=remindDateSelect.getText().toString();
//        String[] RemindDate = remindDate.split("/");
//        year = Integer.parseInt(RemindDate[2]);
//        month = Integer.parseInt(RemindDate[1]) - 1; // months are 0-based
//        day = Integer.parseInt(RemindDate[0]);
//        calendar.set(year, month, day, 0 , 0 , 0);
//        date = calendar.getTime();
//        product.put(Constants.KEY_REMIND_DATE, date);

        database.collection(Constants.KEY_COLLECTION_PRODUCT)
                .add(product)
                .addOnSuccessListener(documentReference -> {
                    Intent intent = new Intent(getApplicationContext(),ProductPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                })
                .addOnFailureListener(e -> {
                    showToast("No");
                });
    }

    private String encodeImage (Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth/ bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult( // select image
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    Uri imageUri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        binding.imageProfile.setImageBitmap(Bitmap.createScaledBitmap(bitmap, binding.imageProfile.getWidth(), binding.imageProfile.getHeight(), false));
                        binding.textAddImage.setVisibility(View.GONE);
                        encodedImage = encodeImage(bitmap);
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );



    private Object selectCategory(){ // drop down list
        String[] item ={"vehicle", "Air Conditioner", "Refrigerator", "Auto Gate", "Television", "Washing Machine", "Kitchen Appliances", "Others Electric Appliances"};

        AutoCompleteTextView autoCompleteTextView;

        ArrayAdapter<String> adapterCategory;

        autoCompleteTextView = findViewById(R.id.categorySelect);

        adapterCategory = new ArrayAdapter<String>(this, R.layout.list_item, item);

        autoCompleteTextView.setAdapter(adapterCategory);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                selectedCategory = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(productCreateActivity.this, "category "+selectedCategory, Toast.LENGTH_SHORT).show();
            }
        });
        return selectedCategory;
    }



//    private void selectRemindBefore(){ // drop down list
//        String[] item ={"1 Day", "3 Days", "1 Week", "2 Weeks", "3 Weeks", "1 Month"};
//
//        AutoCompleteTextView autoCompleteTextView;
//
//        ArrayAdapter<String> adapterRemindBefore;
//
//        autoCompleteTextView = findViewById(R.id.remindBeforeSelect);
//
//        adapterRemindBefore = new ArrayAdapter<String>(this, R.layout.list_item, item);
//
//        autoCompleteTextView.setAdapter(adapterRemindBefore);
//
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
//                String item = adapterView.getItemAtPosition(position).toString();
//                Toast.makeText(productCreateActivity.this, "category "+item, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private void selectNextServiceDate(){ //select date

        etNextServiceDate = findViewById(R.id.etNextServiceDate);

        final Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        etNextServiceDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(productCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        etNextServiceDate.setText(date);

                    }
                },year, month, day);
                dialog.show();
            }
        });
    }

    private void selectLastServiceDate(){ //select date

        etLastServiceDate = findViewById(R.id.etLastServiceDate);

        final Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        etLastServiceDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(productCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        etLastServiceDate.setText(date);

                    }
                },year, month, day);
                dialog.show();
            }
        });

    }

    private void selectWarrantyDate(){ //select date

        etWarrantyDate = findViewById(R.id.etWarrantyDate);

        final Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        etWarrantyDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(productCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        etWarrantyDate.setText(date);

                    }
                },year, month, day);
                dialog.show();
            }
        });

    }


//    private void selectRemindDate(){ //select date
//
//        remindDateSelect = findViewById(R.id.remindDateSelect);
//
//        final Calendar calender = Calendar.getInstance();
//        final int year = calender.get(Calendar.YEAR);
//        final int month = calender.get(Calendar.MONTH);
//        final int day = calender.get(Calendar.DAY_OF_MONTH);
//
//        remindDateSelect.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog dialog = new DatePickerDialog(productCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//                        month = month+1;
//                        String date = dayOfMonth+"/"+month+"/"+year;
//                        remindDateSelect.setText(date);
//
//                    }
//                },year, month, day);
//                dialog.show();
//            }
//        });
//
//    }

    private void loading (Boolean isLoading){
        if(isLoading){
            binding.buttonAddProduct.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.buttonAddProduct.setVisibility(View.VISIBLE);
        }
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidAddDetails(){

        if(encodedImage == null){
            showToast("Pls select profile image");
            return false;
        }else if(binding.inputProductName.getText().toString().trim().isEmpty()){
            showToast("Pls enter product name");
            return false;
        }else if(binding.categorySelect.getText().toString().trim().isEmpty()){
            showToast("Pls select a category for this product");
            return false;
        }else if(binding.etNextServiceDate.getText().toString().trim().isEmpty()){
            showToast("Pls select next service date");
            return false;
        }else if((binding.etNextServiceDate.getText().toString().trim().isEmpty())&&(binding.etWarrantyDate.getText().toString().trim().isEmpty())){
            showToast("Pls select at least one form Next Service Date or Warranty Date");
            return false;
        }else {
            return true;
        }
    }

}