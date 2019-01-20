package org.example.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.core.convert.converter.Converter;

/**
 * 用于将请求中的字符串参数自动转换为Date类型参数
 */
public class StringToDateConverter implements Converter<String, Date> {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    private static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date convert(String source) {

        if (StringUtils.isEmpty(source)) {
            return null;
        }
        try {
            if (StringUtils.isNumeric(source)) {
                return new Date(NumberUtils.toLong(source));
            }
            if (DATE_FORMAT_PATTERN.length() == source.length()) {
                return FastDateFormat.getInstance(DATE_FORMAT_PATTERN).parse(source);
            }
            if (DATETIME_FORMAT_PATTERN.length() == source.length()) {
                return FastDateFormat.getInstance(DATETIME_FORMAT_PATTERN).parse(source);
            }
            return FastDateFormat.getInstance().parse(source);
        } catch (Exception e) {
            return null;
        }
    }

}
