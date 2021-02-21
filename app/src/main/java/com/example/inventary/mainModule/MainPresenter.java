package com.example.inventary.mainModule;

import com.example.inventary.common.pojo.Product;
import com.example.inventary.mainModule.events.MainEvent;

public interface MainPresenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();
    void remove(Product product);
    void onEventListener(MainEvent event);

}
