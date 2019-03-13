package com.ars.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Ocean Liang
 * @date 3/8/2019
 */
@Data
public class Activity {
    @Id
    public String id;
    public String author;
    public String title;
    public String content;
    public List<User> participants;
    public LocalDateTime startDate;
    public LocalDateTime closingDate;

    public Activity(String author, String title) {
        this.author = author;
        this.title = title;
    }
}