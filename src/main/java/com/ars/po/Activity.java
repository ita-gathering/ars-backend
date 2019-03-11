package com.ars.po;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author Ocean Liang
 * @date 3/8/2019
 */
@Data
public class Activity {
    @Id
    private String id;
    private String name;
    private String description;

    public Activity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
