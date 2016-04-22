package com.nomad.cuppcebe.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kaplanfatt on 05/10/15.
 */
public class Shop {

    @SerializedName("CanCheckIn")
    public boolean CanCheckIn;

    @SerializedName("CanbeScanProduct")
    public boolean CanbeScanProduct;

    @SerializedName("Distance")
    public double Distance;

    @SerializedName("Latitude")
    public double Latitude;

    @SerializedName("Logo")
    public String Logo;

    @SerializedName("Longitude")
    public double Longitude;

    @SerializedName("Name")
    public String Name;

    @SerializedName("ShopId")
    public int ShopId;

    @SerializedName("Slogan")
    public String Slogan;

    public boolean isFavorite;
}
