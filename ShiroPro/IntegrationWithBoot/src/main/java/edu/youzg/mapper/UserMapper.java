package edu.youzg.mapper;

import edu.youzg.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: Youzg
 * @CreateTime: 2020-07-05 08:15
 * @Description: 带你深究Java的本质！
 */
@Repository
@Mapper
public interface UserMapper {
    User queryUserByName(String name);
}
