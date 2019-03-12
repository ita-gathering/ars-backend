package com.ars.service;

import com.ars.po.User;
import com.ars.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Created by guowanyi on 2019/3/12.
 */
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public User getUserByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User deleteUser(String userId){
        User user = userRepository.findById(userId).orElse(null);
        if(Objects.isNull(user)){
            return null;
        }
        userRepository.delete(user);
        return user;
    }
}
