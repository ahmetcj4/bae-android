package com.kurye.kurye.screen.show;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kurye.kurye.R;
import com.kurye.kurye.entity.response.ItemEntity;
import com.kurye.kurye.entity.response.OrderEntity;
import com.kurye.kurye.screen.filter.FilterActivity;
import com.kurye.kurye.task.ItemTask;
import com.kurye.kurye.task.OrderTask;
import com.kurye.kurye.viewEntity.OrderVM;
import com.ramotion.cardslider.CardSliderLayoutManager;

import java.util.List;

/**
 * Created by ahmet on 2/18/2018.
 */

public class VMShowActivity extends AndroidViewModel {
    private RecyclerView.OnScrollListener scrollListener;
    private ObservableArrayList<OrderVM> orders = new ObservableArrayList<>();
    private ObservableInt position = new ObservableInt(-1);
    public VMShowActivity(@NonNull Application application) {
        super(application);
        scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    position.set(
                            ((CardSliderLayoutManager) recyclerView.getLayoutManager())
                                    .getActiveCardPosition());
                }
            }
        };
        ItemTask.getInstance().fetch((status, message) -> {
            if (status == ItemTask.SUCCESS) {
                OrderTask.getInstance().get("5a8950f45b9fa82aa90664ff", (status2, message2) -> {
                    if (status2 == OrderTask.SUCCESS) {
                        List<OrderEntity> load = OrderTask.getInstance().load();
                        for (OrderEntity orderEntity : load) {
                            ItemEntity itemEntity = orderEntity.getItemEntity();
                            if (itemEntity != null) {
                                OrderVM orderVM = new OrderVM(
                                        itemEntity.getName(),
                                        itemEntity.getName(),
                                        itemEntity.getPrice() + " Dollars\n " +
                                                itemEntity.getVolume() + " lt",
                                        orderEntity.getCustomerID(),
                                        R.drawable.p1);

                                orders.add(orderVM);
                            }

                        }
                        if (!orders.isEmpty()) {
                            position.set(0);
                        }
                    }
                });
            }
        });
    }

    public ObservableArrayList<OrderVM> getOrders() {
        return orders;
    }

    public void performAdd(View view) {
        FilterActivity.start(view.getContext());
    }

    public RecyclerView.OnScrollListener getScrollListener() {
        return scrollListener;
    }

    public ObservableInt getPosition() {
        return position;
    }
}
