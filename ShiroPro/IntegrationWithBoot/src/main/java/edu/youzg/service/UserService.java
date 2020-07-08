package edu.youzg.service;

import edu.youzg.model.User;

/**
 * @Author: Youzg
 * @CreateTime: 2020-07-05 08:19
 * @Description: 带你深究Java的本质！
 */
public interface UserService {
    User queryUserByName(String name);
}
