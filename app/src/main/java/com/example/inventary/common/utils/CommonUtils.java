package com.example.inventary.common.utils;

import android.content.Context;
import android.widget.EditText;

import com.example.inventary.R;

public class CommonUtils {
    public static boolean ValidateProduct(Context context, EditText etName,
                                          EditText etQuantifty, EditText etPhotoUrl) {
        boolean isValid = true;

        if (etQuantifty.getText().toString().trim().isEmpty()) {
            etQuantifty.setError(context.getString(R.string.common_validate_field_required));
            etQuantifty.requestFocus();
            isValid = false;
        } else if (Integer.valueOf(etQuantifty.getText().toString().trim()) <= 0) {
            etQuantifty.setError(context.getString(R.string.common_validate_min_quantity));
            etQuantifty.requestFocus();
            isValid = false;
        }

        if (etPhotoUrl.getText().toString().trim().isEmpty()) {
            etPhotoUrl.setError(context.getString(R.string.common_validate_field_required));
            etPhotoUrl.requestFocus();
            isValid = false;
        }

        if (etName.getText().toString().trim().isEmpty()) {
            etName.setError(context.getString(R.string.common_validate_field_required));
            etName.requestFocus();
            isValid = false;
        }

        return isValid;
    }
}
