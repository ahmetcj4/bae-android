
package com.kurye.kurye.entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CreateOrderRequest {

    @SerializedName("delivererID")
    @Expose
    private String delivererID;
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("itemID")
    @Expose
    private String itemId;
    @SerializedName("deliveryDate")
    @Expose
    private String deliveryDate;

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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

}