package com.example.inventary.addModule.model;

import com.example.inventary.addModule.events.AddProductEvent;
import com.example.inventary.addModule.model.dataAccess.RealtimeDatabase;
import com.example.inventary.common.BasicErrorEventCallback;
import com.example.inventary.common.pojo.Product;

import org.greenrobot.eventbus.EventBus;

public class AddProductInteractorClass implements AddProductInteractor{

    private RealtimeDatabase mDatabase;

    public AddProductInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void addProduct(Product product) {
        mDatabase.addProduct(product, new BasicErrorEventCallback() {
            @Override
            public void onSucess() {
                post(AddProductEvent.SUCCESS_ADD);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent,resMsg);
            }
        });
    }

    private void post(int typeEvent) {
        post(typeEvent,0);
    }

    private void post(int typeEvent, int resMsg) {
        AddProductEvent event  = new AddProductEvent();
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }
}
