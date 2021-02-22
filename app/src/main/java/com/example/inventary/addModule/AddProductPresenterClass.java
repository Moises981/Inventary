package com.example.inventary.addModule;

import com.example.inventary.addModule.events.AddProductEvent;
import com.example.inventary.addModule.model.AddProductInteractor;
import com.example.inventary.addModule.model.AddProductInteractorClass;
import com.example.inventary.addModule.view.AddProductView;
import com.example.inventary.common.pojo.Product;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AddProductPresenterClass implements AddProductPresenter {

    private AddProductView mView;
    private AddProductInteractor mInteractor;

    public AddProductPresenterClass(AddProductView mView) {
        this.mView = mView;
        this.mInteractor = new AddProductInteractorClass();
    }

    @Override
    public void onShow() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void addProduct(Product product) {
        if (setProgress()) {
            mInteractor.addProduct(product);
        }
    }

    private boolean setProgress() {
        if (mView != null) {
            mView.disableUIElements();
            mView.showProgress();
            return true;
        }
        return false;
    }

    @Subscribe
    @Override
    public void onEventListener(AddProductEvent event) {
        if (mView != null) {
            mView.hideProgress();
            mView.enableUIElements();

            switch (event.getTypeEvent()) {
                case AddProductEvent.SUCCESS_ADD:
                    mView.productAdded();
                    break;
                case AddProductEvent.ERROR_MAX_VALUE:
                    mView.maxValueError(event.getResMsg());
                    break;
                case AddProductEvent.ERROR_SERVER:
                    mView.showError(event.getResMsg());
                    break;
            }
        }
    }
}
