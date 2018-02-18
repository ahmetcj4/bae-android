package com.kurye.kurye.screen.selectDeliverer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kurye.kurye.R;
import com.kurye.kurye.common.ViewUtils;
import com.kurye.kurye.common.view.TextViewFactory;
import com.kurye.kurye.databinding.FragmentSelectDelivererBinding;
import com.kurye.kurye.task.OrderTask;

/**
 * Created by elifguler on 18.02.2018.
 */

public class SelectDelivererActivityFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;

    private TextSwitcher placeSwitcher;
    private TextSwitcher clockSwitcher;
    private TextSwitcher descriptionsSwitcher;

    private TextView country1TextView;
    private TextView country2TextView;
    private int countryOffset1;
    private int countryOffset2;
    private long countryAnimDuration;
    private int currentPosition;
    private Marker currentMarker;
    private FragmentSelectDelivererBinding binding;
    private VMSelectDeliverer vmSelectDeliverer;

    public SelectDelivererActivityFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_deliverer, container, false);
        vmSelectDeliverer = ViewModelProviders.of(this).get(VMSelectDeliverer.class);
        binding.setVmSelectDeliverer(vmSelectDeliverer);
        vmSelectDeliverer.getPosition().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                onActiveCardChange(vmSelectDeliverer.getPosition().get());
            }
        });
        initMap();
        vmSelectDeliverer.getCommunication().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (vmSelectDeliverer.getCommunication().get() == OrderTask.SUCCESS) {
                    ViewUtils.showAlertDialog(getActivity(), "Success", (dialog, which) -> getActivity().finish());
                } else {
                    ViewUtils.showAlertDialog(getActivity(), vmSelectDeliverer.getError().get(), (dialog, which) -> getActivity().finish());
                }
            }
        });
        if (!vmSelectDeliverer.getDeliverers().isEmpty()){
            initCountryText();
            initSwitchers();
        }
        binding.executePendingBindings();
        return binding.getRoot();
    }

    private void onActiveCardChange(int pos) {
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }

        int animV[] = new int[]{R.anim.slide_in_top, R.anim.slide_out_bottom};
        boolean left2right = pos < currentPosition;
        if (left2right) {
            animV[0] = R.anim.slide_in_bottom;
            animV[1] = R.anim.slide_out_top;
        }

        setCountryText(vmSelectDeliverer.getDeliverers().get(pos).getCountry().get(), left2right);

        placeSwitcher.setInAnimation(getContext(), animV[0]);
        placeSwitcher.setOutAnimation(getContext(), animV[1]);
        placeSwitcher.setText(vmSelectDeliverer.getDeliverers().get(pos).getName().get());

        clockSwitcher.setInAnimation(getContext(), animV[0]);
        clockSwitcher.setOutAnimation(getContext(), animV[1]);
        clockSwitcher.setText(vmSelectDeliverer.getDeliverers().get(pos).getTime().get());

        descriptionsSwitcher.setText(vmSelectDeliverer.getDeliverers().get(pos).getDescription().get());

        showMap(Math.random() * 180 - 90, Math.random() * 180 - 90);

        currentPosition = pos;

    }

    private void setCountryText(String text, boolean left2right) {
        final TextView invisibleText;
        final TextView visibleText;
        if (country1TextView.getAlpha() > country2TextView.getAlpha()) {
            visibleText = country1TextView;
            invisibleText = country2TextView;
        } else {
            visibleText = country2TextView;
            invisibleText = country1TextView;
        }

        final int vOffset;
        if (left2right) {
            invisibleText.setX(0);
            vOffset = countryOffset2;
        } else {
            invisibleText.setX(countryOffset2);
            vOffset = 0;
        }

        invisibleText.setText(text);

        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);
        final ObjectAnimator iX = ObjectAnimator.ofFloat(invisibleText, "x", countryOffset1);
        final ObjectAnimator vX = ObjectAnimator.ofFloat(visibleText, "x", vOffset);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha, iX, vX);
        animSet.setDuration(countryAnimDuration);
        animSet.start();

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initSwitchers() {

        placeSwitcher = binding.tsPlace;
        placeSwitcher.setFactory(new TextViewFactory(getContext(), R.style.PlaceTextView, false));
        placeSwitcher.setCurrentText(vmSelectDeliverer.getDeliverers().get(0).getName().get());

        clockSwitcher = binding.tsClock;
        clockSwitcher.setFactory(new TextViewFactory(getContext(), R.style.ClockTextView, false));
        clockSwitcher.setCurrentText(vmSelectDeliverer.getDeliverers().get(0).getTime().get());

        descriptionsSwitcher = binding.tsDescription;
        descriptionsSwitcher.setInAnimation(getContext(), android.R.anim.fade_in);
        descriptionsSwitcher.setOutAnimation(getContext(), android.R.anim.fade_out);
        descriptionsSwitcher.setFactory(new TextViewFactory(getContext(), R.style.DescriptionTextView, false));
        descriptionsSwitcher.setCurrentText(vmSelectDeliverer.getDeliverers().get(0).getDescription().get());
    }

    private void initCountryText() {
        countryAnimDuration = getResources().getInteger(R.integer.labels_animation_duration);
        countryOffset1 = getResources().getDimensionPixelSize(R.dimen.left_offset);
        countryOffset2 = getResources().getDimensionPixelSize(R.dimen.card_width);
        country1TextView = (TextView) binding.tvCountry1;
        country2TextView = (TextView) binding.tvCountry2;

        country1TextView.setX(countryOffset1);
        country2TextView.setX(countryOffset2);
        country1TextView.setText(vmSelectDeliverer.getDeliverers().get(0).getCountry().get());
        country2TextView.setAlpha(0f);

    }

    private void showMap(double latitude, double longitude) {
        LatLng newPlace = new LatLng(latitude, longitude);
        if (currentMarker != null) {
            currentMarker.remove();
        }
        MarkerOptions position = new MarkerOptions()
                .position(newPlace)
                .anchor(0, -1.0f);
        currentMarker = mMap.addMarker(position);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newPlace, 7));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
