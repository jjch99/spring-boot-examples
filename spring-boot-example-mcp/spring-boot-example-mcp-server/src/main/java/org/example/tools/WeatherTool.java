package org.example.tools;

import org.example.service.WeatherService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherTool {

    @Autowired
    private WeatherService weatherService;

    @Tool(description = "根据城市名称查询当前的天气")
    public String getWeather(@ToolParam(description = "城市/位置") String location) {
        return weatherService.getWeather(location);
    }

}
