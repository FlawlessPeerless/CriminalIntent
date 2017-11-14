package com.magicsu.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.magicsu.criminalintent.fragment.CrimeFragment;
import com.magicsu.criminalintent.fragment.CrimeListFragment;
import com.magicsu.criminalintent.model.Crime;
import com.magicsu.criminalintent.model.CrimeLab;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    public static final String EXTRA_SUBTITTLE_VISIBLE = "subtittle_visible";
    private static final String EXTRA_CRIME_ID = "com.magicsu.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context packageContext, UUID crimeId, Boolean subtittleIsVisible) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        intent.putExtra(EXTRA_SUBTITTLE_VISIBLE, subtittleIsVisible);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mViewPager = findViewById(R.id.activity_crime_pager_view_pager);
        mCrimes = CrimeLab.get(this).getCrimes();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }


    /**
     * 设置toolbar返回parent栈的逻辑
     * @return Intent 所要去的intent
     */
    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        boolean isVisible = parentIntent.getBooleanExtra(EXTRA_SUBTITTLE_VISIBLE, false);

        Intent newIntent = new Intent(CrimePagerActivity.this, CrimeListFragment.class);
        newIntent.putExtra(EXTRA_SUBTITTLE_VISIBLE, isVisible);
        return newIntent;
    }

}
