package com.gpw.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.gpw.app.R;
import com.gpw.app.util.FastBlurUtil;
import com.gpw.app.util.LogUtil;


public class LoginPopupWindow extends PopupWindow {

    private Button takePhotoBtn, pickPhotoBtn, cancelBtn;
    private View mMenuView;

    @SuppressLint("InflateParams")
    public LoginPopupWindow(final Activity context, OnClickListener loginOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.dialog_login, null);
//        takePhotoBtn = (Button) mMenuView.findViewById(R.id.takePhotoBtn);
//        pickPhotoBtn = (Button) mMenuView.findViewById(R.id.pickPhotoBtn);
//        cancelBtn = (Button) mMenuView.findViewById(R.id.cancelBtn);
//
//        cancelBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//        pickPhotoBtn.setOnClickListener(itemsOnClick);
//        takePhotoBtn.setOnClickListener(itemsOnClick);


        this.setContentView(mMenuView);

        this.setWidth(LayoutParams.MATCH_PARENT);

        this.setHeight(LayoutParams.WRAP_CONTENT);

        this.setFocusable(true);

        this.setAnimationStyle(R.style.PopupAnimation);

        ColorDrawable dw = new ColorDrawable(0x00000000);

        this.setBackgroundDrawable(dw);
        setBackgroundAlpha(context,0.6f);

        Bitmap bmp = FastBlurUtil.getBlurBackgroundDrawer(context);
        LogUtil.i("bmp"+bmp);
        context.getWindow().setBackgroundDrawable(new BitmapDrawable(context.getResources(), bmp));
        mMenuView.setOnTouchListener(new OnTouchListener() {

            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        setBackgroundAlpha(context,1.0f);
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    private void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }

}
