package com.nomad.cuppcebe.restful;

import com.nomad.cuppcebe.model.Campaign;
import com.nomad.cuppcebe.model.FavoriteResult;
import com.nomad.cuppcebe.model.Point;
import com.nomad.cuppcebe.model.Shop;
import com.nomad.cuppcebe.model.UserResponse;
import com.nomad.cuppcebe.model.Wish;
import com.nomad.cuppcebe.model.WishResult;
import com.nomad.cuppcebe.util.NetworkConstants;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kaplanfatt on 19/09/15.
 */

@Rest(rootUrl = NetworkConstants.BASE_URL, converters = {GsonHttpMessageConverter.class, StringHttpMessageConverter.class})
public interface RestClient extends RestClientHeaders {

    @Get(NetworkConstants.LOGIN)
    UserResponse login(String Email, String Password, int FacebookId, int TwitterId);

    @Get(NetworkConstants.REGISTER)
    UserResponse register(String Name, String Surname, Date Birthdate, String Email, String Password, int FacebookId, int TwitterId, String PhoneNumber, String CityCode, int Gender, String InvitationCode);

    @Get(NetworkConstants.SHOP_LIST)
    ArrayList<Shop> getShopList(String TokenKey, int UserId, String ShopName, double Latitude, double Longitude, int PageSize, int PageNum);

    @Get(NetworkConstants.CAMPAIGN_LIST)
    ArrayList<Campaign> getCampaignList(String tokenKey, int userId, int shopId, int pageNum, int pageSize);

    @Post(NetworkConstants.ADD_WISH)
    WishResult addWish(Wish wish);

    @Get(NetworkConstants.GET_WISH_LIST)
    ArrayList<Wish> getWishList(String tokenKey, String userId);

    @Get(NetworkConstants.ADD_FAVORITE)
    FavoriteResult addFavorite(String tokenKey, String userId, String favoriteId, String shopId, String favoriteType);

    @Get(NetworkConstants.GET_USER_INFO)
    UserResponse.User getUserInfo(String tokenKey, String userId);

    @Get(NetworkConstants.GET_FAVORITES)
    ArrayList<Shop> getFavorites(String tokenKey, String userId);

    @Get(NetworkConstants.GET_POINT_HISTORY)
    ArrayList<Point> getPointHistory(String tokenKey, String userId);

}

