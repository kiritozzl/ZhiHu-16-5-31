package http;

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

    /**
     * 把url内容解析为json对象
     * @param urlAdd
     * @return
     * @throws IOException
     */
    public static String get(String urlAdd) throws IOException{
        HttpURLConnection con = null;
        try{
            URL url = new URL(urlAdd);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent","Mozilla/5.0");
            if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = br.readLine()) != null){
                    sb.append(s);
                }
                br.close();
                return sb.toString();
        }else {
                throw new IOException("NetworkError - RespondCode:"+con.getResponseCode());
            }

        }finally {
            if (con != null){
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
        return get(NEWSDETAIL + id);
    }
}
