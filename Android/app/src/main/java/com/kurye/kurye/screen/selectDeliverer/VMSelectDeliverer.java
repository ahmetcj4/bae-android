package com.kurye.kurye.screen.selectDeliverer;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kurye.kurye.entity.request.CreateOrderRequest;
import com.kurye.kurye.entity.request.SearchDeliverersRequest;
import com.kurye.kurye.entity.response.DelivererEntity;
import com.kurye.kurye.task.FilterTask;
import com.kurye.kurye.task.OrderTask;
import com.kurye.kurye.viewEntity.DelivererVM;
import com.ramotion.cardslider.CardSliderLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmet on 2/18/2018.
 */

public class VMSelectDeliverer extends AndroidViewModel {
    private RecyclerView.OnScrollListener scrollListener;
    private ObservableArrayList<DelivererVM> deliverers = new ObservableArrayList<>();
    private ObservableInt position = new ObservableInt();
    private ObservableInt communication = new ObservableInt(-1);
    private ObservableField<String> error = new ObservableField<>();

    public VMSelectDeliverer(@NonNull Application application) {
        super(application);
        List<DelivererEntity> load = FilterTask.getInstance().load();
        List<DelivererVM> list = new ArrayList<>();
        if (load==null||load.isEmpty()) {
            return;
        }
        DelivererVM vm = new DelivererVM(load.get(0).getFirstName() + " " + load.get(0).getLastName(), load.get(0).getRoute(), load.get(0).getId());
        list.add(vm);
        deliverers.addAll(list);
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
    }

    public RecyclerView.OnScrollListener getScrollListener() {
        return scrollListener;
    }

    public ObservableArrayList<DelivererVM> getDeliverers() {
        return deliverers;
    }

    public ObservableInt getPosition() {
        return position;
    }

    public void performChoose(View view) {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        SearchDeliverersRequest searchDeliverersRequest = FilterTask.getInstance().getSearchDeliverersRequest();
        createOrderRequest.setItemId(searchDeliverersRequest.getItemID());
        createOrderRequest.setDeliveryDate(FilterTask.getInstance().load().get(0).getDeliveryDate());
        createOrderRequest.setCustomerID("5a8950f45b9fa82aa90664ff");
        createOrderRequest.setDelivererID(deliverers.get(0).getId().get());
        OrderTask.getInstance().create(createOrderRequest, (status, message) -> {
            communication.set(status);
        });
    }

    public ObservableInt getCommunication() {
        return communication;
    }

    public ObservableField<String> getError() {
        return error;
    }
}
