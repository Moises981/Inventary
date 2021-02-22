package com.example.inventary.detailModule.model;

import com.example.inventary.common.BasicEventCallback;
import com.example.inventary.detailModule.events.DetailProductEvent;
import com.example.inventary.detailModule.model.dataAccess.RealtimeDatabase;
import com.example.inventary.common.pojo.Product;

import org.greenrobot.eventbus.EventBus;

public class DetailProductInteractorClass implements  DetailProductInteractor{
    private  RealtimeDatabase mDatabase;

    public DetailProductInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void updateProduct(Product product) {
        mDatabase.updateProduct(product, new BasicEventCallback() {
            @Override
            public void onSuccess() {
                post(DetailProductEvent.UPDATE_SUCCESS);
            }

            @Override
            public void onError() {
                post(DetailProductEvent.ERROR_SERVER);
            }
        });
    }

    private void post(int typeEvent) {
        DetailProductEvent event = new DetailProductEvent();
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }
}
