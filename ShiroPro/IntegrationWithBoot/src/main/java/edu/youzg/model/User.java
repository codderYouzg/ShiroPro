package edu.youzg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Youzg
 * @CreateTime: 2020-07-05 08:12
 * @Description: 带你深究Java的本质！
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private String password;
    private String perms;
}
