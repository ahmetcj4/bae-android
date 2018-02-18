package com.kurye.kurye.task;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ahmet on 2/18/2018.
 */

class Task {
    public static final int FAIL = 0;
    public static final int SUCCESS = 1;

    @IntDef({FAIL, SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }


    public interface OnResultListener {
        void onResult(@Status int status, String message);
    }
}
