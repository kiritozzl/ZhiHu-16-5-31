package app.coolwhether.com.zhihu_16_5_31;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import adapter.NewsAdapter;
import http.ParserData;

public class MainActivity extends Activity {
    private ListView lv;
    private boolean isConnected;
    private String latest_news_url = " http://news-at.zhihu.com/api/4/news/latest";
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getDate());
        lv = (ListView) findViewById(R.id.main_lv);
        adapter = new NewsAdapter(MainActivity.this,R.layout.news_item_layout);
        lv.setAdapter(adapter);

        isConnected = isNetworkAvailiable();
        if (isConnected){
            //latest_news_url表示的是：AsyncTask<String,Void,List<News>> 里的第一个参数
            new ParserData(adapter).execute(latest_news_url);
        }else{
            Toast.makeText(getApplicationContext(),"without internet",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载自定义的menu菜单
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    /**
     * 检测当前手机网络是否可用
     * @return
     */
    private boolean isNetworkAvailiable(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private String getDate(){
        setContentView(R.layout.activity_main);
        //MMM:简写的英文月份，如：Jun
        //EEE:简写的英文星期，如：Wed
        SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.date_formate));
        String date = sdf.format(new Date());
        return date;
    }
}
