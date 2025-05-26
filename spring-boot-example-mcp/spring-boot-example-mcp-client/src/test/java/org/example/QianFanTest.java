package org.example;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

/**
 * 使用 OpenAI SDK 访问 QianFan
 * 
 * 创建 API key
 * https://cloud.baidu.com/doc/WENXINWORKSHOP/s/2m3fihw8s
 * 
 * 模型列表
 * https://cloud.baidu.com/doc/WENXINWORKSHOP/s/Wm9cvy6rl
 * 
 * 查看配额，购买配额
 * https://console.bce.baidu.com/qianfan/ais/console/onlineService
 * 
 * 不是所有模型都支持 Function calling, 支持 Function calling 的模型列表
 * https://cloud.baidu.com/doc/qianfan-docs/s/xm95lyys5
 */
public class QianFanTest {

    @Test
    public void run() {

        String apiKey = System.getenv("QIANFAN_API_KEY");
        if (StringUtils.isEmpty(apiKey)) {
            System.out.println("env QIANFAN_API_KEY not found or empty");
            return;
        }

        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .baseUrl("https://qianfan.baidubce.com/v2/")
                .build();
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage("Hello")
                .model("ernie-lite-8k")
                .build();

        ChatCompletion chatCompletion = client.chat().completions().create(params);
        System.out.println(chatCompletion.choices().get(0).message().content());

    }

}
