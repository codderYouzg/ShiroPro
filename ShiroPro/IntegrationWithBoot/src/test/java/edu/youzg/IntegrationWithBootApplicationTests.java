package edu.youzg;

import edu.youzg.model.User;
import edu.youzg.service.UserService;
import edu.youzg.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IntegrationWithBootApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        User youzg = userService.queryUserByName("youzg");
        System.out.println(youzg);
    }

}
