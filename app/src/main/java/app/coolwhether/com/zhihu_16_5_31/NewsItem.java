package app.coolwhether.com.zhihu_16_5_31;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Administrator on 2016/6/3.
 */
public class NewsItem extends Activity{
    private String url;
    private static final String TAG = "NewsItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item);
        url = getIntent().getStringExtra("url");
        Log.e(TAG, "onCreate: -------------url---Item"+url);
    }
}
