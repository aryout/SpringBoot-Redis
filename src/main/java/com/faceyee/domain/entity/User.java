package com.faceyee.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
//import org.springframework.data.annotation.Id;

import javax.persistence.*; // Entity.Id.GeneratedValue
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by 97390 on 8/21/2018.
 */
@Data
//@AllArgsConstructor
@Entity
@Table(name = "tbl_user") //指定数据库中对应的表名
public class User implements Serializable { // 还需要实现这个接口吗?
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // id配置为主键，生成策略为自动生成

    @Column(nullable = false, unique = true) // 不加行不行?
    private String userName;

    @Max(value = 999999,message = "超过最大数值")
    @Min(value = 000000,message = "密码设定不正确")
    private String passWord;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String regTime;

    @Transient  // 不映射成列的字段得加@Transient 瞬态注解
    private String randomName; // 随机名,如脉脉匿名区


    public User(){
        super();
    }
    public User(String email, String randomName, String passWord, String userName, String regTime) {
        super();
        this.email = email;
        this.randomName = randomName;
        this.passWord = passWord;
        this.userName = userName;
        this.regTime = regTime;
    }
}
