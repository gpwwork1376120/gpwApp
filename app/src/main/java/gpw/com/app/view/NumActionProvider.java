package gpw.com.app.view;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;

import gpw.com.app.R;

/**
 * Created by gpw on 2016/10/12.
 * --加油
 */

public class NumActionProvider extends ActionProvider{
    private Context mContext;

    public NumActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view =inflater.inflate(R.layout.view_num_action, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("asdasd");
            }
        });
        return view;
    }
}
