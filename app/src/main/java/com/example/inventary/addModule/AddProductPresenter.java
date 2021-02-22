package com.example.inventary.addModule;

import com.example.inventary.addModule.events.AddProductEvent;
import com.example.inventary.common.pojo.Product;

public interface AddProductPresenter {
    void onShow();
    void onDestroy();

    void addProduct(Product product);
    void onEventListener(AddProductEvent event);
}
