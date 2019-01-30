package com.example.chopin_tp_2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chopin_tp_2.Tab1;
import com.example.chopin_tp_2.Tab2;


public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {

    private Context context;

    public SimpleFragmentPageAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        if(position ==0)
        {
            return new Tab1();
        }
        else
        {
            return new Tab2();
        }
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch(position)
        {
            case 0: return context.getString(R.string.hello_blank_fragment);
            case 1: return context.getString(R.string.hello_blank_fragment);

            default : return null;
        }
    }
}
