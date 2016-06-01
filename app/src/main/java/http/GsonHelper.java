package http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import app.coolwhether.com.zhihu_16_5_31.News;

/**
 * Created by Administrator on 2016/6/1.
 */
public class GsonHelper {
    private static final String TAG = "GsonHelper";

    public static List<News> getListNews(String url) throws JSONException{
        //要先把ＵＲＬ里的内容获取才能转化为JSONObject
        JSONObject jsonObject = new JSONObject(readUrl(url));
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
     * 获取URL里的内容
     * @param urls
     * @return
     */
    public static String readUrl(String urls){
        URL url;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(urls);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s;
            while ((s = br.readLine()) != null){
                sb.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return sb.toString();
    }
}
