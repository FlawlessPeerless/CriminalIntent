package com.magicsu.criminalintent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.magicsu.criminalintent.CrimeListActivity;
import com.magicsu.criminalintent.CrimePagerActivity;
import com.magicsu.criminalintent.R;
import com.magicsu.criminalintent.model.Crime;
import com.magicsu.criminalintent.model.CrimeLab;

import java.util.List;

/**
 * Created by admin on 2017/11/3.
 */

public class CrimeListFragment extends Fragment {
    private static final String SAVED_SUBTITTLE_VISIBLE = "subtittle";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private int position;
    private boolean mSubtitleVisible;

    /**
     * CrimeList 适配器
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    /**
     * CrimeList 视图控制器
     */
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTittleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;

        public CrimeHolder(View itemView) {
            super(itemView);
            mTittleTextView = itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = itemView.findViewById(R.id.list_item_crime_solved_check_box);
            mSolvedCheckBox.setOnCheckedChangeListener((compoundButton, b) -> mCrime.setSolved(b));

            itemView.setOnClickListener(this);

        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTittleTextView.setText(crime.getTitle());
            String date = (String) DateFormat.format("EEEE,MMMM dd,yyyy", crime.getDate());
            mDateTextView.setText(date);
            mSolvedCheckBox.setChecked(crime.isSolved());
        }

        @Override
        public void onClick(View view) {
            CrimeLab mCrimeLab = CrimeLab.get(getActivity());
            position = mCrimeLab.getCrimeIndex(mCrime);

            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId(), mSubtitleVisible);
            startActivity(intent);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mSubtitleVisible = getArguments().getBoolean(CrimeListActivity.ARG_SUBTITTLE_VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITTLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId(), mSubtitleVisible);
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                //noinspection ConstantConditions
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITTLE_VISIBLE, mSubtitleVisible);
    }

    private void updateUI() {
        if (mAdapter == null) {
            CrimeLab crimeLab = CrimeLab.get(getActivity());
            List<Crime> crimes = crimeLab.getCrimes();
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemChanged(position);
        }
        updateSubtitle();
    }

    @SuppressWarnings("ConstantConditions")
    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().setSubtitle(subtitle);
        }
    }
}
