package com.example.chatapplecation01.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.chatapplecation01.databinding.ItemContainerProductBinding;
import com.example.chatapplecation01.models.Product;

import java.text.SimpleDateFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

//    private List<Product> products;
//    private Context context;
//
//    public ProductAdapter(List<Product> products, Context context) {
//        this.products = products;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_container_product, parent, false);
//        return new ProductViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
//        Product product = products.get(position);
//
//        holder.productName.setText(product.getProductName());
//        holder.productCategory.setText(product.getProductCategory());
//    }
//
//    @Override
//    public int getItemCount() {
//        return products.size();
//    }
//
//    public class ProductViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView productName;
//        public TextView productCategory;
//
//        public ProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            productName = (TextView) itemView.findViewById(R.id.textName);
//            productCategory = (TextView) itemView.findViewById(R.id.textCategory);
//        }
//    }


    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    private final List<Product> products;
//    private final ProductListener productListener;
//    private final String currentUserId;
//    private Bitmap productImage;

    public ProductAdapter(List<Product> products) {
        this.products = products;
//        this.productListener = productListener;
//        this.currentUserId = currentUserId;
//        this.productImage = productImage;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerProductBinding itemContainerProductBinding = ItemContainerProductBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ProductViewHolder(itemContainerProductBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        holder.setProductData(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        ItemContainerProductBinding binding;

        ProductViewHolder(ItemContainerProductBinding itemContainerProductBinding){
            super(itemContainerProductBinding.getRoot());
            binding = itemContainerProductBinding;
        }

        void  setProductData(Product product){
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//            String nextServiceDateString = dateFormat.format(product.nextServiceDate);
//            String warrantyExpiryDateString = dateFormat.format(product.warrantyExpiryDate);

            binding.textName.setText(product.productName);
            binding.textCategory.setText(product.productCategory);
//            binding.textNextServiceDate.setText(nextServiceDateString);
//            binding.textWarrantyDate.setText(warrantyExpiryDateString);
            binding.imageProduct.setImageBitmap(getProductImage(product.productImage));
//            binding.getRoot().setOnClickListener(v -> productListener.onProductClicked(product));
        }

    }

    private Bitmap getProductImage(String encodedImage){
        if (encodedImage == null) {
            return null;
        }
        byte[] bytes = Base64.decode(encodedImage,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
        return Bitmap.createScaledBitmap(bitmap, size, size, false);
    }
}
