package com.gpw.app.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gpw.app.R;
import com.gpw.app.activity.MainActivity;
import com.gpw.app.util.DensityUtil;
import com.gpw.app.util.FastBlurUtil;
import com.gpw.app.util.LogUtil;


/**
 * Created by gpw on 2016/8/2.
 * --加油
 */
public class MyDialog extends Dialog {

    private static MyDialog nickDialog;
    private static MyDialog endDialog;
    private static MyDialog psdDialog;
    private static MyDialog selectSexDialog;
    private static MyDialog selectPicDialog;
    private static MyDialog loginPicDialog;

    public static NickListener nickListener;
    public static EndListener endListener;
    public static PsdListener psdListener;

    public MyDialog(Activity activity) {
        super(activity, R.style.myDialog);
    }

    public MyDialog(Activity activity,int style) {
        super(activity, style);
    }
    public static MyDialog nickDialog(Activity activity) {
        nickDialog = new MyDialog(activity);

        View v = View.inflate(activity, R.layout.dialog_edit, null);
        final EditText et_nick = (EditText) v.findViewById(R.id.et_nick);
        Button bt_cancel = (Button) v.findViewById(R.id.bt_cancel);
        Button bt_ok = (Button) v.findViewById(R.id.bt_ok);
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        int width = (int) (size.x * 0.8);
        int height = (int) (size.y * 0.3);
        nickDialog.setContentView(v);
        nickDialog.setCancelable(true);
        Window dialogWindow = nickDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);
        lp.height = height;
        lp.width = width;
        dialogWindow.setAttributes(lp);


        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickDialog.dismiss();
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickListener.onSetting(et_nick.getText().toString());
            }
        });

        return nickDialog;
    }


    public static MyDialog psdDialog(Activity activity) {
        psdDialog = new MyDialog(activity);
        View v = View.inflate(activity, R.layout.dialog_psd, null);
        final EditText et_old = (EditText) v.findViewById(R.id.et_old);
        final EditText et_new = (EditText) v.findViewById(R.id.et_new);
        final EditText et_new1 = (EditText) v.findViewById(R.id.et_new1);
        Button bt_cancel = (Button) v.findViewById(R.id.bt_cancel);
        Button bt_ok = (Button) v.findViewById(R.id.bt_ok);

        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        int width = (int) (size.x * 0.9);
        int height = (int) (size.y * 0.53);
        psdDialog.setContentView(v);
        psdDialog.setCancelable(true);
        Window dialogWindow = psdDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);
        lp.height = height;
        lp.width = width;
        dialogWindow.setAttributes(lp);


        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psdDialog.dismiss();
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psdListener.onSetting(et_old.getText().toString(), et_new.getText().toString(), et_new1.getText().toString());
            }
        });
        return psdDialog;
    }

    public static MyDialog endDialog(final Activity activity) {
        endDialog = new MyDialog(activity);
        View v = View.inflate(activity, R.layout.dialog_reason, null);

        final EditText et_content = (EditText) v.findViewById(R.id.et_reason);
        Button bt_cancel = (Button) v.findViewById(R.id.bt_cancel);
        Button bt_ok = (Button) v.findViewById(R.id.bt_ok);

        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        int width = (int) (size.x * 0.8);
        int height = (int) (size.y * 0.3);
        endDialog.setContentView(v);
        endDialog.setCancelable(true);
        Window dialogWindow = endDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);
        lp.height = height;
        lp.width = width;
        dialogWindow.setAttributes(lp);

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDialog.dismiss();
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_content.getText().toString().isEmpty()) {
                    Toast.makeText(activity, "请输入原因", Toast.LENGTH_SHORT).show();
                    return;
                }
                endListener.onSetting(et_content.getText().toString());
            }
        });

        return endDialog;
    }


    public static MyDialog selectSexDialog(final Activity activity, View.OnClickListener sexOnClick) {
        selectSexDialog = new MyDialog(activity);
        View v = View.inflate(activity, R.layout.dialog_sex, null);

        Button manBtn = (Button) v.findViewById(R.id.manBtn);
        Button womanBtn = (Button) v.findViewById(R.id.womanBtn);
        Button  cancelBtn = (Button) v.findViewById(R.id.cancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSexDialog.dismiss();
            }
        });

        manBtn.setOnClickListener(sexOnClick);
        womanBtn.setOnClickListener(sexOnClick);

        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;
        selectSexDialog.setContentView(v);
        selectSexDialog.setCancelable(true);
        Window dialogWindow = selectSexDialog.getWindow();
        dialogWindow.setWindowAnimations(R.style.PopupAnimation); //设置窗口弹出动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.BOTTOM);
        lp.height = DensityUtil.dip2px(activity,183.2f);
        lp.width = width;
        dialogWindow.setAttributes(lp);
        return selectSexDialog;
    }

    public static MyDialog selectPicDialog(final Activity activity, View.OnClickListener picOnClick) {
        selectPicDialog = new MyDialog(activity);
        View v = View.inflate(activity, R.layout.dialog_pic, null);

        Button takePhotoBtn = (Button) v.findViewById(R.id.takePhotoBtn);
        Button pickPhotoBtn = (Button) v.findViewById(R.id.pickPhotoBtn);
        Button cancelBtn = (Button) v.findViewById(R.id.cancelBtn);

        pickPhotoBtn.setOnClickListener(picOnClick);
        takePhotoBtn.setOnClickListener(picOnClick);
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectPicDialog.dismiss();
            }
        });
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;
        selectPicDialog.setContentView(v);
        selectPicDialog.setCancelable(true);
        Window dialogWindow = selectPicDialog.getWindow();
        assert dialogWindow != null;
        dialogWindow.setWindowAnimations(R.style.PopupAnimation); //设置窗口弹出动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.BOTTOM);

        lp.height = DensityUtil.dip2px(activity,183.2f);
        lp.width = width;
        dialogWindow.setAttributes(lp);
        return selectPicDialog;
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static MyDialog loginPicDialog(final Activity activity, View.OnClickListener loginOnClick) {
        loginPicDialog = new MyDialog(activity,R.style.fullDialog);
        View v = View.inflate(activity, R.layout.dialog_login, null);

//        Button takePhotoBtn = (Button) v.findViewById(R.id.takePhotoBtn);
//        Button pickPhotoBtn = (Button) v.findViewById(R.id.pickPhotoBtn);
//        Button cancelBtn = (Button) v.findViewById(R.id.cancelBtn);
//
//        pickPhotoBtn.setOnClickListener(loginOnClick);
//        takePhotoBtn.setOnClickListener(loginOnClick);
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                selectPicDialog.dismiss();
//            }
//        });

        RelativeLayout login_layout = (RelativeLayout) v.findViewById(R.id.login_layout);
        Bitmap bmp = FastBlurUtil.getBlurBackgroundDrawer(activity);
        LogUtil.i("bmp"+bmp);
        login_layout.setBackground(new BitmapDrawable(activity.getResources(), bmp));
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;
        int height = size.y;
        loginPicDialog.setContentView(v);
        loginPicDialog.setCancelable(true);
        Window dialogWindow = loginPicDialog.getWindow();
        assert dialogWindow != null;
        dialogWindow.setWindowAnimations(R.style.AnimationFade); //设置窗口弹出动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.BOTTOM);
        lp.height = height;
        lp.width = width;
        dialogWindow.setAttributes(lp);
        return loginPicDialog;
    }



    public interface NickListener {
        void onSetting(String name);
    }

    public void setOnSettingListener(NickListener listener) {
        nickListener = listener;
    }

    public interface PsdListener {
        void onSetting(String old, String new1, String new2);
    }

    public void setOnSettingListener(PsdListener listener) {
        psdListener = listener;
    }

    public interface EndListener {
        void onSetting(String content);
    }

    public void setOnSettingListener(EndListener listener) {
        endListener = listener;
    }

}
