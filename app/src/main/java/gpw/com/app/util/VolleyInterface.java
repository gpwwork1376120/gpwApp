package gpw.com.app.util;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

/**
 * Created by gpw on 2016/11/1.
 * --加油
 */
public abstract  class VolleyInterface {

    public Context mContext;
    public static Listener<String> mListener;
    public static ErrorListener mErrorListener;

    public VolleyInterface(Context context, Listener<String> listener, ErrorListener errorListener) {
        this.mContext = context;
        this.mListener = listener;
        this.mErrorListener = errorListener;
    }

    abstract void onSuccess(String result);

    abstract void onError(VolleyError error);



    Listener<String> loadingListener() {
        mListener = new Listener<String>() {

            @Override
            public void onResponse(String result) {

                onSuccess(result);
            }
        };
        return mListener;
    }

    ErrorListener errorListener() {
        mErrorListener = new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                onError(error);
            }
        };
        return mErrorListener;
    }
}
