package com.example.inventary.detailModule.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.inventary.R;
import com.example.inventary.common.pojo.Product;
import com.example.inventary.common.utils.CommonUtils;
import com.example.inventary.databinding.FragmentDetailBinding;
import com.example.inventary.detailModule.DetailProductPresenter;
import com.example.inventary.detailModule.DetailProductPresenterClass;
import com.google.android.material.snackbar.Snackbar;

public class DetailFragment extends Fragment implements DetailProductView {
    private FragmentDetailBinding bnd;
    private Product mProduct;
    private DetailProductPresenter mPresenter;


    public DetailFragment() {
        // Required empty public constructor
        mPresenter = new DetailProductPresenterClass(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bnd = bnd.inflate(inflater, container, false);
        bnd.btnSave.setOnClickListener(onSaveClicked);
        View view = bnd.getRoot();
        if (getArguments() != null) {
            configProduct(getArguments());
            configValues();
            configEditText();
        }
        mPresenter.onCreate();
        return view;
    }

    private View.OnClickListener onSaveClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (CommonUtils.ValidateProduct(getActivity(),bnd.etName,bnd.etQuantity,bnd.etPhotoUrl)) {
                mProduct.setName(bnd.etName.getText().toString().trim());
                mProduct.setPhotoUrl(bnd.etPhotoUrl.getText().toString().trim());
                mProduct.setQuantity(Integer.valueOf(bnd.etQuantity.getText().toString().trim()));
                mPresenter.updateProduct(mProduct);
            }
        }
    };

    private void configEditText() {
        bnd.etPhotoUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String photoUrl = bnd.etPhotoUrl.getText().toString().trim();
                if(photoUrl.isEmpty()){
                    bnd.imgPhoto.setImageDrawable(null);
                }else{
                    configPhoto(photoUrl);
                }
            }
        });
    }

    private void configValues() {
        bnd.etName.setText(mProduct.getName());
        bnd.etPhotoUrl.setText(mProduct.getPhotoUrl());
        bnd.etQuantity.setText(String.valueOf(mProduct.getQuantity()));
        configPhoto(mProduct.getPhotoUrl());
    }

    private void configPhoto(String photoUrl) {
        if (getActivity() != null) {
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop();
            Glide.with(getActivity())
                    .load(photoUrl)
                    .apply(options)
                    .into(bnd.imgPhoto);
        }
    }

    private void configProduct(Bundle arguments) {
        mProduct = new Product();
        mProduct.setId(arguments.getString(Product.ID));
        mProduct.setName(arguments.getString(Product.NAME));
        mProduct.setQuantity(arguments.getInt(Product.QUANTITY));
        mProduct.setPhotoUrl(arguments.getString(Product.PHOTO_URL));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bnd = null;
        mPresenter.onDestroy();
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
    public void enableUIElements() {
        bnd.etName.setEnabled(true);
        bnd.etQuantity.setEnabled(true);
        bnd.etPhotoUrl.setEnabled(true);
        bnd.btnSave.setEnabled(true);
    }

    @Override
    public void disableUIElements() {
        bnd.etName.setEnabled(false);
        bnd.etQuantity.setEnabled(false);
        bnd.etPhotoUrl.setEnabled(false);
        bnd.btnSave.setEnabled(false);
    }

    @Override
    public void showFab() {

        if (getActivity() != null) {
            getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideFab() {
        if (getActivity() != null) {
            getActivity().findViewById(R.id.fab).setVisibility(View.GONE);
        }
    }

    @Override
    public void updateSuccess() {
        Snackbar.make(bnd.getRoot(),R.string.detailProduct_update_success,Snackbar.LENGTH_LONG)
                .setAction(R.string.detailProduct_snackbar_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getActivity() != null) {
                            getActivity().onBackPressed();
                        }
                    }
                }).show();
    }

    @Override
    public void updateError() {
        Snackbar.make(bnd.getRoot(),R.string.dtailProduct_update_error,Snackbar.LENGTH_LONG).show();
    }
}