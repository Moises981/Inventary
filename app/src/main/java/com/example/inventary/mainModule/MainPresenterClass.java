package com.example.inventary.mainModule;

import com.example.inventary.common.pojo.Product;
import com.example.inventary.mainModule.events.MainEvent;
import com.example.inventary.mainModule.model.MainInteractor;
import com.example.inventary.mainModule.model.MainInteractorClass;
import com.example.inventary.mainModule.view.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainPresenterClass implements MainPresenter {

    private MainView mView;
    private MainInteractor mInteractor;

    public MainPresenterClass(MainView mView) {
        this.mView = mView;
        this.mInteractor = new MainInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        mInteractor.unsubscribeToProducts();
    }

    @Override
    public void onResume() {
        mInteractor.subscribeToProducts();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void remove(Product product) {
        if (setProgress()) {
            mInteractor.removeProduct(product);
        }
    }

    private boolean setProgress() {
        if (mView != null) {
            mView.showProgress();
            return true;
        }
        return false;
    }

    @Subscribe
    @Override
    public void onEventListener(MainEvent event) {
        if (mView != null) {
            mView.hideProgress();

            switch (event.getTypeEvent()) {
                case MainEvent.SUCCESS_ADD:
                    mView.add(event.getProduct());
                    break;
                case MainEvent.SUCCESS_UPDATE:
                    mView.update(event.getProduct());
                    break;
                case MainEvent.SUCCESS_REMOVE:
                    mView.remove(event.getProduct());
                    break;
                case MainEvent.ERROR_SERVER:
                    mView.onShowError(event.getResMsg());
                    break;
                case MainEvent.ERROR_TO_REMOVE:
                    mView.removeFail();
                    break;

            }
        }
    }
}
