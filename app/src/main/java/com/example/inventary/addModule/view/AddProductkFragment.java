package com.example.inventary.addModule.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.inventary.R;
import com.example.inventary.addModule.AddProductPresenter;
import com.example.inventary.addModule.AddProductPresenterClass;
import com.example.inventary.common.pojo.Product;
import com.example.inventary.common.utils.CommonUtils;
import com.example.inventary.databinding.FragmentAddProductBinding;
import com.google.android.material.snackbar.Snackbar;

public class AddProductkFragment extends DialogFragment
        implements DialogInterface.OnShowListener,AddProductView {


    private FragmentAddProductBinding bnd;
    private AddProductPresenter mPresenter;

    public AddProductkFragment() {
        mPresenter = new AddProductPresenterClass(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.addProduct_title)
                .setPositiveButton(R.string.addProduct_dialog_ok,null)
                .setNegativeButton(R.string.common_dialog_cancel,null);
        bnd = bnd.inflate(getLayoutInflater());
        builder.setView(bnd.getRoot());

        configFocus();
        configEditText();

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroy();
    }

    private void configEditText() {
        bnd.etPhotoUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                final String photoUrl = bnd.etPhotoUrl.getText().toString().trim();
                if(photoUrl.isEmpty()){
                    bnd.imgPhoto.setImageDrawable(null);
                }else{
                    if(getActivity() != null){
                        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop();

                        Glide.with(getActivity()).load(photoUrl).apply(options).into(bnd.imgPhoto);
                    }
                }
            }
        });
    }



    private void configFocus() {
        bnd.etName.requestFocus();
    }

    @Override
    public void onShow(DialogInterface dialog) {
        final AlertDialog Adialog = (AlertDialog)getDialog();
        if(Adialog != null){
            Button positive = Adialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negative = Adialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CommonUtils.ValidateProduct(getActivity(),bnd.etName,bnd.etQuantity,bnd.etPhotoUrl)) {
                        Product product = new Product();
                        product.setName(bnd.etName.getText().toString().trim());
                        product.setQuantity(Integer.valueOf(bnd.etQuantity.getText().toString().trim()));
                        product.setPhotoUrl(bnd.etPhotoUrl.getText().toString().trim());
                        product.setName(bnd.etName.getText().toString().trim());
                        mPresenter.addProduct(product);
                    }
                }
            });
            negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        mPresenter.onShow();
    }

    @Override
    public void enableUIElements() {
        bnd.etName.setEnabled(true);
        bnd.etQuantity.setEnabled(true);
        bnd.etPhotoUrl.setEnabled(true);
    }

    @Override
    public void disableUIElements() {
        bnd.etName.setEnabled(false);
        bnd.etQuantity.setEnabled(false);
        bnd.etPhotoUrl.setEnabled(false);
    }

    @Override
    public void showProgress() {
        bnd.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        bnd.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void productAdded() {
        Toast.makeText(getActivity(),R.string.addProduct_message_added_successfully,Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void showError(int resMsg) {
        Snackbar.make(bnd.contentMain,resMsg,Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.addProduct_snackbar_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    @Override
    public void maxValueError(int resMsg) {
        bnd.etQuantity.setError(getString(resMsg));
        bnd.etQuantity.requestFocus();
    }
}