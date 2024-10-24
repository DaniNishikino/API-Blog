package com.descomplica.frameblog.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Date date;
    @ManyToOne
    private User userId;

    public Post() {
    }

    public Post(final String content, final Date date,
                final String title, final User userId) {
        this.content = content;
        this.date = date;
        this.title = title;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
