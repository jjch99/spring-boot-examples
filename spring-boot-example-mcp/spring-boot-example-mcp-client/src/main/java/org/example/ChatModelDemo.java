package org.example;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1100)
public class ChatModelDemo implements CommandLineRunner {

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.getClass().getName());
        
        String response = openAiChatModel.call("Hello");
        System.out.println(response);
    }

}
