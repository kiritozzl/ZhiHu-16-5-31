package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import app.coolwhether.com.zhihu_16_5_31.News;

/**
 * Created by Administrator on 2016/7/11.
 */
public class DailyNewsDb {
    private DbHelper dbHelper;
    private static DailyNewsDb dailyNewsDb;
    private SQLiteDatabase db;
    private String [] allColumns = {DbHelper.COLUMN_ID,DbHelper.COLUMN_NEWS_ID,
                                DbHelper.COLUMN_NEWS_TITLE,DbHelper.COLUMN_NEWS_IMAGE};

    public DailyNewsDb(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    private static final String TAG = "DailyNewsDb";

    /**
     * 静态方法创建DailyNewsDb实例
     * @param context
     * @return
     */
    public synchronized static DailyNewsDb getInstance(Context context){
        if (dailyNewsDb == null){
            dailyNewsDb = new DailyNewsDb(context);
        }
        return dailyNewsDb;
    }

    /**
     * 设置某条消息为saveFavourite
     * @param news
     */
    public void saveFavourite(News news){
        if (news != null){
            ContentValues values = new ContentValues();
            values.put(DbHelper.COLUMN_NEWS_ID,news.getId());
            values.put(DbHelper.COLUMN_NEWS_IMAGE,news.getImg_url());
            values.put(DbHelper.COLUMN_NEWS_TITLE,news.getTitle());

            db.insert(DbHelper.TABLE_NAME,null,values);
            Log.e(TAG, "saveFavourite: values----"+values);
            Log.e(TAG, "saveFavourite: db---"+db.toString());
        }
    }

    /**
     * 加载已保存的Favourite列表
     * @return
     */
    public List<News> loadFavourite(){
        List<News> newsList = new ArrayList<>();
        Cursor cursor = db.query(DbHelper.TABLE_NAME,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                News news = new News();
                news.setId(cursor.getInt(1));
                news.setTitle(cursor.getString(2));
                news.setImg_url(cursor.getString(3));
                Log.e(TAG, "loadFavourite: news--"+news.toString());
                newsList.add(news);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return newsList;
    }

    /**
     * 判断某条news是否为favourite
     * @param news
     * @return
     */
    public boolean isFavourite(News news){
        Cursor cursor = db.query(DbHelper.TABLE_NAME,null,DbHelper.COLUMN_NEWS_ID + " = ?",
                new String[]{news.getId() + ""},null,null,null);
        if (cursor.moveToNext()){
            cursor.close();
            return true;
        }else {
            return false;
        }
    }

    public void deleteNews(News news){
        if (news != null){
            db.delete(DbHelper.TABLE_NAME,DbHelper.COLUMN_NEWS_ID + " = ?",new String[]{news.getId() + ""});
        }
    }


}
