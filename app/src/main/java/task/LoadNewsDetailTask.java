package task;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import org.json.JSONException;

import http.GsonHelper;
import http.Http;
import items.NewsDetail;

/**
 * Created by Administrator on 2016/7/8.
 */
public class LoadNewsDetailTask extends AsyncTask<Integer,Void, NewsDetail> {
    private WebView webView;
    private static final String TAG = "LoadNewsDetailTask";

    public LoadNewsDetailTask(WebView webView) {
        this.webView = webView;
    }

    @Override
    protected NewsDetail doInBackground(Integer... params) {
        NewsDetail newsDetail = null;
        try {
            //通过传入的id，利用Http类来构建完整url
            newsDetail = GsonHelper.parseJsonDetail(Http.getNewsDetail(params[0]));
            Log.e(TAG, "doInBackground: newsDetail----"+newsDetail );
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return newsDetail;
        }
    }

    @Override
    protected void onPostExecute(NewsDetail newsDetail) {
        String headImage;
        if (newsDetail.getImage() == null || newsDetail.getImage() == ""){
            headImage = "file:///android_asset/news_detail_header_image.jpg";
        }else {
            headImage = newsDetail.getImage();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"img-wrap\">")
                .append("<h1 class=\"headline-title\">")
                .append(newsDetail.getTitle()).append("</h1>")
                .append("<span class=\"img-source\">")
                .append(newsDetail.getImage_souce()).append("</span>")
                .append("<img src=\"").append(headImage)
                .append("\" alt=\"\">")
                .append("<div class=\"img-mask\"></div>");
        String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
                + newsDetail.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
        Log.e(TAG, "onPostExecute: mNewsContent---"+mNewsContent);
        webView.loadDataWithBaseURL("file:///android_asset/",mNewsContent,"text/html","UTF-8",null);
    }
}
