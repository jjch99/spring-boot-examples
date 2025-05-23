package org.example.service;

import org.example.utils.Gzip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 和风天气
 * https://dev.qweather.com/
 * https://console.qweather.com/project
 * 
 * 实时天气接口
 * https://dev.qweather.com/docs/api/weather/weather-now/
 * 
 * 城市代码
 * https://github.com/qwd/LocationList/blob/master/China-City-List-latest.csv
 */
@Service
public class QweatherService  implements WeatherService {

    private final static Logger log = LoggerFactory.getLogger(QweatherService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${weather.api-key}")
    private String apiKey;

    @Value("${weather.url}")
    private String apiUrl;

    private String getLocation(String in) {
        return "101010100";
    }

    @Override
    public String getWeather(String location) {
        location = getLocation(location);

        String url = String.format("%s?location=%s&key=%s", apiUrl, location, apiKey);
        log.info("url: {}", url);

        try {
            byte[] body = restTemplate.getForObject(url, byte[].class);
            byte[] data = Gzip.gunzip(body);
            String json = new String(data, "UTF-8");
            return json;
        } catch (Exception e) {
            String msg = "获取天气信息失败";
            log.error(msg, e);
            return msg;
        }
    }

}
