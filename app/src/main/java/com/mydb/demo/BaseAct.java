package com.mydb.demo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class BaseAct extends AppCompatActivity {
    //===================================handler软引用 --开始==========================================
    public final Handler handler = new WeakRefHandler(this);


    public class WeakRefHandler extends Handler {

        private final WeakReference<BaseAct> mFragmentReference;

        public WeakRefHandler(BaseAct activity) {
            mFragmentReference = new WeakReference<BaseAct>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final BaseAct activity = mFragmentReference.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    /**
     * @param msg
     */
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            default:
                break;
        }
    }
    //===================================handler软引用 --结束==========================================
}
