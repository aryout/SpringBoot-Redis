package com.faceyee.service;

import com.faceyee.domain.repository.UserRepository;
import com.faceyee.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 97390 on 8/21/2018.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepositoty;

    public User findUserByName(String name){ // service 的业务方法名往往和Dao的方法名匹配； 当然，复杂的业务方法会集合操作多个Dao方法，所以，事务是基于service的
        User user = null;
        try {
            user = userRepositoty.findByUserName(name);
        }catch (Exception e){}

        return user;
    }
}
