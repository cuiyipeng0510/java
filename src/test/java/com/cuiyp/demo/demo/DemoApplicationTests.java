package com.cuiyp.demo.demo;

import com.cuiyp.demo.designmode.SingleMode;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class DemoApplicationTests {
    @Test
    SingleMode getSingleMode(){
        SingleMode singleMode = getSingleMode();
        return singleMode;
    }
    @Test
    void contextLoads() {
    }

}
