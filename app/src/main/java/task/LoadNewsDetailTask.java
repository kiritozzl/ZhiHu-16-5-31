package task;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import org.json.JSONException;

import http.GsonHelper;
import http.Http1;
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
            newsDetail = GsonHelper.parseJsonDetail(Http1.getNewsDetail(params[0]));
            Log.e(TAG, "doInBackground: newsDetail---"+newsDetail);
            Log.e(TAG, "doInBackground: image_source---"+newsDetail.getImage_souce());
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
        webView.loadDataWithBaseURL("file:///android_asset/",mNewsContent,"text/html","UTF-8",null);

        /**
         * htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + htmlData;
         // lets assume we have /assets/style.css file
         webView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);

         And only after that WebView will be able to find and use css-files from the assets directory.

         ps And, yes, if you load your html-file form the assets folder, you don't need to specify a base url.
         */
    }
}

