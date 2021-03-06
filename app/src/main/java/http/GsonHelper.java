package http;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        HttpURLConnection con = null;
        Log.e(TAG, "readUrl: urls---"+urls);
        try {
            url = new URL(urls);
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK){
                br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
                String s;
                while ((s = br.readLine()) != null){
                    Log.e(TAG, "readUrl: s----"+s);
                    sb.append(s);
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con != null)
                con.disconnect();
        }
        return sb.toString();
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
