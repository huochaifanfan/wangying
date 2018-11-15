package com.wytianxiatuan.wytianxia.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by liuju on 2018/1/27.
 */
@Table(name = "history")
public class History {
    @Column(name = "id",isId = true,autoGen = true)
    private int id;
    @Column(name = "content")
    private String content;
    @Column(name = "time")
    private String time;

    public History(String content, String time) {
        this.content = content;
        this.time = time;
    }
    public History(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
