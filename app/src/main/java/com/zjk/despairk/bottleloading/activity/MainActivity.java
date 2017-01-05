package com.zjk.despairk.bottleloading.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zjk.despairk.bottleloading.R;
import com.zjk.despairk.bottleloading.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction()
                .replace(R.id.fl_top_view, MainFragment.newInstance()).commit();
    }
}
