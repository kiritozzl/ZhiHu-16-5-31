package app.coolwhether.com.zhihu_16_5_31;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import adapter.NewsAdapter;
import db.DailyNewsDb;

/**
 * Created by Administrator on 2016/7/11.
 */
public class FavouriteItems extends Activity implements AdapterView.OnItemClickListener {
    private ListView lv;
    private List<News> newsList;
    private NewsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_favourite);
        lv = (ListView) findViewById(R.id.favourite_list);
        newsList = DailyNewsDb.getInstance(this).loadFavourite();
        adapter = new NewsAdapter(this,R.layout.news_item_layout,newsList);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsItem.startActivity(this,adapter.getItem(position));
    }
}
