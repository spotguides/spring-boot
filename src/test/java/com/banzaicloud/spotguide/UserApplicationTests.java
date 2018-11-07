package com.banzaicloud.spotguide;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:testdb")
public class UserApplicationTests {

    @Test
    public void contextLoads() {
    }

}
