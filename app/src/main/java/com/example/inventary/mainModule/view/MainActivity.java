package com.example.inventary.mainModule.view;

import android.os.Bundle;

import com.example.inventary.R;
import com.example.inventary.common.pojo.Product;
import com.example.inventary.databinding.ActivityMainBinding;
import com.example.inventary.databinding.ContentMainBinding;
import com.example.inventary.mainModule.MainPresenter;
import com.example.inventary.mainModule.MainPresenterClass;
import com.example.inventary.mainModule.view.adapters.ProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        configToolbar();
        configAdapter();
        configRecyclerView();
        mPresenter = new MainPresenterClass(this);
        mPresenter.onCreate();
    }


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

    }

    @Override
    public void onLongItemClick(Product product) {

    }
}