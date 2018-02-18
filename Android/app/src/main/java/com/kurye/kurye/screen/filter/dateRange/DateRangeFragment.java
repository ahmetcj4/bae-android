package com.kurye.kurye.screen.filter.dateRange;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker;
import com.kurye.kurye.databinding.FragmentDateRangeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateRangeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateRangeFragment extends Fragment {
    public DateRangeFragment() {}

    private SublimeDatePicker dateRangePicker;

    public static DateRangeFragment newInstance() {
        return new DateRangeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentDateRangeBinding binding = FragmentDateRangeBinding.inflate(inflater, container, false);
        dateRangePicker = binding.dateRangePicker;
        return binding.getRoot();
    }

    public SelectedDate getDate(){
        return dateRangePicker.getSelectedDate();
    }
}
