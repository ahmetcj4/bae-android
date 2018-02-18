package com.kurye.kurye.network;


import com.kurye.kurye.entity.request.CreateOrderRequest;
import com.kurye.kurye.entity.request.SearchDeliverersRequest;
import com.kurye.kurye.entity.response.BaseResponse;
import com.kurye.kurye.entity.response.DelivererEntity;
import com.kurye.kurye.entity.response.ItemEntity;
import com.kurye.kurye.entity.response.OrderEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Basic retrofit service interface.
 *
 * No magical stuff here..
 * Created by ahmet on 2/4/2018.
 */
public interface NetworkService {

    @GET(NetworkConstants.ITEMS)
    Call<BaseResponse<List<ItemEntity>>> getItems();

    @POST(NetworkConstants.SEARCH_DELIVERERS)
    Call<BaseResponse<List<DelivererEntity>>> searchDeliverers(@Body SearchDeliverersRequest searchDeliverersRequest);

    @POST(NetworkConstants.CREATE_ORDER)
    Call<BaseResponse<OrderEntity>> createOrder(@Body CreateOrderRequest createOrderRequest);

    @GET(NetworkConstants.ORDERS + "{customerID}")
    Call<BaseResponse<List<OrderEntity>>> getOrders(@Path("customerID") String customerId);
}