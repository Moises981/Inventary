package com.example.inventary.mainModule.events;

import com.example.inventary.common.pojo.Product;

public class MainEvent {

    //Flags
    public static final int SUCCESS_ADD=0;
    public static final int SUCCESS_UPDATE=1;
    public static final int SUCCESS_REMOVE=2;
    public static final int ERROR_SERVER=100;
    public static final int ERROR_TO_REMOVE=101;



    private Product product;
    private int typeEvent;
    private int resMsg;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public int getResMsg() {
        return resMsg;
    }

    public void setResMsg(int resMsg) {
        this.resMsg = resMsg;
    }
}
