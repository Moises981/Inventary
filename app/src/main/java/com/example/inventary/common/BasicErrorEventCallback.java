package com.example.inventary.common;

public interface BasicErrorEventCallback {
    void onSucess();
    void onError(int typeEvent, int resMsg);
}
