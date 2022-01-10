package com.camp.project2;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewpagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> items = new ArrayList<>();


    public ViewpagerAdapter(FragmentManager fm){
        super(fm);
    }

    public void addItem(Fragment item){ items.add(item); }

    @NonNull
    @Override
    public Fragment getItem(int position){
        return items.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        // 뷰페이저에서 삭제.
        container.removeView((View) object);
    }

    @Override
    public int getCount(){
        return items.size();
    }

}
