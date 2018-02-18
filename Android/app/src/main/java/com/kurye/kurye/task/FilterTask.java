package com.kurye.kurye.task;

import android.support.annotation.NonNull;

import com.kurye.kurye.entity.request.SearchDeliverersRequest;
import com.kurye.kurye.entity.response.BaseResponse;
import com.kurye.kurye.entity.response.DelivererEntity;
import com.kurye.kurye.network.NetworkApi;
import com.kurye.kurye.network.NetworkCallback;

import java.util.List;


/**
 * Deliverer interacter. Used for managing deliverer data.
 * <p>
 * Created by ahmet on 2/4/2018.
 */
public class FilterTask extends Task{

    private static FilterTask instance;
    private List<DelivererEntity> deliverers;
    private SearchDeliverersRequest searchDeliverersRequest;

    public static FilterTask getInstance() {
        if (instance == null) {
            instance = new FilterTask();
        }
        return instance;
    }

    public List<DelivererEntity> load() {
        return deliverers;
    }

    public void fetch(SearchDeliverersRequest searchDeliverersRequest, @NonNull OnResultListener listener) {
        this.searchDeliverersRequest = searchDeliverersRequest;
        NetworkApi.getInstance().getDeliverers(searchDeliverersRequest, new NetworkCallback<BaseResponse<List<DelivererEntity>>>() {
            @Override
            public void onSuccess(BaseResponse<List<DelivererEntity>> response) {
                if (response == null || response.getCode() != 200 || response.getData() == null || response.getData().isEmpty()) {
                    listener.onResult(FAIL, "No Matching Results");
                } else {
                    deliverers = response.getData();
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

    public boolean itemsIsEmpty() {
        return deliverers == null || deliverers.isEmpty();
    }

    public SearchDeliverersRequest getSearchDeliverersRequest() {
        return searchDeliverersRequest;
    }
}
