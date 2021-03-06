package http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/8.
 */

/**
 * Http类构建完整url，并把url内容解析为json对象
 */
public class Http {
    public static String NEWSLIST_LATEST = "http://news-at.zhihu.com/api/4/news/latest";
    public static String STORY_VIEW = "http://daily.zhihu.com/story/";
    public static String NEWSDETAIL = "http://news-at.zhihu.com/api/4/news/";
    private static final String TAG = "Http";
    /**
     * 把url内容解析为json对象
     * @param urlAddr
     * @return
     * @throws IOException
     */
    public static String get(String urlAddr) throws IOException {
        HttpURLConnection con = null;
        Log.e(TAG, "get: urlAddr---"+urlAddr );
        try {
            URL url = new URL(urlAddr);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Log.e(TAG, "get: response---"+response.toString() );
                return response.toString();
            } else {
                throw new IOException("Network Error - response code: " + con.getResponseCode());
            }
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    /**
     * 获取具体的item的url，json对象
     * @param id
     * @return
     * @throws IOException
     */
    public static String getNewsDetail (int id) throws IOException{
        Log.e(TAG, "getNewsDetail: get(NEWSDETAIL + id)---"+get(NEWSDETAIL + id));
        return get(NEWSDETAIL + id);
    }
}
