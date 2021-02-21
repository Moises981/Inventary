package com.example.inventary.mainModule.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.inventary.R;
import com.example.inventary.common.BasicErrorEventCallback;
import com.example.inventary.common.model.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.example.inventary.common.pojo.Product;
import com.example.inventary.mainModule.events.MainEvent;
import com.example.inventary.mainModule.model.dataAccess.ProductsEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class RealtimeDatabase {
    private static final String PATH_PRODUCTS = "products";

    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;
    private ChildEventListener mProductsChildEventListener;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    private DatabaseReference getProductsReference() {
        return mDatabaseAPI.getReference().child(PATH_PRODUCTS);
    }

    public void subscribeToProducts(ProductsEventListener listener) {
        if (mProductsChildEventListener == null) {
            mProductsChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    listener.onChildAdded(getProduct(snapshot));
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    listener.onChildUpdated(getProduct(snapshot));
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    listener.onChildRemoved(getProduct(snapshot));
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    switch (error.getCode()) {
                        case DatabaseError.PERMISSION_DENIED:
                            listener.onError(R.string.main_error_permission_denied);
                            break;
                        default:
                            listener.onError(R.string.main_error_server);
                    }
                }
            };
        }
        getProductsReference().addChildEventListener(mProductsChildEventListener);
    }

    private Product getProduct(DataSnapshot snapshot) {
        Product product = snapshot.getValue(Product.class);
        if (product != null) {
            product.setId(snapshot.getKey());
        }
        return product;
    }

    public void unsuscribeToProducts() {
        if (mProductsChildEventListener != null) {
            getProductsReference().removeEventListener(mProductsChildEventListener);
        }
    }

    public void removeProduct(Product product, BasicErrorEventCallback callback) {
        getProductsReference().child(product.getId())
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                            callback.onSucess();
                        } else {
                            switch (error.getCode()){
                                case DatabaseError.PERMISSION_DENIED:
                                    callback.onError(MainEvent.ERROR_TO_REMOVE,R.string.main_error_remove);
                                    break;
                                default:
                                    callback.onError(MainEvent.ERROR_SERVER,R.string.main_error_server);
                            }
                        }
                    }
                });
    }


}
