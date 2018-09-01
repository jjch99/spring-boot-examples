package org.example.component;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageInitializer implements InitializingBean {

    @Autowired
    private Sender sender;

    @Override
    public void afterPropertiesSet() throws Exception {
        for (int i = 0; i < 100; i++) {
            sender.send();
        }
    }

}
