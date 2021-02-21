package com.example.inventary.mainModule.view;

import com.example.inventary.common.pojo.Product;

public interface MainView {
    void showProgress();
    void hideProgress();

    void add(Product product);
    void update(Product product);
    void remove(Product product);

    void onShowError(int resMs);
    void removeFail();
}
