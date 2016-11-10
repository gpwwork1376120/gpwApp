package gpw.com.app.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.google.gson.JsonObject;

import gpw.com.app.util.HttpUtil;


/**
 * Created by gpw on 2016/9/28.
 * --加油
 */

public abstract class BaseAsyncTask extends AsyncTask<JsonObject, String, String> {

    private Context mContext;
    private Activity mActivity;
    private String url;
    private String token;

    public BaseAsyncTask(Context mContext, String url) {
        super();
        this.mContext = mContext;
        this.mActivity = (Activity) mContext;
        this.url = url;
    }

    @Override
    protected String doInBackground(JsonObject... params) {
        JsonObject json_data = params[0];
      //  return HttpUtil.httpPost(url, json_data,token);

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
//        if (s.equals("unAuthorization")) {
//
//            JPushInterface.setAlias(mContext, //上下文对象
//                    "", //别名
//                    new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
//                        @Override
//                        public void gotResult(int i, String s, Set<String> set) {
//                            if(i==0){
//                                System.out.println("绑定成功");}
//                        }
//                    });
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//            builder.setTitle("提示");
//            builder.setMessage("当前账号在另一台设备登录，请重新登录");
//            builder.setCancelable(false);
//            builder.setIcon(R.mipmap.hint);
//            builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent(mContext,LoginActivity.class);
//                    mContext.startActivity(intent);
//                    mActivity.finish();
//                }
//            });
//            builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    MyApplication.getInstance().exit();
//                }
//            });
//            builder.show();
//            return;
//        }
        dealResults(s);
        super.onPostExecute(s);
    }

    abstract void dealResults(String s);

}
