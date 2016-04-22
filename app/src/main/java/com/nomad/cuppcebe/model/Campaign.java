package com.nomad.cuppcebe.model;


import com.google.gson.annotations.SerializedName;

public class Campaign {

    @SerializedName("CampaignId")
    public int CampaignId;

    @SerializedName("ImagePath")
    public String ImagePath;

    @SerializedName("IsEnableForUser")
    public boolean IsEnableForUser;

    @SerializedName("Point")
    public int Point;

    @SerializedName("ProductCode")
    public String ProductCode;

    @SerializedName("ProductName")
    public String ProductName;

    @SerializedName("ShopId")
    public int ShopId;

    @SerializedName("ShopName")
    public String ShopName;

}
