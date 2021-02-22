package com.example.inventary.mainModule.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.inventary.common.pojo.Product;
import com.example.inventary.R;
import com.example.inventary.databinding.ItemProductBinding;
import com.example.inventary.mainModule.view.OnItemClickListener;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private OnItemClickListener listener;
    private Context context;

    public ProductAdapter(List<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new  ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.setOnClickListener(product,listener);


        holder.bnd.tvData.setText(context.getString(R.string.item_product_data,product.getName(),
               product.getQuantity()));
        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(context).load(product.getPhotoUrl())
                .apply(options)
                .into(holder.bnd.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void add(Product product){
        if(!productList.contains(product)){
            productList.add(product);
            notifyItemInserted(productList.size()-1);
        }
        else{
            update(product);
        }
    }

    public void update(Product product) {
        if(productList.contains(product)){
            final int index = productList.indexOf(product);
            productList.set(index,product);
            notifyItemChanged(index);
        }
    }

    public void remove(Product product){
        if(productList.contains(product)){
            final int index = productList.indexOf(product);
            productList.remove(index);
            notifyItemRemoved(index);
        }
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        private ItemProductBinding bnd;

        ViewHolder(ItemProductBinding bnd) {
            super(bnd.getRoot());
            this.bnd = bnd;
        }

        void setOnClickListener(Product product, OnItemClickListener listener) {
            bnd.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(product);
                }
            });
            bnd.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongItemClick(product);
                    return true;
                }
            });
        }
    }

}
