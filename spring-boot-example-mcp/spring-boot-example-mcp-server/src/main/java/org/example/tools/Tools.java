package org.example.tools;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Tools {

	// 可以有多个这样的Bean，框架会自动merge
    @Bean
	public ToolCallbackProvider weatherTools(@Autowired WeatherTool weatherTool) {
		return MethodToolCallbackProvider.builder().toolObjects(weatherTool).build();
	}

}
