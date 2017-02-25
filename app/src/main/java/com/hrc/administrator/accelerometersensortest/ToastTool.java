package com.hrc.administrator.accelerometersensortest;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/25.
 */

public class ToastTool {
    public static Toast mToast;
    public static Handler mHandler=new Handler();
    public static Runnable r=new Runnable() {
        @Override
        public void run() {
            mToast.cancel();
            mToast=null;
        }
    };

    public static void SetMessage(Context context,String message){
        mHandler.removeCallbacks(r);
        if(mToast==null){
            mToast=Toast.makeText(context,message,Toast.LENGTH_SHORT);
        }
        mHandler.postDelayed(r,1*1000);
        mToast.show();
    }
}
