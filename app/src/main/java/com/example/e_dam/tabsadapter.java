package com.example.e_dam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class tabsadapter extends FragmentPagerAdapter {
    public tabsadapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                isteklerFragment isteklerFragment=new isteklerFragment();
                return isteklerFragment;

            case 1:
                mesajFragment mesajFragment= new mesajFragment();
                return mesajFragment;

            case 2:
                arkadasFragment arkadasFragment=new arkadasFragment();
                return arkadasFragment;
            default:
                return null;

        }
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){

            case 0:
                return "İSTEKLER";
            case 1:
                return "MESAJLAR";
            case 2:
                return "ARKADASLAR";

            default:
                return null;


        }


    }
}
