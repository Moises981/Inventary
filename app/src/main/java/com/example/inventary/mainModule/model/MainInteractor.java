package com.example.inventary.mainModule.model;

import com.example.inventary.common.pojo.Product;

public interface MainInteractor {
    void subscribeToProducts();
    void unsubscribeToProducts();
    void removeProduct(Product product);
}
