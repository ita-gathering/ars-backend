package com.ars.repository;

import com.ars.po.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ocean Liang
 * @date 3/8/2019
 */
@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
}
