package org.example.service;

import org.example.utils.Gzip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

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
public class QweatherService implements WeatherService {

    private final static Logger log = LoggerFactory.getLogger(QweatherService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    private List<CSVRecord> locations = new ArrayList<>();

    @Value("${weather.api-key}")
    private String apiKey;

    @Value("${weather.url}")
    private String apiUrl;

    @PostConstruct
    public void init() {
        ClassPathResource resource = new ClassPathResource("China-City-List.csv");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            reader.readLine();

            this.locations = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setNullString("")
                    .get()
                    .parse(reader)
                    .getRecords();

            log.info("China-City-List init ok, records: {}", this.locations.size());
        } catch (Exception e) {
            throw new RuntimeException("China-City-List init failed", e);
        }
    }

    private String getLocation(String location) {
        for (CSVRecord record : this.locations) {
            String id = record.get(0);
            String nameEN = record.get(1);
            String nameCN = record.get(2);
            if (StringUtils.equals(id, location) || StringUtils.equals(nameCN, location) || StringUtils.equalsIgnoreCase(nameEN, location)) {
                return id;
            }
        }
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
