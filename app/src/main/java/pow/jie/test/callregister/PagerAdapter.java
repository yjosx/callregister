package pow.jie.test.callregister;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    String[] list;

    List<Fragment> fragments;


    public PagerAdapter(FragmentManager fm, String[] list, List<Fragment> fragments) {
        super(fm);
        this.list = list;
        this.fragments = fragments;
    }


    @Override

    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override

    public CharSequence getPageTitle(int position) {
        return list[position];
    }


    @Override

    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }

}

