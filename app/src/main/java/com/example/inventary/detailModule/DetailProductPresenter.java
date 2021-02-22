package com.example.inventary.detailModule;

import com.example.inventary.common.pojo.Product;
import com.example.inventary.detailModule.events.DetailProductEvent;

public interface DetailProductPresenter {
    void onCreate();
    void onDestroy();

    void updateProduct(Product product);
    void onEventListener(DetailProductEvent event);
}
