package com.faceyee.service;

import com.faceyee.domain.repository.UserRepository;
import com.faceyee.domain.entity.User;
import com.faceyee.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 97390 on 8/21/2018.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUserName(String userName){ // service 的业务方法名往往和Dao的方法名匹配； 当然，复杂的业务方法会集合操作多个Dao方法，所以，事务是基于service的
        User user = null;
        try {
            user = userRepository.findByUserName(userName);
        }catch (Exception ignored){}

        return user;
    }

    @Transactional
    public void testBusiness(){

        // some database options

        // 模拟新名字与其他名字冲突
        isTrue(false, "名字已经被使用了...");

        // update database dog info

    }

    public static void isTrue(boolean expression, String error){
        if(!expression) {
            throw new BusinessException(-3,error);
        }
    }
}
