package app.coolwhether.com.zhihu_16_5_31;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import task.LoadNewsDetailTask;

/**
 * Created by Administrator on 2016/6/3.
 */
public class NewsItem extends Activity{
    private static final String TAG = "NewsItem";
    private WebView webView;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item);

        webView = (WebView) findViewById(R.id.webview);
        setWebView(webView);
        id = getIntent().getIntExtra("id",0);
        //异步加载url
        new LoadNewsDetailTask(webView).execute(id);
    }

    /*
    设置webview的基本属性
     */
    private void setWebView(WebView mWebView){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
    }
}
