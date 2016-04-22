package com.nomad.cuppcebe.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kaplanfatt on 27/11/15.
 */
public class Point {

    @SerializedName("Description")
    public String Description;

    @SerializedName("EarningStatus")
    public int EarningStatus;

    @SerializedName("EarningType")
    public int EarningType;

    @SerializedName("Point")
    public int Point;

    @SerializedName("PointExpireDate")
    public String PointExpireDate;

    @SerializedName("PointGainDate")
    public String PointGainDate;

    @SerializedName("PointSpentDate")
    public String PointSpentDate;

    @SerializedName("ProcessId")
    public int ProcessId;

    @SerializedName("ShopId")
    public int ShopId;

    @SerializedName("ShopName")
    public String ShopName;


}
