package app.coolwhether.com.zhihu_16_5_31;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/6/3.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
