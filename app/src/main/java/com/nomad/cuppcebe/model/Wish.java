package com.nomad.cuppcebe.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kaplanfatt on 23/11/15.
 */
public class Wish {

    @SerializedName("tokenKey")
    public String tokenKey;

    @SerializedName("userId")
    public String userId;

    @SerializedName("fsId")
    public String fsId;

    @SerializedName("geoTagName")
    public String geoTagName;

    @SerializedName("relatedGeoTags")
    public String[] relatedGeoTags;
}
