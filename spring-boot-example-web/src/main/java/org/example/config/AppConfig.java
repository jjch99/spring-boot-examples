package org.example.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private String name;

    private List<Menu> menus;

    @Getter
    @Setter
    public static class Menu {

        private String title;

        private String name;

        private String path;

    }

}
