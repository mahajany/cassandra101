package com.datastax.workshop.enity.todo;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Data
public class Todo {
    
    private String url;
    private UUID uuid;
    private String title;
    private boolean completed = false;
    private int order = 0;
    
    public Todo() {}
    
    public Todo(String title, int order) {
        this.uuid  = UUID.randomUUID();
        this.title = title;
        this.order = order;
    }
    
    public Todo(String title, int order, boolean completed) {
       this(title, order);
       this.completed = completed;
    }
    
    public Todo setUrl(HttpServletRequest req) {
        if (url == null) {
            String reqUrl = req.getRequestURL().toString();
            url = reqUrl.contains("gitpod") ? reqUrl.replaceAll("http", "https") : reqUrl;
            url += uuid;
        }
        return this;
    }
    
    public Todo setUrl(String myUrl) {
        if (url == null) {
            url = myUrl.contains("gitpod") ? myUrl.replaceAll("http", "https") : myUrl;
        }
        return this;
    }
}
