package com.nomad.cuppcebe.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kaplanfatt on 19/09/15.
 */
public class UserResponse {

    @SerializedName("User")
    public User user;

    @SerializedName("TokenKey")
    public String TokenKey;

    public class User {

        @SerializedName("Birthdate")
        public String Birthdate;

        @SerializedName("CityCode")
        public String CityCode;

        @SerializedName("CityName")
        public String CityName;

        @SerializedName("Email")
        public String Email;

        @SerializedName("FacebookId")
        public int FacebookId;

        @SerializedName("Gender")
        public int Gender;

        @SerializedName("InvitationCode")
        public String InvitationCode;

        @SerializedName("Name")
        public String Name;

        @SerializedName("OverAllPoint")
        public int OverAllPoint;

        @SerializedName("PhoneNumber")
        public String PhoneNumber;

        @SerializedName("Surname")
        public String Surname;

        @SerializedName("TwitterId")
        public int TwitterId;

        @SerializedName("UserId")
        public int UserId;

        @SerializedName("UserImagePath")
        public String UserImagePath;
    }

}
