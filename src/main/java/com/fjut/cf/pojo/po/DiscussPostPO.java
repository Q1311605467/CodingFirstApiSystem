package com.fjut.cf.pojo.po;

import java.util.Date;

/**
 * @author axiang [2019/10/21]
 */
public class DiscussPostPO {
    private Integer id;
    private String title;
    private Date time;
    private String author;
    private Double priority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "DiscussPostPO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", author='" + author + '\'' +
                ", priority=" + priority +
                '}';
    }
}
