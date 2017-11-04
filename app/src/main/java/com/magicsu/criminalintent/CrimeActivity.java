package com.magicsu.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.magicsu.criminalintent.fragment.CrimeFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import java.util.UUID;


public class CrimeActivity extends SingleFragmentActivity {
    public static final String EXTRA_CRIME_ID = "com.magicsu.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
