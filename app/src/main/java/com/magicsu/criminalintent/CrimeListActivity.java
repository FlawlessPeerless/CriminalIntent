package com.magicsu.criminalintent;

import android.support.v4.app.Fragment;

import com.magicsu.criminalintent.fragment.CrimeListFragment;

/**
 * Created by admin on 2017/11/3.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
