package com.kurye.kurye.screen.filter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.kurye.kurye.R;
import com.kurye.kurye.common.DateUtils;
import com.kurye.kurye.common.ViewUtils;
import com.kurye.kurye.entity.request.SearchDeliverersRequest;
import com.kurye.kurye.screen.filter.dateRange.DateRangeFragment;
import com.kurye.kurye.screen.filter.editText.EditTextFragment;
import com.kurye.kurye.screen.selectDeliverer.SelectDelivererActivity;
import com.kurye.kurye.task.FilterTask;

import java.util.ArrayList;

import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;
import me.drozdzynski.library.steppers.interfaces.OnFinishAction;

/**
 * A placeholder fragment containing a simple view.
 */
public class FilterActivityFragment extends Fragment {
    private SearchDeliverersRequest searchDeliverersRequest;

    private SteppersView steppersView;
    private OnFinishAction onFinishAction;

    public FilterActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter, container, false);
        searchDeliverersRequest = new SearchDeliverersRequest();
        setupSteppersView(v);
        return v;
    }

    private void setupSteppersView(View v) {
        steppersView = v.findViewById(R.id.steppersView);
        steppersView.setItems(getStepperItems());
        steppersView.setConfig(getConfig());
        steppersView.build();
    }

    @NonNull
    private SteppersView.Config getConfig() {
        SteppersView.Config steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setFragmentManager(getChildFragmentManager());
        steppersViewConfig.setCancelAvailable(false);
        steppersViewConfig.setOnFinishAction(onFinishAction);
        return steppersViewConfig;
    }

    @NonNull
    private ArrayList<SteppersItem> getStepperItems() {
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        ArrayList<SteppersItem> steps = new ArrayList<>();
        steps.add(getStepFirst());
        steps.add(getStepSecond(typeFilter));
        steps.add(getStepThird(typeFilter));
        steps.add(getStepFourth());
        return steps;
    }

    @NonNull
    private SteppersItem getStepFirst() {
        SteppersItem stepFirst = new SteppersItem();
        stepFirst.setLabel(getString(R.string.select_item_label));
        stepFirst.setSubLabel(getString(R.string.select_item_description));

        EditTextFragment editTextFragment = EditTextFragment.newInstance(getString(R.string.select_item_question));
        editTextFragment.setListener(entity -> searchDeliverersRequest.setItemID(entity.getId()));
        stepFirst.setFragment(editTextFragment);
        stepFirst.setOnClickContinue(() -> {
            if (TextUtils.isEmpty(searchDeliverersRequest.getItemID())) {
                ViewUtils.getSnackbar(getActivity(), "Ne almak istediğini bilmeden sana yardımcı olamam :)").show();
            } else {
                steppersView.nextStep();
            }
        });
        return stepFirst;
    }

    @NonNull
    private SteppersItem getStepSecond(AutocompleteFilter typeFilter) {
        SteppersItem stepSecond = new SteppersItem();
        stepSecond.setLabel(getString(R.string.select_from_city_label));
        stepSecond.setSubLabel(getString(R.string.select_city_description));

        SupportPlaceAutocompleteFragment fragmentFrom = new SupportPlaceAutocompleteFragment();
        fragmentFrom.setFilter(typeFilter);
        fragmentFrom.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                searchDeliverersRequest.setFromLocationID(place.getId());
            }

            @Override
            public void onError(Status status) {
                searchDeliverersRequest.setFromLocationID(null);
            }
        });
        stepSecond.setFragment(fragmentFrom);
        stepSecond.setOnClickContinue(() -> {
            if (TextUtils.isEmpty(searchDeliverersRequest.getFromLocationID())) {
                ViewUtils.getSnackbar(getActivity(), "Nereden almak istediğini bilmeden sana yardımcı olamam :)").show();
            } else {
                steppersView.nextStep();
            }
        });
        return stepSecond;
    }

    @NonNull
    private SteppersItem getStepThird(AutocompleteFilter typeFilter) {
        SteppersItem stepThird = new SteppersItem();
        stepThird.setLabel(getString(R.string.select_to_city_label));
        stepThird.setSubLabel(getString(R.string.select_city_description));

        SupportPlaceAutocompleteFragment fragmentTo = new SupportPlaceAutocompleteFragment();
        fragmentTo.setFilter(typeFilter);
        fragmentTo.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                searchDeliverersRequest.setToLocationID(place.getId());
            }

            @Override
            public void onError(Status status) {
                searchDeliverersRequest.setToLocationID(null);
            }
        });
        stepThird.setFragment(fragmentTo);
        stepThird.setOnClickContinue(() -> {
            if (TextUtils.isEmpty(searchDeliverersRequest.getToLocationID())) {
                ViewUtils.getSnackbar(getActivity(), "Nereye almak istediğini bilmeden sana yardımcı olamam :)").show();
            } else {
                steppersView.nextStep();
            }
        });
        return stepThird;
    }

    @NonNull
    private SteppersItem getStepFourth() {
        SteppersItem stepFourth = new SteppersItem();
        stepFourth.setLabel(getString(R.string.select_time_label));
        stepFourth.setSubLabel(getString(R.string.select_time_description));
        DateRangeFragment dateRangeFragment = DateRangeFragment.newInstance();
        stepFourth.setFragment(dateRangeFragment);
        onFinishAction = () -> {
            SelectedDate date = dateRangeFragment.getDate();
            searchDeliverersRequest.setStartDate(DateUtils.formatDateToString(date.getStartDate()));
            searchDeliverersRequest.setEndDate(DateUtils.formatDateToString(date.getEndDate()));

            if (TextUtils.isEmpty(searchDeliverersRequest.getStartDate()) ||
                    TextUtils.isEmpty(searchDeliverersRequest.getEndDate())) {
                ViewUtils.getSnackbar(getActivity(), "Ne zaman almak istediğini bilmeden sana yardımcı olamam :)").show();
            } else {
                sendFilterRequest();
            }
        };
        return stepFourth;
    }

    private void sendFilterRequest() {
        FilterTask.getInstance().fetch(searchDeliverersRequest, (status, message) -> {
            if (status==FilterTask.SUCCESS){
                SelectDelivererActivity.start(getContext());
                getActivity().finish();
            } else {
                ViewUtils.showAlertDialog(getContext(), message, (dialog, which) -> getActivity().finish());
            }
        });
    }
}
