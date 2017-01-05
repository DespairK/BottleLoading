package com.zjk.despairk.bottleloading.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjk.despairk.bottleloading.R;

/**
 * com.zjk.despairk.bottleloading.fragment
 * DespairK
 *
 * @author ZJK
 *         created at 2017/1/5 12:01
 *         功能:
 */
public class MainFragment extends Fragment {
    public ViewGroup rootView;

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initValue();
    }

    private void initValue() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment, container, false);
        return rootView;
    }
}
