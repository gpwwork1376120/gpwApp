package gpw.com.app.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import gpw.com.app.R;

public class CustomProgressDialog extends ProgressDialog {


    private TextView mLoadingTv;

    public CustomProgressDialog(Context context) {
        super(context,R.style.MyDialog);
        //setCanceledOnTouchOutside(true);//点击对话框外部可取消对话框显示
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        mLoadingTv = (TextView) findViewById(R.id.loadingTv);

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
    public void setText(CharSequence charSequence){
        mLoadingTv.setText(charSequence);
    }
}