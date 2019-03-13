package com.ars.po;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Ocean Liang
 * @date 3/8/2019
 */
@Document(collection = "activity")
@Data
public class Activity {
    private String id;
    private String author;
    private String title;
    private String content;
    private List<Long> participantId;
    private LocalDateTime startDate;
    private LocalDateTime closingDate;
}