package com.androidtecknowlogy.directory.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nezspencer on 12/8/16.
 */

public class ProfileAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList=new ArrayList<>();
    private final List<String> fragmentTitle=new ArrayList<>();
    public ProfileAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment,String title){

        fragmentList.add(fragment);
        fragmentTitle.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
}
