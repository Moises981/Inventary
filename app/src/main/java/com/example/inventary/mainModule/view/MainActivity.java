package com.example.inventary.mainModule.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.inventary.R;
import com.example.inventary.addModule.view.AddProductkFragment;
import com.example.inventary.common.pojo.Product;
import com.example.inventary.databinding.ActivityMainBinding;
import com.example.inventary.databinding.ContentMainBinding;
import com.example.inventary.detailModule.view.DetailFragment;
import com.example.inventary.mainModule.MainPresenter;
import com.example.inventary.mainModule.MainPresenterClass;
import com.example.inventary.mainModule.view.adapters.ProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener,MainView{

    ActivityMainBinding bnd;
    ContentMainBinding bndMain;
    private MainPresenter mPresenter;
    private ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityMainBinding.inflate(getLayoutInflater());
        //bndMain = bndMain.bind(bnd.getRoot());
        setContentView(bnd.getRoot());
        bnd.fab.setOnClickListener(onAddClicked);
        configToolbar();
        configAdapter();
        configRecyclerView();
        mPresenter = new MainPresenterClass(this);
        mPresenter.onCreate();
    }
    
    private View.OnClickListener onAddClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AddProductkFragment().show(getSupportFragmentManager(),getString(R.string.addProduct_title));
        }
    };


    private void configToolbar() {
        setSupportActionBar(bnd.toolbar);
    }

    private void configAdapter(){
        mAdapter = new ProductAdapter(new ArrayList<Product>(),this);
    }

    private void configRecyclerView(){
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.main_column));
        bnd.contentMain.recyclerView.setLayoutManager(linearLayoutManager);
        bnd.contentMain.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Main view

    @Override
    public void showProgress() {
        bnd.contentMain.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        bnd.contentMain.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void add(Product product) {
        mAdapter.add(product);
    }

    @Override
    public void update(Product product) {
        mAdapter.update(product);
    }

    @Override
    public void remove(Product product) {
        mAdapter.remove(product);
    }

    @Override
    public void onShowError(int resMs) {
        Snackbar.make(bnd.contentMain.getRoot(),resMs,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void removeFail() {
        Snackbar.make(bnd.contentMain.getRoot(),R.string.main_error_remove,Snackbar.LENGTH_LONG).show();
    }

    //On item click listener

    @Override
    public void onItemClick(Product product) {
        Bundle arguments = new Bundle();
        arguments.putString(Product.ID,product.getId());
        arguments.putString(Product.NAME,product.getName());
        arguments.putInt(Product.QUANTITY,product.getQuantity());
        arguments.putString(Product.PHOTO_URL,product.getPhotoUrl());

        Fragment fragment = new DetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentMain,fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onLongItemClick(Product product) {

        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if(vibrator != null){
            vibrator.vibrate(60);
        }

        new AlertDialog.Builder(this).setTitle(R.string.main_dialog_remove_title)
                .setMessage(R.string.main_dialog_remove_message)
                .setPositiveButton(R.string.main_dialog_remove_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.remove(product);
                    }
                })
                .setNegativeButton(R.string.common_dialog_cancel,null)
                .show();

    }
}