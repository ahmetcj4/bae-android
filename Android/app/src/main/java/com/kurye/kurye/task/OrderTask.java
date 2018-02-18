package com.kurye.kurye.task;

import android.support.annotation.NonNull;

import com.kurye.kurye.entity.request.CreateOrderRequest;
import com.kurye.kurye.entity.response.BaseResponse;
import com.kurye.kurye.entity.response.OrderEntity;
import com.kurye.kurye.network.NetworkApi;
import com.kurye.kurye.network.NetworkCallback;

import java.util.List;

/**
 * Order interacter. Used for managing order.
 * <p>
 * Created by elifguler on 18.02.2018.
 */

public class OrderTask extends Task {
    private static OrderTask instance;
    private OrderEntity order;
    private List<OrderEntity> orders;

    public static OrderTask getInstance() {
        if (instance == null) {
            instance = new OrderTask();
        }
        return instance;
    }

    public List<OrderEntity> load() {
        return orders;
    }

    public void create(CreateOrderRequest createOrderRequest, @NonNull OnResultListener listener) {
        NetworkApi.getInstance().createOrder(createOrderRequest, new NetworkCallback<BaseResponse<OrderEntity>>() {
            @Override
            public void onSuccess(BaseResponse<OrderEntity> response) {
                if (response == null || response.getCode() != 200) {
                    listener.onResult(FAIL, "No Matching Results");
                } else {
                    order = response.getData();
                    listener.onResult(SUCCESS, null);
                }
            }

            @Override
            public void onServiceFailure(int httpResponseCode, String message) {
                listener.onResult(FAIL, message);
            }

            @Override
            public void onNetworkFailure(Throwable message) {
                listener.onResult(FAIL, "Unknown Error");
            }
        });
    }

    public void get(String customerId, @NonNull OnResultListener listener) {
        NetworkApi.getInstance().getOrders(customerId, new NetworkCallback<BaseResponse<List<OrderEntity>>>() {
            @Override
            public void onSuccess(BaseResponse<List<OrderEntity>> response) {
                if (response == null || response.getCode() != 200 || response.getData() == null || response.getData().isEmpty()) {
                    listener.onResult(FAIL, "No Matching Results");
                } else {
                    orders = response.getData();
                    listener.onResult(SUCCESS, null);
                }
            }

            @Override
            public void onServiceFailure(int httpResponseCode, String message) {
                listener.onResult(FAIL, message);
            }

            @Override
            public void onNetworkFailure(Throwable message) {
                listener.onResult(FAIL, "Unknown Error");
            }
        });
    }
}
