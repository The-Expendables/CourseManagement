package com.example.asus.coursemanagament.Util;

/**
 * Created by Administrator on 2015/11/16.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
