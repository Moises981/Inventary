package com.example.inventary.detailModule.model.dataAccess;

import androidx.annotation.NonNull;

import com.example.inventary.common.BasicEventCallback;
import com.example.inventary.common.model.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.example.inventary.common.pojo.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void updateProduct(Product product, BasicEventCallback callback){
        mDatabaseAPI.getProductsReference().child(product.getId()).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError();
            }
        });
    }
}
