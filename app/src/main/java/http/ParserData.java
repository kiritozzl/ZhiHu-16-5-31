package http;

import android.os.AsyncTask;

import org.json.JSONException;

import java.util.List;

import adapter.NewsAdapter;
import app.coolwhether.com.zhihu_16_5_31.News;

/**
 * Created by Administrator on 2016/6/1.
 */
public class ParserData extends AsyncTask<String,Void,List<News>>{
    private NewsAdapter adapter;
    private static final String TAG = "ParserData";
    public ParserData(NewsAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected List<News> doInBackground(String... params) {
        List<News> lists = null;
        try {
            lists = GsonHelper.getListNews(params[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return lists;
        }
    }

    /**
     * 参数：newses是方法doInBackground的返回值
     * @param newses
     */
    @Override
    protected void onPostExecute(List<News> newses) {
        //为adapter设置回调，使用newses为list添加内容
        adapter.getNewsList(newses);
    }
}
