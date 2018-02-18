package com.kurye.kurye.common.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.List;

public class StaticDataBindingAdapters {
    @BindingAdapter("initWithRange")
    public static void initializePicker(SublimeDatePicker p, boolean canPickRange) {
        p.init(null, canPickRange, null);
    }

    @BindingAdapter(value = {"itemList", "itemLayout","scrollListener"}, requireAll = true)
    public static <T> void createAdapter(RecyclerView rv, List<T> items, int itemLayout, RecyclerView.OnScrollListener scrollListener) {
        if (rv.getAdapter() == null) {
            rv.setAdapter(new BaseAdapter<T>(items) {
                @Override
                protected T getObjForPosition(int position) {
                    return itemList.get(position);
                }

                @Override
                protected int getLayoutIdForPosition(int position) {
                    return itemLayout;
                }

                @Override
                public int getItemCount() {
                    return itemList == null ? 0 : itemList.size();
                }
            });
            rv.addOnScrollListener(scrollListener);
            new CardSnapHelper().attachToRecyclerView(rv);

        } else {
            ((BaseAdapter<T>) rv.getAdapter()).updateData(items);
        }
    }

    @BindingAdapter("divider")
    public static void addDivider(RecyclerView rv, DividerItemDecoration itemDecoration) {
        rv.addItemDecoration(itemDecoration);
    }

    @BindingAdapter("textAdapter")
    public static void addTextAdapter(AutoCompleteTextView autoCompleteTextView, ArrayAdapter adapter) {
        autoCompleteTextView.setAdapter(adapter);
    }
}