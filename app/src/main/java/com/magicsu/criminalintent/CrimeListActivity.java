package com.magicsu.criminalintent;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.magicsu.criminalintent.fragment.CrimeListFragment;
import com.magicsu.criminalintent.model.CrimeLab;

/**
 * Created by admin on 2017/11/3.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    public static final String ARG_SUBTITTLE_VISIBLE = "arg.subtittle.visible";

    @Override
    protected Fragment createFragment() {
        Boolean isVisibile = getIntent().getBooleanExtra(CrimePagerActivity.EXTRA_SUBTITTLE_VISIBLE, false);
        Bundle args = new Bundle();
        args.putBoolean(ARG_SUBTITTLE_VISIBLE, isVisibile);

        Fragment fragment = new CrimeListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
