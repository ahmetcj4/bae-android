package com.kurye.kurye.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

public class ViewUtils {
    public static Snackbar getSnackbar(Activity activity, String message) {
        return Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
    }
    public static Snackbar getSnackbarWithAction(Activity activity, String message,String actionTitle, View.OnClickListener listener) {
        return getSnackbar(activity,message).setAction(actionTitle, listener);
    }

    public static DividerItemDecoration getDividerItemDecoration(Context context, int orientation){
        return new DividerItemDecoration(context,orientation);
    }

    public static void showAlertDialog(Context context, String title, DialogInterface.OnClickListener onClick){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(title)
                .setPositiveButton(android.R.string.yes, onClick)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}