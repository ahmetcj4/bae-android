package com.kurye.kurye.entity.response;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kurye.kurye.task.ItemTask;

import java.util.Date;

public class OrderEntity {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("delivererID")
    @Expose
    private String delivererID;
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("deliveryDate")
    @Expose
    private Date deliveryDate;
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;
    @SerializedName("itemID")
    @Expose
    private String itemEntity;
    @SerializedName("__v")
    @Expose
    private int v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDelivererID() {
        return delivererID;
    }

    public void setDelivererID(String delivererID) {
        this.delivererID = delivererID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public ItemEntity getItemEntity() {
        for (ItemEntity entity : ItemTask.getInstance().load()) {
            if (TextUtils.equals(entity.getId(), itemEntity)) {
                return entity;
            }
        }
        return null;
    }

    public void setItemEntity(String itemEntity) {
        this.itemEntity = itemEntity;
    }
}