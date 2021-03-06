package app.coolwhether.com.zhihu_16_5_31;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import db.DailyNewsDb;
import task.LoadNewsDetailTask;
import utility.Utility;

/**
 * Created by Administrator on 2016/6/3.
 */
public class NewsItem extends Activity{
    private WebView webView;
    private News news;
    private boolean isFavourite = false;
    private static final String TAG = "NewsItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item);

        webView = (WebView) findViewById(R.id.webview);
        setWebView(webView);
        news = (News) getIntent().getSerializableExtra("news");
        isFavourite = DailyNewsDb.getInstance(this).isFavourite(news);
        //异步加载url
        new LoadNewsDetailTask(webView).execute(news.getId());
        Log.e(TAG, "onCreate: isFavourite----"+isFavourite);
    }

    /*
    设置webview的基本属性
     */
    private void setWebView(WebView mWebView){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
    }

    /**
     * 实现点击item跳转到相应的具体新闻界面
     * @param context
     * @param news
     */
    public static void startActivity(Context context,News news){
        if(Utility.isNetworkAvailiable(context)){
            Intent i = new Intent(context,NewsItem.class);
            i.putExtra("news",news);
            context.startActivity(i);
        }else {
            Utility.alertNoNetwork(context);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        if(isFavourite){
            menu.findItem(R.id.favourite).setIcon(R.drawable.fav_active);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.favourite){
            if (!isFavourite){
                DailyNewsDb.getInstance(this).saveFavourite(news);
                item.setIcon(R.drawable.fav_active);
                isFavourite = true;
            }else if(isFavourite){
                DailyNewsDb.getInstance(this).deleteNews(news);
                item.setIcon(R.drawable.fav_normal);
                isFavourite = false;
            }
        }else if(item.getItemId() == R.id.Setting){

        }
        return true;
    }
}
