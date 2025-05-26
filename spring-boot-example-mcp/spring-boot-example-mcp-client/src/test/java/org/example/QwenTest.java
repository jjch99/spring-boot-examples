package org.example;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

/**
 * 使用 OpenAI 兼容 API 访问
 * https://help.aliyun.com/zh/model-studio/use-qwen-by-calling-api
 * 
 * 获取API Key
 * https://bailian.console.aliyun.com/?tab=model#/api-key
 */
public class QwenTest {

    @Test
    public void run() {

        String apiKey = System.getenv("DASHSCOPE_API_KEY");
        if (StringUtils.isEmpty(apiKey)) {
            System.out.println("env DASHSCOPE_API_KEY not found or empty");
            return;
        }

        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage("Hello")
                .model("qwen-plus")
                .build();

        ChatCompletion chatCompletion = client.chat().completions().create(params);
        System.out.println(chatCompletion.choices().get(0).message().content().orElse("No response"));

    }

}
