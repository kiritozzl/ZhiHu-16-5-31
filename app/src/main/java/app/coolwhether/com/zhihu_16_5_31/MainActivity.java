package app.coolwhether.com.zhihu_16_5_31;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import adapter.NewsAdapter;
import http.ParserData;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{
    private ListView lv;
    private boolean isConnected;
    private String latest_news_url = " http://news-at.zhihu.com/api/4/news/latest";
    private NewsAdapter adapter;
    private SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getDate());

        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        srl.setOnRefreshListener(this);
        //SwipeRefreshLayout设置刷新时显示的颜色
        srl.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        lv = (ListView) findViewById(R.id.main_lv);
        adapter = new NewsAdapter(MainActivity.this,R.layout.news_item_layout);
        lv.setAdapter(adapter);
        /**
         * 设置主页面item点击事件，传入点击item的id
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) lv.getItemAtPosition(position);
                int i = news.getId();
                Intent intent = new Intent(MainActivity.this,NewsItem.class);
                intent.putExtra("id",i);
                startActivity(intent);
            }
        });

        isConnected = isNetworkAvailiable();
        if (isConnected){
            //latest_news_url表示的是：AsyncTask<String,Void,List<News>> 里的第一个参数
            new ParserData(adapter).execute(latest_news_url);
        }else{
            Toast.makeText(getApplicationContext(),"without internet",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 使用SwipeRefreshLayout，onRefresh方法实现在断网，恢复网络的更新
     * 提供缓冲效果
     */
    @Override
    public void onRefresh() {
        srl.setRefreshing(true);
        if(isNetworkAvailiable()){
            new ParserData(adapter, new ParserData.refreshListener() {
                @Override
                public void setOnRefresh() {
                    srl.setRefreshing(false);
                }
            }).execute(latest_news_url);
        }else{
            Toast.makeText(getApplicationContext(),"without internet",Toast.LENGTH_SHORT).show();
            srl.setRefreshing(false);
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
