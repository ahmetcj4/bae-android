package com.kurye.kurye.screen.show;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kurye.kurye.R;
import com.kurye.kurye.databinding.ActivityShowBinding;
import com.kurye.kurye.screen.filter.FilterActivity;
import com.kurye.kurye.viewEntity.OrderVM;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

public class ShowActivity extends AppCompatActivity implements OnMapReadyCallback {
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
    private VMShowActivity vmShowActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityShowBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_show);

        vmShowActivity = ViewModelProviders.of(this).get(VMShowActivity.class);
        binding.setVmShow(vmShowActivity);
        binding.executePendingBindings();
        initMap();
        initRecyclerView();
        initCountryText();
        initSwitchers();

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange(((CardSliderLayoutManager)
                            recyclerView.getLayoutManager()).getActiveCardPosition());
                }
            }
        });

        new CardSnapHelper().attachToRecyclerView(recyclerView);
    }

    private void initSwitchers() {

        placeSwitcher = (TextSwitcher) findViewById(R.id.ts_place);
        placeSwitcher.setFactory(new TextViewFactory(R.style.PlaceTextView, false));
        placeSwitcher.setCurrentText(vmShowActivity.getOrders().get(0).getPlace().get());

        clockSwitcher = (TextSwitcher) findViewById(R.id.ts_clock);
        clockSwitcher.setFactory(new TextViewFactory(R.style.ClockTextView, false));
        clockSwitcher.setCurrentText(vmShowActivity.getOrders().get(0).getTime().get());

        descriptionsSwitcher = (TextSwitcher) findViewById(R.id.ts_description);
        descriptionsSwitcher.setInAnimation(this, android.R.anim.fade_in);
        descriptionsSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        descriptionsSwitcher.setFactory(new TextViewFactory(R.style.DescriptionTextView, false));
        descriptionsSwitcher.setCurrentText(vmShowActivity.getOrders().get(0).getDescription().get());
    }

    private void initCountryText() {
        countryAnimDuration = getResources().getInteger(R.integer.labels_animation_duration);
        countryOffset1 = getResources().getDimensionPixelSize(R.dimen.left_offset);
        countryOffset2 = getResources().getDimensionPixelSize(R.dimen.card_width);
        country1TextView = (TextView) findViewById(R.id.tv_country_1);
        country2TextView = (TextView) findViewById(R.id.tv_country_2);

        country1TextView.setX(countryOffset1);
        country2TextView.setX(countryOffset2);
        country1TextView.setText(vmShowActivity.getOrders().get(0).getCountry().get());
        country2TextView.setAlpha(0f);

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

        setCountryText(vmShowActivity.getOrders().get(pos).getCountry().get(), left2right);

        placeSwitcher.setInAnimation(ShowActivity.this, animV[0]);
        placeSwitcher.setOutAnimation(ShowActivity.this, animV[1]);
        placeSwitcher.setText(vmShowActivity.getOrders().get(pos).getPlace().get());

        clockSwitcher.setInAnimation(ShowActivity.this, animV[0]);
        clockSwitcher.setOutAnimation(ShowActivity.this, animV[1]);
        clockSwitcher.setText(vmShowActivity.getOrders().get(pos).getTime().get());

        descriptionsSwitcher.setText(vmShowActivity.getOrders().get(pos).getDescription().get());

        showMap(Math.random() * 180 - 90, Math.random() * 180 - 90);

        currentPosition = pos;
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

    private class TextViewFactory implements ViewSwitcher.ViewFactory {

        @StyleRes
        final int styleId;
        final boolean center;

        TextViewFactory(@StyleRes int styleId, boolean center) {
            this.styleId = styleId;
            this.center = center;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View makeView() {
            final TextView textView = new TextView(ShowActivity.this);

            if (center) {
                textView.setGravity(Gravity.CENTER);
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(ShowActivity.this, styleId);
            } else {
                textView.setTextAppearance(styleId);
            }

            return textView;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

}
