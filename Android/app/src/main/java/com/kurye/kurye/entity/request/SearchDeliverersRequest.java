
package com.kurye.kurye.entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchDeliverersRequest {

    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("fromLocationID")
    @Expose
    private String fromLocationID;
    @SerializedName("toLocationID")
    @Expose
    private String toLocationID;
    @SerializedName("itemID")
    @Expose
    private String itemID;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFromLocationID() {
        return fromLocationID;
    }

    public void setFromLocationID(String fromLocationID) {
        this.fromLocationID = fromLocationID;
    }

    public String getToLocationID() {
        return toLocationID;
    }

    public void setToLocationID(String toLocationID) {
        this.toLocationID = toLocationID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

}