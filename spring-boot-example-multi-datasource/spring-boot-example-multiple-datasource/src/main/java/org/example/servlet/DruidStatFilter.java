package org.example.servlet;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

@WebFilter(filterName = "druidWebStatFilter",
        urlPatterns = "/*",
        initParams = { @WebInitParam(name = "exclusions", value = DruidStatFilter.IGNORE_RES) })
public class DruidStatFilter extends WebStatFilter {

    public static final String IGNORE_RES = "*.mp4,*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*,/hello/*";

}
