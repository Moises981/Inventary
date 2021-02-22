package com.example.inventary.detailModule;

import com.example.inventary.common.pojo.Product;
import com.example.inventary.detailModule.events.DetailProductEvent;
import com.example.inventary.detailModule.model.DetailProductInteractor;
import com.example.inventary.detailModule.model.DetailProductInteractorClass;
import com.example.inventary.detailModule.view.DetailProductView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DetailProductPresenterClass implements DetailProductPresenter {

    private DetailProductInteractor mInteractor;
    private DetailProductView mView;

    public DetailProductPresenterClass(DetailProductView mView) {
        this.mView = mView;
        this.mInteractor = new DetailProductInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
        if (mView != null) {
            mView.hideFab();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mView != null) {
            mView.showFab();
            mView = null;
        }
    }

    @Override
    public void updateProduct(Product product) {
        if (setProgress()) {
            mInteractor.updateProduct(product);
        }
    }

    private boolean setProgress() {
        if (mView != null) {
            mView.showProgress();
            mView.disableUIElements();
            return true;
        }
        return false;
    }

    @Subscribe
    @Override
    public void onEventListener(DetailProductEvent event) {
        if (mView != null) {
            mView.hideProgress();
            mView.enableUIElements();
            switch (event.getTypeEvent()) {
                case DetailProductEvent.UPDATE_SUCCESS:
                    mView.updateSuccess();
                    break;
                case DetailProductEvent.ERROR_SERVER:
                    mView.updateError();
                    break;
            }
        }
    }
}
