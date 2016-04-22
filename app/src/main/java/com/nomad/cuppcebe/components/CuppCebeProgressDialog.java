package com.nomad.cuppcebe.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.nomad.cuppcebe.R;

/**
 * Created by kaplanfatt
 */
public class CuppCebeProgressDialog extends Dialog {

    private static CuppCebeProgressDialog instance;


    public synchronized static CuppCebeProgressDialog getInstance(Context context) {
        if (instance == null) instance = new CuppCebeProgressDialog(context);
        return instance;
    }

    public static CuppCebeProgressDialog show(Context context, CharSequence title,
                                             CharSequence message) {
        return show(context, title, message, false);
    }

    public static CuppCebeProgressDialog show(Context context, CharSequence title,
                                             CharSequence message, boolean indeterminate) {
        return getCuppCebeProgressDialog(context, title, message, indeterminate, false, null);
    }

    public static CuppCebeProgressDialog show(Context context, CharSequence title,
                                             CharSequence message, boolean indeterminate, boolean cancelable) {
        return getCuppCebeProgressDialog(context, title, message, indeterminate, cancelable, null);
    }

    public static CuppCebeProgressDialog getCuppCebeProgressDialog(Context context, CharSequence title,
                                                                 CharSequence message, boolean indeterminate,
                                                                 boolean cancelable, OnCancelListener cancelListener) {
        CuppCebeProgressDialog dialog = new CuppCebeProgressDialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.setContentView(R.layout.progress_layout);

        return dialog;
    }

    public CuppCebeProgressDialog(Context context) {
        super(context, R.style.ProgressDialogStyle);
    }
}
