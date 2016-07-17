package app.coolwhether.com.zhihu_16_5_31;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import adapter.NewsAdapter;
import http.ParserData;
import utility.Utility;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener{
    private ListView lv;
    private boolean isConnected;
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
        lv.setOnItemClickListener(this);

        isConnected = Utility.isNetworkAvailiable(this);
        if (isConnected){
            new ParserData(adapter).execute();
        }else{
            Utility.alertNoNetwork(this);
        }
    }

    /**
     * 使用SwipeRefreshLayout，onRefresh方法实现在断网，恢复网络的更新
     * 提供缓冲效果
     */
    @Override
    public void onRefresh() {
        srl.setRefreshing(true);
        if(Utility.isNetworkAvailiable(MainActivity.this)){
            new ParserData(adapter, new ParserData.refreshListener() {
                @Override
                public void setOnRefresh() {
                    srl.setRefreshing(false);
                }
            }).execute();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favourite){
            Intent intent = new Intent(this,FavouriteItems.class);
            startActivity(intent);
        }
        return true;
    }

    private String getDate(){
        //MMM:简写的英文月份，如：Jun
        //EEE:简写的英文星期，如：Wed
        SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.date_formate));
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 添加item点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsItem.startActivity(MainActivity.this,adapter.getItem(position));
    }
}
