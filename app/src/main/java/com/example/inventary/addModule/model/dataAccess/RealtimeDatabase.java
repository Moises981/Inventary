package com.example.inventary.addModule.model.dataAccess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.inventary.R;
import com.example.inventary.addModule.events.AddProductEvent;
import com.example.inventary.common.BasicErrorEventCallback;
import com.example.inventary.common.model.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.example.inventary.common.pojo.Product;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void addProduct(Product product, BasicErrorEventCallback callback) {
        mDatabaseAPI.getProductsReference().push().setValue(product, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    callback.onSucess();
                } else {
                    switch (error.getCode()) {
                        case DatabaseError.PERMISSION_DENIED:
                            callback.onError(AddProductEvent.ERROR_MAX_VALUE,
                                    R.string.addProduct_message_validate_max_quantity);
                            break;
                        default:
                            callback.onError(AddProductEvent.ERROR_SERVER,
                                    R.string.addProduct_message_added_error);

                    }
                }
            }
        });
    }


}
