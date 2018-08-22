package com.faceyee.domain.repository;

import com.faceyee.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by 97390 on 8/21/2018.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{ // 两个泛型参数分别代表Java POJO类以及主键数据类型

    /*
    * Spring Data JPA提供了很多持久层接口
    * Repository为基类
    * CrudRepositoty提供了对增删改查操作的实现
    * PagingAndSortingRepository提供了分页查询方法的实现
    * JpaRepository继承自PagingAndSortingRepository接口
    * 创建自己的数据库操作接口时，只需继承上述JPA提供的某个接口，即可自动继承相关数据操作方法，而不需要再次实现。
    *如果这仍不能满足业务需求，也可以自定义SQL查询语句。如，采用@Query标签，语法为引用下面用@Param标识的变量
    * */
    @Query("select t from User t where t.userName = :userName")
    User findByUserName(@Param("userName") String userName);
    // 等同于下面一句JPA 自动构造的语句
    User findUserByName(String userName);
    User findUserByNameOrEmail(String userName, String email); // 自动构造的语句,参数名要和实体属性严格一致,如name在实体里是name,那么在这里也得是name
}
