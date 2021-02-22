package com.example.inventary.detailModule.view;

public interface DetailProductView {
    void showProgress();
    void hideProgress();
    void enableUIElements();
    void disableUIElements();

    void showFab();
    void hideFab();

    void updateSuccess();
    void updateError();
}
