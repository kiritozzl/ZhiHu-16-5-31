package http;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.coolwhether.com.zhihu_16_5_31.News;
import items.NewsDetail;

/**
 * Created by Administrator on 2016/6/1.
 */
public class GsonHelper {
    private static final String TAG = "GsonHelper";
    static String respond;

    public static List<News> getListNews(String url) throws JSONException{
        //要先把ＵＲＬ里的内容获取才能转化为JSONObject
        JSONObject jsonObject = new JSONObject(url);
        JSONArray jsonArray = jsonObject.getJSONArray("stories");
        List<News> list = new ArrayList<>();
        for (int i = 0; i <jsonArray.length() ; i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            int id = obj.optInt("id");
            String title = obj.optString("title");
            String images = (String) obj.getJSONArray("images").get(0);
            News news = new News(title,images,id);
            list.add(news);
        }
        return list;
    }

    /**
     * 把传入的json对象解析为具体的类
     * @param json
     * @return
     * @throws JSONException
     */
    public static NewsDetail parseJsonDetail(String json) throws JSONException{
        Gson gson = new Gson();
        return gson.fromJson(json,NewsDetail.class);
    }
}
