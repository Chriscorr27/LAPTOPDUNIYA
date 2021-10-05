package com.example.laptopduniya;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DateSelectedListener mListener;
    private int p_year=-1,p_month=-1,p_date=-1;

    public DatePickerFragment(int p_year, int p_month, int p_date) {
        this.p_year = p_year;
        this.p_month = p_month;
        this.p_date = p_date;
    }

    public DatePickerFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();

        if(p_year==-1||p_month==-1||p_date==-1){
            p_year = c.get(Calendar.YEAR);
            p_month = c.get(Calendar.MONTH);
            p_date = c.get(Calendar.DAY_OF_MONTH);
        }

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, p_year, p_month, p_date);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        return datePicker;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        mListener.onDateSelected(view, year, month, day);
    }

    public void display(FragmentManager manager, String tag, DateSelectedListener listener) {
        super.show(manager, tag);
        mListener = listener;
    }

    public interface DateSelectedListener {
        void onDateSelected(DatePicker view, int year, int month, int day);
    }
}