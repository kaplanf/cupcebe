package com.nomad.cuppcebe.util;

/**
 * Created by kaplanfatt on 19/09/15.
 */
public class NetworkConstants {

    public static final String BASE_URL = "http://api.cuppcebe.nmdapps.com/Service.svc/rest/";
    public static final String LOGIN = "LoginUser?email={Email}&password={Password}&facebookId={FacebookId}&twitterId={TwitterId}";
    public static final String REGISTER = "RegisterUser?name={Name}&surname={Surname}&" +
            "birthdate={Birthdate}&email={Email}&password={Password}&facebookId=" +
            "{FacebookId}&twitterId={TwitterId}&phoneNumber={PhoneNumber}&cityCode=" +
            "{CityCode}&gender={Gender}&invitationCode={InvitationCode}";

    public static final String SHOP_LIST = "GetShopList?tokenKey={TokenKey}&userId={UserId}&shopName={ShopName}&latitude=" +
            "{Latitude}&longitude={Longitude}&pageSize={PageSize}&pageNum={PageNum}";

    public static final String CAMPAIGN_LIST = "GetShopsProductCampaigns?tokenKey={tokenKey}&userId={userId}&shopId={shopId}&pageNum={pageNum}&pagesize={pageSize}";

    public static final String ADD_FAVORITE = "AddUsersFavorites?tokenKey={tokenKey}&userId={userId}&favoriteId={favoriteId}&shopId={shopId}&favoriteType={favoriteType}";

    public static final String ADD_WISH = "AddUsersWishList";

    public static final String GET_WISH_LIST = "GetUsersWishList?tokenKey={tokenKey}&userId={userId}";

    public static final String GET_USER_INFO = "GetUser?tokenKey={tokenKey}&userId={userId}";

    public static final String GET_FAVORITES = "GetUsersFavoriteShops?tokenKey={tokenKey}&userId={userId}";

    public static final String GET_POINT_HISTORY = "GetUsersPointHistory?tokenKey={tokenKey}&userId={userId}&pageNum=5&pageSize=1";
}


