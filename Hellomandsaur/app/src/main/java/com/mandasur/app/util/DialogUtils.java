package com.mandasur.app.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to keep static utility methods for various utility tasks Please Define
 * DIALOG_ICON before using the class
 */
public final class DialogUtils {

    private static ProgressDialog pd;
    public static int DIALOG_ICON = 0;

    /**
     * To show Dialog with message
     *
     * @param context
     * @param info
     */
    public static void showInfoDialog(final Activity activity,
                                      final String message) {

        AlertDialog.Builder builder = null;
         if (activity.getParent() != null)
            builder = new AlertDialog.Builder(activity.getParent());
        else
            builder = new AlertDialog.Builder(activity);

        // builder.setTitle("Alert!");
        builder.setIcon(DIALOG_ICON);
        if (message != null)
            builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Runtime.getRuntime().gc();
            }
        });
        AlertDialog msg = builder.create();

        msg.show();
    }

    /**
     * To show Dialog with message
     *
     * @param context
     * @param info
     */
    public static void showInfoDialog(final Activity activity,
                                      final String title, final String message) {
        AlertDialog.Builder builder = null;
        if (activity.getParent() != null)
            builder = new AlertDialog.Builder(activity.getParent());
        else
            builder = new AlertDialog.Builder(activity);

        if (title != null)
            builder.setTitle(title);
        builder.setIcon(DIALOG_ICON);
        if (message != null)
            builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Runtime.getRuntime().gc();
            }
        });
        AlertDialog msg = builder.create();

        msg.show();
    }


    public interface OnFinishDialogClickInterface {

        public void onFinishDialog();

    }


    public static void showInfoDialogWithTwoButtons(final Activity activity,
                                                    final String title, final String message, final String buttonOK,
                                                    final String buttonCancel) {
        AlertDialog.Builder builder = null;
        if (activity.getParent() != null)
            builder = new AlertDialog.Builder(activity.getParent());
        else
            builder = new AlertDialog.Builder(activity);

        if (title != null)
            builder.setTitle(title);
        builder.setIcon(DIALOG_ICON);
        if (message != null)
            builder.setMessage(message);

        builder.setPositiveButton(buttonOK,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Runtime.getRuntime().gc();
                    }
                });

        builder.setNegativeButton(buttonCancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Runtime.getRuntime().gc();
                    }
                });
        AlertDialog msg = builder.create();

        msg.show();
    }



    /**
     * To show Dialog with message and finish an activity when tap on Ok
     *
     * @param context
     * @param info
     */
    public static AlertDialog showFinishDialog(final Activity activity,
                                               final String message,final OnFinishDialogClickInterface onFinishDialogClickInterface) {
        try {
            AlertDialog.Builder builder = null;
            if (activity.getParent() != null)
                builder = new AlertDialog.Builder(activity.getParent(),AlertDialog.THEME_HOLO_LIGHT);
            else
                builder = new AlertDialog.Builder(activity,AlertDialog.THEME_HOLO_LIGHT);

            builder.setCancelable(false);
            builder.setIcon(DIALOG_ICON);
            if (message != null)
                builder.setMessage(message);

            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Runtime.getRuntime().gc();
                            if (onFinishDialogClickInterface!=null){
                                onFinishDialogClickInterface.onFinishDialog();
                            }

                        }
                    });
            AlertDialog msg = builder.create();

            msg.show();
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * To show Dialog with message and finish an activity when tap on Ok
     *
     * @param context
     * @param info
     */
    public static void showFinishDialog(final Activity activity,
                                        final String title, final String message) {
        try {
            AlertDialog.Builder builder = null;
            if (activity.getParent() != null)
                builder = new AlertDialog.Builder(activity.getParent());
            else
                builder = new AlertDialog.Builder(activity);

            if (title != null)
                builder.setTitle(title);
            builder.setCancelable(false);
            builder.setIcon(DIALOG_ICON);
            if (message != null)
                builder.setMessage(message);

            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Runtime.getRuntime().gc();
                            activity.finish();
                        }
                    });
            AlertDialog msg = builder.create();

            msg.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * **
     * To show Dialog with message and pop current fragment when tap on Ok
     *
     * @param fActivity
     * @param title
     * @param message
     */
    public static void showFinishFragmentDialog(
            final FragmentActivity fActivity, final String title,
            final String message) {
        try {
            AlertDialog.Builder builder = null;
            if (fActivity.getParent() != null)
                builder = new AlertDialog.Builder(fActivity.getParent());
            else
                builder = new AlertDialog.Builder(fActivity);

            if (title != null)
                builder.setTitle(title);
            builder.setCancelable(false);
            builder.setIcon(DIALOG_ICON);
            if (message != null)
                builder.setMessage(message);

            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Runtime.getRuntime().gc();
                            fActivity.getSupportFragmentManager()
                                    .popBackStackImmediate();
                        }
                    });
            AlertDialog msg = builder.create();
            msg.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * *
     * To show confirmation dialog with custom title,message,icon and buttons
     * name. With an interface to respond with tapping on dialog' buttons.
     *
     * @param activity
     * @param title
     * @param message
     * @param isCancelable
     * @param iconResId
     * @param positiveBtnName
     * @param negativeBtnName
     * @param DialogResponseInterface
     */
    public static AlertDialog showConfirmationDialog(final Activity activity,
                                                     final String title, final String message,
                                                     final boolean isCancelable, final int iconResId,
                                                     final String positiveBtnName, final String negativeBtnName,
                                                     final DialogResponseInterface dri) {

        try {
            AlertDialog.Builder builder = null;
            if (activity.getParent() != null)
                builder = new AlertDialog.Builder(activity.getParent());
            else
                builder = new AlertDialog.Builder(activity);

            if (title != null)
                builder.setTitle(title);
            builder.setCancelable(isCancelable);
            builder.setIcon(iconResId);
            if (message != null)
                builder.setMessage(message);

            if (positiveBtnName != null) {
                builder.setPositiveButton(positiveBtnName,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                dri.doOnPositiveBtnClick(activity);
                            }
                        });
            }
            if (negativeBtnName != null) {
                builder.setNegativeButton(negativeBtnName,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                dri.doOnNegativeBtnClick(activity);
                            }
                        });
            }
            AlertDialog msg = builder.create();
            msg.show();
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * To show ProgressDialog while fetching in progress
     *
     * @param activity
     * @param title
     * @param message
     * @param isCancelable
     */
    public static void showProgressDialog(final Context activity,
                                          final String title, final String message, final boolean isCancelable) {
        try {
            if (pd == null) {


                    pd = new ProgressDialog(activity,ProgressDialog.THEME_HOLO_LIGHT);

            } else
                pd.dismiss();

            //////////////////////////////////pd.setTitle(title);
            if(!TextUtils.isEmpty(message)){
                pd.setMessage(message);

            }
            pd.setIcon(DIALOG_ICON);
            pd.setCancelable(isCancelable);
            pd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * To dismiss progressDialog
     */
    public static void dismissProgressDialog() {
        try {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
                pd = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd = null;
        }
    }



    /**
     * @param dateString
     * @param patternRequired
     * @param dateStringPattern
     * @return String
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateWithPattern(Date date, String patternRequired) {
        try {
            SimpleDateFormat requiredSdf = new SimpleDateFormat(patternRequired);
            return requiredSdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return date.toString();
        }
    }
}
