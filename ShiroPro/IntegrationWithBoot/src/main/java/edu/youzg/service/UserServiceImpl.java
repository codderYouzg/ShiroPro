package edu.youzg.service;

import edu.youzg.mapper.UserMapper;
import edu.youzg.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Youzg
 * @CreateTime: 2020-07-05 08:20
 * @Description: 带你深究Java的本质！
 */
@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }
}
