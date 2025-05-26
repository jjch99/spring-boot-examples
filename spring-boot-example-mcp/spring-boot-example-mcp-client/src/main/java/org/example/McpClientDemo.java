package org.example;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1200)
public class McpClientDemo implements CommandLineRunner {

    @Autowired
    private SyncMcpToolCallbackProvider toolCallbackProvider;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Override
    public void run(String... args) throws Exception {

        System.out.println(this.getClass().getName());

        ChatClient chatClient = chatClientBuilder
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
        String q = "北京的天气";
        System.out.println("\n>>> QUESTION: " + q);
        System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(q).call().content());

    }

}
