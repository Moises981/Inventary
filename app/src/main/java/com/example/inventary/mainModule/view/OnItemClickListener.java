package com.example.inventary.mainModule.view;

import com.example.inventary.common.pojo.Product;

public interface OnItemClickListener {
    void onItemClick(Product product);
    void onLongItemClick(Product product);
}
