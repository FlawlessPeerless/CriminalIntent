package com.magicsu.criminalintent.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.magicsu.criminalintent.R;

import java.util.Calendar;
import java.util.Date;

/**
 * A Timepicker Dialog {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_TIME = "com.magicsu.android.criminalintent.time";

    private TimePicker mTimePicker;
    private Calendar mCalendar;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(EXTRA_TIME);

        mCalendar = Calendar.getInstance();
        mCalendar.setTime(date);
        int hour1 = mCalendar.get(Calendar.HOUR);
        int minute1 = mCalendar.get(Calendar.MINUTE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null, false);

        mTimePicker = v.findViewById(R.id.dialog_time_picker);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(hour1);
        } else {
            mTimePicker.setCurrentHour(hour1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setMinute(minute1);
        } else {
            mTimePicker.setCurrentHour(minute1);
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle("选择时间")
                .setView(v)
                .setPositiveButton(android.R.string.ok, (DialogInterface dialogInterface, int i) -> {
                    int hour, minute;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        hour = mTimePicker.getHour();
                    } else {
                        hour = mTimePicker.getCurrentHour();
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        minute = mTimePicker.getMinute();
                    } else {
                        minute = mTimePicker.getCurrentMinute();
                    }
                    mCalendar.set(Calendar.HOUR, hour);
                    mCalendar.set(Calendar.MINUTE, minute);
                    sendResult(Activity.RESULT_OK, mCalendar.getTime());
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
