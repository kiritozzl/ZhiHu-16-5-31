package utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/10.
 */
public class Utility {

    /**
     * 检测网络状态
     * @param context
     * @return
     */
    public static boolean isNetworkAvailiable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static void alertNoNetwork(Context context){
        Toast.makeText(context,"No Network!",Toast.LENGTH_LONG).show();
    }
}
