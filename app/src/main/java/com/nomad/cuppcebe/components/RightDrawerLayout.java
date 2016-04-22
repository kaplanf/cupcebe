package com.nomad.cuppcebe.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nomad.cuppcebe.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.drawer_right_layout)
public class RightDrawerLayout extends RelativeLayout {

    @ViewById(R.id.rightDrawerLogOut)
    public TextView rightDrawerLogOut;

    @ViewById(R.id.rightDrawerProfile)
    public TextView rightDrawerProfile;

    @ViewById(R.id.slidingmenuFavLayout)
    public RelativeLayout slidingMenuFavLayout;

    public RightDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
