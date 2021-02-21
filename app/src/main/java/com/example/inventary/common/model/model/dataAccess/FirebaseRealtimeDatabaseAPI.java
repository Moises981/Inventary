package com.example.inventary.common.model.model.dataAccess;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRealtimeDatabaseAPI {
    private DatabaseReference reference;
    private static FirebaseRealtimeDatabaseAPI INSTANCE = null;

    private FirebaseRealtimeDatabaseAPI() {
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseRealtimeDatabaseAPI getInstance(){
        if(INSTANCE == null){
            INSTANCE = new FirebaseRealtimeDatabaseAPI();
        }
        return INSTANCE;
    }

    // References

    public DatabaseReference getReference(){
        return reference;
    }


}
