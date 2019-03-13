package com.ars.config.mongo;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.time.LocalDateTime;

/**
 * @author Ocean Liang
 * @date 3/13/2019
 */
@Component
public class SaveMongoEventListener extends AbstractMongoEventListener<Object> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        final Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            if (field.isAnnotationPresent(CreatedDate.class) && field.get(source) == null) {
                field.set(source, LocalDateTime.now());
            }
        });
    }
}
