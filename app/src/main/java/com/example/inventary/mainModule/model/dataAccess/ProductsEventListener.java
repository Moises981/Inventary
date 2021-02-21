package com.example.inventary.mainModule.model.dataAccess;

import com.example.inventary.common.pojo.Product;

public interface ProductsEventListener {
    void onChildAdded(Product product);
    void onChildUpdated(Product product);
    void onChildRemoved(Product product);
    void onError(int resMsg);
}
