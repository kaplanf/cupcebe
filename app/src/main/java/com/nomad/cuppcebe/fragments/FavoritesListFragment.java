package com.nomad.cuppcebe.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nomad.cuppcebe.CuppCebeApplication;
import com.nomad.cuppcebe.MainActivity;
import com.nomad.cuppcebe.R;
import com.nomad.cuppcebe.components.SquareImageView;
import com.nomad.cuppcebe.interfaces.OnMainFragmentListener;
import com.nomad.cuppcebe.model.FavoriteResult;
import com.nomad.cuppcebe.model.Shop;
import com.nomad.cuppcebe.restful.RestClient;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;

/**
 * Created by kaplanfatt on 27/11/15.
 */

@EFragment(R.layout.favorites_list_fragment)
public class FavoritesListFragment extends BaseFragment {

    @ViewById(R.id.favoritesFragmentList)
    ListView favoritesFragmentList;

    OnMainFragmentListener mainFragmentListener;

    @RestService
    protected RestClient restClient;

    private ArrayList<Shop> favoriteShopList;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainFragmentListener = (OnMainFragmentListener) activity;
        ((MainActivity) getActivity()).showOrHideBottomContainer(1);
    }

    @AfterViews
    protected void afterViews() {
        showCuppCebeProgress();
        initActionBar();
        getFavorites();
    }

    @Background
    protected void getFavorites() {
        favoriteShopList = restClient.getFavorites(getPreferences().getString(CuppCebeApplication.getInstance().CUPPCEBE_TOKEN_KEY, ""),
                Integer.toString(getPreferences().getInt(CuppCebeApplication.getInstance().USER_ID, 0)));
        hideCuppCebeProgress();
        if (favoriteShopList.size() > 0) {

            for (int a = 0; a < favoriteShopList.size(); a++) {
                favoriteShopList.get(a).isFavorite = true;
            }
            initUI(favoriteShopList);

        } else {

        }
    }


    @Background
    public void addFavorite(int shopId) {
        FavoriteResult result = restClient.addFavorite(getPreferences().getString(CuppCebeApplication.getInstance().CUPPCEBE_TOKEN_KEY, ""),
                Integer.toString(getPreferences().getInt(CuppCebeApplication.getInstance().USER_ID, 0)),
                Integer.toString(shopId), Integer.toString(shopId), "1");
    }

    @UiThread
    protected void initUI(ArrayList<Shop> shopList) {
        ShopListAdapter adapter = new ShopListAdapter(getActivity(), R.layout.shop_list_row, shopList);
        favoritesFragmentList.setAdapter(adapter);
    }

    class ShopListAdapter extends ArrayAdapter<Shop> {

        private ArrayList<Shop> items;
        private Context context;
        private MainActivity activity;

//        @RestService
//        protected RestClient restClient;

        public ShopListAdapter(Context context, int textViewResourceId, ArrayList<Shop> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
            this.activity = (MainActivity) context;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            final ViewHolder holder;

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.shop_list_row, null);

                holder = new ViewHolder();
                holder.text = (TextView) v.findViewById(R.id.shopListText);
                holder.image = (SquareImageView) v.findViewById(R.id.shopListImage);
                holder.desc = (TextView) v.findViewById(R.id.shopListItemDesc);
                holder.favoriteButton = (ImageView) v.findViewById(R.id.shopListItemFavoriteButton);

                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            final Shop shop = items.get(position);
            if (shop != null) {
                holder.text.setText(shop.Name);
                holder.desc.setText(shop.Slogan);
                if (shop.Logo != null && !shop.Logo.equals("")) {
//                    if (shop.Logo.endsWith(".png")) {
                    Picasso.with(context).load(shop.Logo).into(holder.image);

//                    }
                }


                if (shop.isFavorite) {
                    holder.favoriteButton.setSelected(true);
                } else {
                    holder.favoriteButton.setSelected(false);
                }

                holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.favoriteButton.isSelected()) {
                            addFavorite(shop.ShopId);
                            shop.isFavorite = false;
                            notifyDataSetChanged();

                        } else {
                            addFavorite(shop.ShopId);
                            shop.isFavorite = true;
                            notifyDataSetChanged();
                        }
                    }
                });

            }
            return v;
        }


        class ViewHolder {
            TextView text;
            TextView desc;
            SquareImageView image;
            ImageView favoriteButton;
        }
    }

    @UiThread
    public void initActionBar() {

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
        View customActionBarView = getActivity().getLayoutInflater().inflate(R.layout.custom_action_bar_favorite_list, null);
        ActionBar actionbar = ((MainActivity) getActivity()).getSupportActionBar();

        actionbar.setDefaultDisplayHomeAsUpEnabled(true);
        actionbar.setCustomView(customActionBarView, layoutParams);
        actionbar.setDisplayShowCustomEnabled(true);

    }

}
