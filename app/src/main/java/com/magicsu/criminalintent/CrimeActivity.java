package com.magicsu.criminalintent;

import android.support.v4.app.Fragment;
import com.magicsu.criminalintent.fragment.CrimeFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;


public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
