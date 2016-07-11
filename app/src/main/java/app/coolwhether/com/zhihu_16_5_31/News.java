package app.coolwhether.com.zhihu_16_5_31;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/1.
 */
public class News implements Serializable{
    private String title;
    private String img_url;
    private int id;

    public News(String title, String img_url, int id) {
        this.title = title;
        this.img_url = img_url;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
