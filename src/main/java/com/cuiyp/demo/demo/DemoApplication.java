package com.cuiyp.demo.demo;

import com.cuiyp.demo.demo.redis.TestRedis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext cac = SpringApplication.run(DemoApplication.class, args);
        TestRedis bean = cac.getBean(TestRedis.class);
        bean.testRedis();
//        Math.round(11.5);
    }

}
