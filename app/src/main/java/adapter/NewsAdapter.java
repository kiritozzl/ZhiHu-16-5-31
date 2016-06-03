package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.coolwhether.com.zhihu_16_5_31.News;
import app.coolwhether.com.zhihu_16_5_31.R;

/**
 * Created by Administrator on 2016/6/1.
 */
public class NewsAdapter extends ArrayAdapter<News> {
    private LayoutInflater inflater;
    private int resource;
    private Context context;
    private static final String TAG = "NewsAdapter";

    public NewsAdapter(Context context, int resource) {
        super(context, resource);
        inflater = LayoutInflater.from(context);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(resource,null);
            holder = new viewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.lv_iv);
            holder.tv = (TextView) convertView.findViewById(R.id.lv_tv);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }
        //此时，即使news里的值为空，初始化NewsAdapter也不会出错
        News news = getItem(position);
        holder.tv.setText(news.getTitle());
        Picasso.with(context).load(news.getImg_url()).into(holder.iv);
        return convertView;
    }

    public void getNewsList(List<News> listNews){
        clear();
        //为adapter添加值
        Log.e(TAG, "getNewsList: listNews"+listNews);
        addAll(listNews);
        notifyDataSetChanged();
    }

    class viewHolder{
        ImageView iv;
        TextView tv;
    }
}
