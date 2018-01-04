package com.example.vamsisaikrishna.appwithuiwork;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

/**
 * Created by Vamsisaikrishna on 12/30/2017.
 */

public class CustomHandlerThread extends HandlerThread {

    Handler handler;
    public CustomHandlerThread(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.e(CustomHandlerThread.class.getSimpleName(),"thread Id when message is posted:"+String.valueOf(Thread.currentThread().getId())+" : "+msg.obj);
            }
        };
    }
}
